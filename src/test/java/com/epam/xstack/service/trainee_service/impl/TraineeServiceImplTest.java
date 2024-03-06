package com.epam.xstack.service.trainee_service.impl;

import com.epam.xstack.actuators.prometheuses.UserSessionMetrics;
import com.epam.xstack.configuration.JwtService;
import com.epam.xstack.exceptions.exception.UserNameNotExistsException;
import com.epam.xstack.exceptions.generator.PasswordUserNameGenerator;
import com.epam.xstack.exceptions.validator.ActivationValidator;
import com.epam.xstack.exceptions.validator.SaveTokenValidation;
import com.epam.xstack.exceptions.validator.UserNameExistenceValidator;
import com.epam.xstack.mapper.trainee_mapper.*;
import com.epam.xstack.models.dto.trainee_dto.request.*;
import com.epam.xstack.models.dto.trainee_dto.response.*;
import com.epam.xstack.models.entity.Trainee;
import com.epam.xstack.models.entity.Training;
import com.epam.xstack.models.enums.Code;
import com.epam.xstack.repository.TraineeRepository;
import com.epam.xstack.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TraineeServiceImplTest {
    @InjectMocks
    private TraineeServiceImpl service;
    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private UserRepository repository;
    @Mock
    private PasswordUserNameGenerator generator;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserNameExistenceValidator checkUserNameExistence;
    @Mock
    private TraineeProfileUpdateRequestMapper updateTraineeProfileRequestMapper;
    @Mock
    private TraineeProfileSelectRequestMapper traineeProfileRequestMapper;
    @Mock
    private TraineeActivateDeActivateMapper activateDeActivateTraineeMapper;
    @Mock
    private TraineeTrainingsListMapper traineeTrainingsListMapper;
    @Mock
    private ActivationValidator checkActivation;
    @Mock
    private SaveTokenValidation tokenValidation;
    @Mock
    private UserSessionMetrics userSessionMetrics;
    @Mock
    private TraineesTrainerListUpdateMapper traineesTrainerListUpdateMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = mock(TraineeServiceImpl.class);
        traineeRepository = mock(TraineeRepository.class);
        checkUserNameExistence = mock(UserNameExistenceValidator.class);
        userSessionMetrics = mock(UserSessionMetrics.class);
        jwtService = mock(JwtService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        generator = mock(PasswordUserNameGenerator.class);
        userSessionMetrics = mock(UserSessionMetrics.class);
        traineeProfileRequestMapper = mock(TraineeProfileSelectRequestMapper.class);
        service = new TraineeServiceImpl(repository, traineeRepository, tokenValidation, checkActivation, passwordEncoder,
                jwtService, generator, checkUserNameExistence, traineeProfileRequestMapper, updateTraineeProfileRequestMapper,
                activateDeActivateTraineeMapper, traineeTrainingsListMapper, traineesTrainerListUpdateMapper, userSessionMetrics);

    }

    @Test
    public void testShouldSaveTrainee() {
        String firstName = "Sam";
        String lastName = "Smith";
        String createdUserName = "Sam.Smith";
        String generatedPassword = "newRandomPassword";
        String token = "newJwtToken";

        TraineeRegistrationRequestDTO request = new TraineeRegistrationRequestDTO();
        request.setFirstName(firstName);
        request.setLastName(lastName);

        Trainee savedTrainee = new Trainee();
        savedTrainee.setUserName(createdUserName);

        when(generator.generateRandomPassword()).thenReturn(generatedPassword);
        when(generator.generateUserName(firstName, lastName)).thenReturn(createdUserName);
        when(jwtService.generateToken(any(Trainee.class))).thenReturn(token);
        when(passwordEncoder.encode(generatedPassword)).thenReturn(generatedPassword);
        when(traineeRepository.save(any(Trainee.class))).thenReturn(savedTrainee);

        TraineeRegistrationResponseDTO response = service.registerTrainee(request);

        Assertions.assertThat(response).isNotNull();
        assertEquals(createdUserName, response.getUserName());
        assertEquals(generatedPassword, response.getPassword());
        verify(generator).generateRandomPassword();
        verify(generator).generateUserName(firstName, lastName);
        verify(jwtService).generateToken(savedTrainee);
        verify(passwordEncoder).encode(generatedPassword);
        verify(userSessionMetrics).incrementActiveSessions();
    }

    @Test
    public void testShouldSelectTraineeTrainingsList() {
        UUID id = UUID.randomUUID();
        List<Training> trainings = Arrays.asList(new Training(), new Training());
        TraineeTrainingsListRequestDTO requestDTO = new TraineeTrainingsListRequestDTO();
        requestDTO.setUserName("Andrea.Bocelli");
        Trainee trainee = new Trainee();
        trainee.setUserName("Andrea.Bocelli");
        trainee.setTrainings(trainings);
        when(traineeRepository.findById(any(UUID.class))).thenReturn(Optional.of(trainee));
        when(traineeTrainingsListMapper.toEntity(requestDTO)).thenReturn(trainee);

        TraineeTrainingsListResponseDTO responseDTO = service.selectTraineeTrainingsList(id, requestDTO);

        assertNotNull(responseDTO);
        assertEquals("Andrea.Bocelli", trainee.getUsername());
        assertEquals(trainings, trainee.getTrainings());
    }

    @Test
    public void testShouldActivateOrDeActivateTrainee() {
        UUID id = UUID.randomUUID();
        TraineeActivateDeActivateDTO dto = new TraineeActivateDeActivateDTO();
        dto.setIsActive(true);

        Trainee trainee = new Trainee();
        trainee.setIsActive(true);

        Trainee existingTrainee = new Trainee();
        existingTrainee.setId(id);
        existingTrainee.setIsActive(false);

        TraineeOkResponseDTO expectedResponse = TraineeOkResponseDTO.builder()
                .code(Code.STATUS_200_OK)
                .response("Activate DeActivate Trainee updated")
                .build();

        when(activateDeActivateTraineeMapper.toEntity(dto)).thenReturn(trainee);
        when(traineeRepository.findById(id)).thenReturn(Optional.of(existingTrainee));

        TraineeOkResponseDTO response = service.activateDe_ActivateTrainee(id, dto);

        verify(activateDeActivateTraineeMapper).toEntity(dto);
        verify(traineeRepository).findById(id);
        verify(checkActivation).checkActiveOrNotTraineeActive(id, dto);
        verify(traineeRepository).save(existingTrainee);
        verify(activateDeActivateTraineeMapper).toDto(trainee);

        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getResponse(), response.getResponse());
    }

    @Test
    public void testShouldUpdateTraineeProfile() {
        String firstName = "Omar";
        String lastName = "Smith";
        String createdUserName = "Omar.Smith";
        String generatedPassword = "someRandomPassword";
        String token = "someJwtToken";
        TraineeRegistrationRequestDTO request = new TraineeRegistrationRequestDTO();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        Trainee savedTrainee = new Trainee();
        savedTrainee.setUserName(createdUserName);

        when(generator.generateRandomPassword()).thenReturn(generatedPassword);
        when(generator.generateUserName(firstName, lastName)).thenReturn(createdUserName);
        when(jwtService.generateToken(any(Trainee.class))).thenReturn(token);
        when(passwordEncoder.encode(generatedPassword)).thenReturn(generatedPassword);
        when(traineeRepository.save(any(Trainee.class))).thenReturn(savedTrainee);

        TraineeProfileUpdateRequestDTO requestDTO = new TraineeProfileUpdateRequestDTO();
        savedTrainee.setFirstName(requestDTO.getFirstName());
        savedTrainee.setLastName(requestDTO.getLastName());

        Trainee updatedTrainee = traineeRepository.save(savedTrainee);

        Assertions.assertThat(updatedTrainee).isNotNull();
        verify(traineeRepository).save(any(Trainee.class));
    }

    @Test
    public void testShouldSelectTraineeProfileByUserName() {
        UUID id = UUID.randomUUID();
        Trainee trainee = new Trainee();
        trainee.setFirstName("Sara");
        trainee.setLastName("Toms");
        trainee.setIsActive(true);
        trainee.setUserName("Sara.Toms");
        TraineeProfileSelectRequestDTO requestDTO = new TraineeProfileSelectRequestDTO();
        requestDTO.setUserName("Sara.Toms");

        when(traineeProfileRequestMapper.toEntity(requestDTO)).thenReturn(trainee);
        when(traineeProfileRequestMapper.toDto(trainee)).thenReturn(requestDTO);
        when(traineeRepository.findById(id)).thenReturn(Optional.of(trainee));

        TraineeProfileSelectResponseDTO responseDTO = service.selectTraineeProfileByUserName(id, requestDTO);

        assertNotNull(responseDTO);
        assertTrue(responseDTO.getIsActive());
        assertEquals("Sara", responseDTO.getFirstName());
        assertEquals("Toms", responseDTO.getLastName());
    }

    @Test
    public void testShouldDeleteTraineeByUserName() {
        UUID id = UUID.randomUUID();
        Trainee traineeInDb = new Trainee();
        traineeInDb.setId(id);
        traineeInDb.setFirstName("Emilia");
        traineeInDb.setLastName("Clarke");
        traineeInDb.setUserName("Emilia.Clarke");
        traineeInDb.setIsActive(true);
        TraineeProfileSelectRequestDTO requestDTO = new TraineeProfileSelectRequestDTO();
        requestDTO.setUserName("Emilia.Clarke");
        when(traineeRepository.findById(id)).thenReturn(Optional.of(traineeInDb));

        TraineeOkResponseDTO responseDTO = service.deleteTraineeByUserName(id, requestDTO);

        when(traineeRepository.findById(id)).thenReturn(Optional.empty());
        verify(traineeRepository, times(1)).deleteById(id);
        assertEquals("Trainee is deleted from database", responseDTO.getResponse());
        assertEquals(Code.STATUS_200_OK, responseDTO.getCode());
    }

    @Test
    public void testShouldSelectNotAssignedOnTraineeActiveTrainers() {
        UUID id = UUID.randomUUID();
        Trainee trainee = new Trainee();
        trainee.setTrainers(new ArrayList<>());
        trainee.setIsActive(true);
        trainee.setIsAssigned(false);
        trainee.setUserName("Salma.Moon");
        TraineesTrainerActiveAndNotAssignedRequestDTO requestDTO = new TraineesTrainerActiveAndNotAssignedRequestDTO();
        requestDTO.setUserName("Salma.Moon");

        when(traineeRepository.findById(id)).thenReturn(Optional.of(trainee));

        TraineesTrainerActiveAndNotAssignedResponseDTO responseDTO = service.selectNotAssignedOnTraineeActiveTrainers(id, requestDTO);

        assertNotNull(responseDTO);
        assertEquals(0, responseDTO.getTrainers().size());
    }

    @Test
    public void testShouldUpdateTraineesTrainerList() {
        UUID id = UUID.randomUUID();
        TraineesTrainerListUpdateRequestDTO requestDTO = new TraineesTrainerListUpdateRequestDTO();
        requestDTO.setUserName("Oscar.Wild");
        Trainee traineeToBeUpdated = mock(Trainee.class);

        when(traineeRepository.findById(id)).thenReturn(Optional.of(traineeToBeUpdated));
        when(traineeToBeUpdated.getId()).thenReturn(null);
        when(traineesTrainerListUpdateMapper.toEntity(requestDTO)).thenReturn(traineeToBeUpdated);

        assertThrows(UserNameNotExistsException.class, () -> {
            service.updateTraineesTrainerList(id, requestDTO);
        });
    }
}

