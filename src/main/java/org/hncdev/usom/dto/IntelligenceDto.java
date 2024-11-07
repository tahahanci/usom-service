package org.hncdev.usom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hncdev.usom.converter.DateTimeConverter;
import org.hncdev.usom.model.Intelligence;

public record IntelligenceDto(Long id, String url, String type, String desc,
                              String source, String date,
                              @JsonProperty(value = "criticality_level")
                              int criticalityLevel,
                              @JsonProperty(value = "connectiontype")
                              String connectionType) {

    public static Intelligence convert(IntelligenceDto intelligenceDto) {
        return Intelligence.builder()
                .intelligenceID(intelligenceDto.id)
                .intelligenceUrl(intelligenceDto.url)
                .intelligenceType(intelligenceDto.type)
                .description(intelligenceDto.desc)
                .source(intelligenceDto.source)
                .intelligenceDate(new DateTimeConverter().convert(intelligenceDto.date))
                .criticalityLevel(intelligenceDto.criticalityLevel)
                .connectionType(intelligenceDto.connectionType)
                .build();
    }
}
