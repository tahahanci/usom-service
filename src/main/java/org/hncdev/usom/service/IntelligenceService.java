package org.hncdev.usom.service;

import org.hncdev.usom.dto.IntelligenceResponse;

public interface IntelligenceService {

    void save();

    IntelligenceResponse findIntelligence(Long intelligenceID);
}
