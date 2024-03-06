package com.epam.xstack.service.training_type_service.impl;

import com.epam.xstack.mapper.training_type_mapper.TrainingTypeMapper;
import com.epam.xstack.models.dto.training_type_dto.TrainingTypeDTO;
import com.epam.xstack.models.entity.TrainingType;
import com.epam.xstack.repository.TrainingTypeRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TrainingTypeServiceImplTest {
    @Mock
    private TrainingTypeRepository trainingTypeRepository;
    @Mock
    private TrainingTypeMapper trainingTypeMapper;
    @Mock
    private TrainingType trainingType;
    @Mock
    private TrainingTypeDTO trainingTypeDTO;
    @InjectMocks
    private TrainingTypeServiceImpl service;

    @BeforeEach
    public void setUp() {
        trainingTypeDTO.setTrainingType("New training type");
        trainingType = trainingTypeMapper.toEntity(trainingTypeDTO);
        trainingTypeRepository.save(trainingType);
    }

    @Test
    public void testShouldSaveTrainingType() {
        when(trainingTypeMapper.toEntity(any(TrainingTypeDTO.class))).thenReturn(trainingType);
        service.save(trainingTypeDTO);

        verify(trainingTypeMapper).toEntity(trainingTypeDTO);
        verify(trainingTypeRepository).save(trainingType);
        verify(trainingTypeMapper).toDto(trainingType);
    }

    @Test
    public void testShouldFindAllTrainingType() {
        List<TrainingType> trainingTypes = Arrays.asList(
                new TrainingType(1L, "Type swimming"),
                new TrainingType(2L, "Type dancing"),
                new TrainingType(3L, "Type boxing")
        );
        List<TrainingTypeDTO> expectedDtos = Arrays.asList(
                new TrainingTypeDTO("Type swimming"),
                new TrainingTypeDTO("Type dancing"),
                new TrainingTypeDTO("Type boxing")
        );
        when(trainingTypeRepository.findAll()).thenReturn(trainingTypes);
        when(trainingTypeMapper.toDtos(trainingTypes)).thenReturn(expectedDtos);

        List<TrainingTypeDTO> actualDtos = service.findAll();

        assertEquals(expectedDtos, actualDtos);
        assertEquals(3, trainingTypes.size());
    }
}
