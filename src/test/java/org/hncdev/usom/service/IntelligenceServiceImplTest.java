package org.hncdev.usom.service;

import jakarta.persistence.EntityNotFoundException;
import org.hncdev.usom.model.Intelligence;
import org.hncdev.usom.repository.IntelligenceRepository;
import org.hncdev.usom.service.concretes.IntelligenceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IntelligenceServiceImplTest {

    @Mock
    private IntelligenceRepository intelligenceRepository;

    @InjectMocks
    private IntelligenceServiceImpl intelligenceService;

    @Test
    void itShouldReturnIntelligenceIfIntelligenceExistsInDB() {
        Long intelligenceID = 12345L;

        Intelligence expectedIntelligence = new Intelligence();
        expectedIntelligence.setIntelligenceID(intelligenceID);

        when(intelligenceRepository.findByIntelligenceID(intelligenceID)).thenReturn(Optional.of(expectedIntelligence));

        Intelligence resultIntelligence = intelligenceService.findIntelligence(intelligenceID);

        assertThat(resultIntelligence).isNotNull();
        assertThat(resultIntelligence.getIntelligenceID()).isEqualTo(expectedIntelligence.getIntelligenceID());
        verify(intelligenceRepository, times(1)).findByIntelligenceID(intelligenceID);
    }

    @Test
    void itShouldThrowExceptionIfIntelligenceNotExist() {
        Long intelligenceID = 12345L;
        when(intelligenceRepository.findByIntelligenceID(intelligenceID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> intelligenceService.findIntelligence(intelligenceID))
                .isInstanceOf(EntityNotFoundException.class);
        verify(intelligenceRepository, times(1)).findByIntelligenceID(intelligenceID);
    }

}
