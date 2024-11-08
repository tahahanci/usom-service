package org.hncdev.usom.service;

import org.hncdev.usom.model.Intelligence;

public interface IntelligenceService {

    void save();

    Intelligence findIntelligence(Long intelligenceID);
}
