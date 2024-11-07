package org.hncdev.usom.controller;

import lombok.RequiredArgsConstructor;
import org.hncdev.usom.service.IntelligenceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/intelligences")
@RequiredArgsConstructor
public class IntelligenceController {

    private final IntelligenceService intelligenceService;

    @GetMapping("/fetch")
    public void fetchUsomIntelligences() {
        intelligenceService.save();
    }
}
