package org.hncdev.usom.dto;

import java.util.List;

public record UsomResponse(int totalCount, int count, List<IntelligenceDto> models,
                           int page, int pageCount) {
}
