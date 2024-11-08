package org.hncdev.usom.dto;

import lombok.Builder;
import org.hncdev.usom.model.Intelligence;

import java.time.LocalDateTime;

@Builder
public record IntelligenceResponse(Long intelligenceID, String intelligenceUrl,
                                   String intelligenceType, LocalDateTime intelligenceDate) {

    public static IntelligenceResponse convert(Intelligence intelligence) {
        return new IntelligenceResponse(intelligence.getIntelligenceID(), intelligence.getIntelligenceUrl(),
                intelligence.getIntelligenceType(), intelligence.getIntelligenceDate());
    }
}
