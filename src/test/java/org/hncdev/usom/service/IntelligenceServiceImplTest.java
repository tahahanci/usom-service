package org.hncdev.usom.service;

import jakarta.persistence.EntityNotFoundException;
import org.hncdev.usom.dto.IntelligenceResponse;
import org.hncdev.usom.model.Intelligence;
import org.hncdev.usom.repository.IntelligenceRepository;
import org.hncdev.usom.service.concretes.IntelligenceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
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

    private Intelligence testIntelligence;
    private IntelligenceResponse expectedResponse;

    @BeforeEach
    void setUp() {
        testIntelligence = Intelligence.builder()
                .intelligenceID(1L)
                .intelligenceUrl("test-url")
                .intelligenceType("test")
                .intelligenceDate(LocalDateTime.of(2021, 1, 1, 1, 1, 1))
                .build();

        expectedResponse = IntelligenceResponse.builder()
                .intelligenceID(1L)
                .intelligenceUrl("test-url")
                .intelligenceType("test")
                .intelligenceDate(LocalDateTime.of(2021, 1, 1, 1, 1, 1))
                .build();
    }

    @Test
    void shouldFindIntelligenceWhenIntelligenceIDExists() {
        Long intelligenceID = 1L;

        when(intelligenceRepository.findByIntelligenceID(intelligenceID))
                .thenReturn(Optional.of(testIntelligence));

        IntelligenceResponse actualResponse = intelligenceService.findIntelligence(intelligenceID);

        assertThat(actualResponse)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);

        verify(intelligenceRepository, times(1))
                .findByIntelligenceID(intelligenceID);
    }

    @Test
    void shouldThrowExceptionWhenIntelligenceNotFound() {
        Long intelligenceID = 100L;

        when(intelligenceRepository.findByIntelligenceID(intelligenceID))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> intelligenceService.findIntelligence(intelligenceID))
                .isInstanceOf(EntityNotFoundException.class);

        verify(intelligenceRepository, times(1))
                .findByIntelligenceID(intelligenceID);
    }

    @Test
    void shouldThrowExceptionIfIntelligenceIDIsNull() {
        assertThatThrownBy(() -> intelligenceService.findIntelligence(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Intelligence ID cannot be null");

        verify(intelligenceRepository, never()).findByIntelligenceID(any());
    }

}
