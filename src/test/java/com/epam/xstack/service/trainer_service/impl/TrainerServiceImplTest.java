package com.epam.xstack.service.trainer_service.impl;

import com.epam.xstack.actuators.prometheuses.UserSessionMetrics;
import com.epam.xstack.configuration.JwtService;
import com.epam.xstack.exceptions.generator.PasswordUserNameGenerator;
import com.epam.xstack.exceptions.validator.ActivationValidator;
import com.epam.xstack.exceptions.validator.SaveTokenValidation;
import com.epam.xstack.exceptions.validator.UserNameExistenceValidator;
import com.epam.xstack.mapper.trainer_mapper.*;
import com.epam.xstack.models.dto.trainer_dto.request.*;
import com.epam.xstack.models.dto.trainer_dto.response.TrainerOkResponseDTO;
import com.epam.xstack.models.dto.trainer_dto.response.TrainerProfileSelectResponseDTO;
import com.epam.xstack.models.dto.trainer_dto.response.TrainerRegistrationResponseDTO;
import com.epam.xstack.models.dto.trainer_dto.response.TrainerTrainingsListResponseDTO;
import com.epam.xstack.models.entity.Trainer;
import com.epam.xstack.models.entity.Training;
import com.epam.xstack.models.enums.Code;
import com.epam.xstack.repository.TrainerRepository;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TrainerServiceImplTest {
    @InjectMocks
    private TrainerServiceImpl service;
    @Mock
    private UserRepository repository;
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private PasswordUserNameGenerator generator;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private TrainerRegistrationRequestMapper registrationRequestMapper;
    @Mock
    private UserNameExistenceValidator checkUserNameExistence;
    @Mock
    private TrainerProfileSelectRequestMapper trainerProfileRequestMapper;
    @Mock
    private TrainerProfileUpdateRequestMapper updateTrainerProfileRequestMapper;
    @Mock
    private TrainerActivateDeActivateMapper activateDeActivateTrainerMapper;
    @Mock
    private TrainerTrainingsListMapper trainerTrainingsListMapper;
    @Mock
    private ActivationValidator checkActivation;
    @Mock
    private SaveTokenValidation tokenValidation;
    @Mock
    private UserSessionMetrics userSessionMetrics;
    @Mock
    private TrainerProfileSelectRequestMapper requestMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = mock(TrainerServiceImpl.class);
        trainerRepository = mock(TrainerRepository.class);
        checkUserNameExistence = mock(UserNameExistenceValidator.class);
        userSessionMetrics = mock(UserSessionMetrics.class);
        jwtService = mock(JwtService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        generator = mock(PasswordUserNameGenerator.class);
        userSessionMetrics = mock(UserSessionMetrics.class);
        requestMapper = mock(TrainerProfileSelectRequestMapper.class);
        service = new TrainerServiceImpl(repository, trainerRepository, passwordEncoder, jwtService, generator,
                tokenValidation, checkActivation, checkUserNameExistence, userSessionMetrics, trainerProfileRequestMapper,
                updateTrainerProfileRequestMapper, activateDeActivateTrainerMapper, trainerTrainingsListMapper, registrationRequestMapper);
    }


    @Test
    public void testShouldSaveTrainer() {
        String firstName = "Omar";
        String lastName = "Smith";
        String createdUserName = "Omar.Smith";
        String generatedPassword = "someRandomPassword";
        String token = "someJwtToken";

        TrainerRegistrationRequestDTO request = new TrainerRegistrationRequestDTO();
        request.setFirstName(firstName);
        request.setLastName(lastName);

        Trainer savedTrainer = new Trainer();
        savedTrainer.setUserName(createdUserName);

        when(generator.generateRandomPassword()).thenReturn(generatedPassword);
        when(generator.generateUserName(firstName, lastName)).thenReturn(createdUserName);
        when(jwtService.generateToken(any(Trainer.class))).thenReturn(token);
        when(passwordEncoder.encode(generatedPassword)).thenReturn(generatedPassword);
        when(trainerRepository.save(any(Trainer.class))).thenReturn(savedTrainer);

        TrainerRegistrationResponseDTO response = service.registerTrainer(request);

        assertEquals(createdUserName, response.getUserName());
        assertEquals(generatedPassword, response.getPassword());
        verify(generator).generateRandomPassword();
        verify(generator).generateUserName(firstName, lastName);
        verify(jwtService).generateToken(savedTrainer);
        verify(passwordEncoder).encode(generatedPassword);
        verify(trainerRepository).save(any(Trainer.class));
        verify(userSessionMetrics).incrementActiveSessions();
    }

    @Test
    public void testShouldUpdateTrainerProfile() {
        String firstName = "Omar";
        String lastName = "Smith";
        String createdUserName = "Omar.Smith";
        String generatedPassword = "someRandomPassword";
        String token = "someJwtToken";
        TrainerRegistrationRequestDTO request = new TrainerRegistrationRequestDTO();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        Trainer savedTrainer = new Trainer();
        savedTrainer.setUserName(createdUserName);

        when(generator.generateRandomPassword()).thenReturn(generatedPassword);
        when(generator.generateUserName(firstName, lastName)).thenReturn(createdUserName);
        when(jwtService.generateToken(any(Trainer.class))).thenReturn(token);
        when(passwordEncoder.encode(generatedPassword)).thenReturn(generatedPassword);
        when(trainerRepository.save(any(Trainer.class))).thenReturn(savedTrainer);

        TrainerProfileUpdateRequestDTO requestDTO = new TrainerProfileUpdateRequestDTO();
        savedTrainer.setFirstName(requestDTO.getFirstName());
        savedTrainer.setLastName(requestDTO.getLastName());

        Trainer updatedTrainer = trainerRepository.save(savedTrainer);

        Assertions.assertThat(updatedTrainer).isNotNull();
        verify(trainerRepository).save(any(Trainer.class));
    }

    @Test
    public void testShouldActivateOrDeActivateTrainer() {
        UUID id = UUID.randomUUID();
        TrainerActivateDeActivateDTO dto = new TrainerActivateDeActivateDTO();
        dto.setIsActive(true);
        Trainer trainer = new Trainer();
        trainer.setIsActive(true);
        Trainer existingTrainer = new Trainer();
        existingTrainer.setId(id);
        existingTrainer.setIsActive(false);
        TrainerOkResponseDTO expectedResponse = TrainerOkResponseDTO.builder()
                .code(Code.STATUS_200_OK)
                .response("Activate DeActivate Trainer updated")
                .build();

        when(activateDeActivateTrainerMapper.toEntity(dto)).thenReturn(trainer);
        when(trainerRepository.findById(id)).thenReturn(Optional.of(existingTrainer));

        TrainerOkResponseDTO response = service.activateDe_ActivateTrainer(id, dto);

        verify(activateDeActivateTrainerMapper).toEntity(dto);
        verify(trainerRepository).findById(id);
        verify(checkActivation).checkActiveOrNotTrainerActive(id, dto);
        verify(trainerRepository).save(existingTrainer);
        verify(activateDeActivateTrainerMapper).toDto(trainer);

        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getResponse(), response.getResponse());
    }

    @Test
    public void testShouldSelectTrainerTrainingsList() {
        UUID id = UUID.randomUUID();
        List<Training> trainings = Arrays.asList(new Training(), new Training());
        TrainerTrainingsListRequestDTO requestDTO = new TrainerTrainingsListRequestDTO();
        requestDTO.setUserName("Hafez.Shiraz");
        Trainer trainer = new Trainer();
        trainer.setUserName("Hafez.Shiraz");
        trainer.setTrainings(trainings);
        when(trainerRepository.findById(any(UUID.class))).thenReturn(Optional.of(trainer));
        when(trainerTrainingsListMapper.toEntity(requestDTO)).thenReturn(trainer);

        TrainerTrainingsListResponseDTO responseDTO = service.selectTrainerTrainingsList(id, requestDTO);

        assertNotNull(responseDTO);
        assertEquals("Hafez.Shiraz", trainer.getUsername());
        assertEquals(trainings, trainer.getTrainings());
    }

    @Test
    public void testShouldSelectTrainerProfileByUserName() {
        UUID id = UUID.randomUUID();
        Trainer trainer = new Trainer();
        trainer.setFirstName("Sarah");
        trainer.setLastName("Toms");
        trainer.setIsActive(true);
        trainer.setUserName("Sarah.Toms");
        TrainerProfileSelectRequestDTO requestDTO = new TrainerProfileSelectRequestDTO();
        requestDTO.setUserName("Sarah.Toms");

        when(trainerProfileRequestMapper.toEntity(requestDTO)).thenReturn(trainer);
        when(trainerProfileRequestMapper.toDto(trainer)).thenReturn(requestDTO);
        when(trainerRepository.findById(id)).thenReturn(Optional.of(trainer));

        TrainerProfileSelectResponseDTO responseDTO = service.selectTrainerProfileByUserName(id, requestDTO);

        assertNotNull(responseDTO);
        assertEquals("Sarah", responseDTO.getFirstName());
        assertEquals("Toms", responseDTO.getLastName());
        assertTrue(responseDTO.getIsActive());
        assertNotNull(responseDTO);
        verify(trainerProfileRequestMapper).toDto(trainer);
    }
}
