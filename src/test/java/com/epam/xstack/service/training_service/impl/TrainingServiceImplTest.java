package com.epam.xstack.service.training_service.impl;

import com.epam.xstack.mapper.training_mapper.TrainingMapper;
import com.epam.xstack.models.dto.training_dto.request.TrainingSaveRequestDTO;
import com.epam.xstack.models.dto.training_dto.response.TrainingSaveResponseDTO;
import com.epam.xstack.models.entity.Trainee;
import com.epam.xstack.models.entity.Trainer;
import com.epam.xstack.models.entity.Training;
import com.epam.xstack.models.enums.Code;
import com.epam.xstack.repository.TrainingRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TrainingServiceImplTest {
    @InjectMocks
    private TrainingServiceImpl service;
    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private TrainingMapper trainingMapper;
    @Mock
    private TrainingSaveRequestDTO requestDTO;
    @Mock
    private Training training;

    @BeforeEach
    public void setUp() {
        training = trainingMapper.toEntity(requestDTO);
        training.setTrainingName("Swimming");
        training.setTrainingDate(new Date());
        training.setTrainer(new Trainer());
        training.setTrainee(new Trainee());
        trainingRepository.save(training);
        trainingMapper.toDto(training);
    }

    @Test
    public void testShouldSaveTraining() {
        when(trainingMapper.toEntity(any(TrainingSaveRequestDTO.class))).thenReturn(training);
        TrainingSaveResponseDTO trainingSaveResponseDTO = service.saveTraining(requestDTO);

        assertNotNull(trainingSaveResponseDTO);
        assertEquals("Training is saved", trainingSaveResponseDTO.getResponse());
        assertEquals(Code.STATUS_200_OK, trainingSaveResponseDTO.getCode());

        verify(trainingMapper).toEntity(requestDTO);
        verify(trainingRepository).save(training);
        verify(trainingMapper).toDto(training);
    }
}
