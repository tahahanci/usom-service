package org.hncdev.usom.controller;

import lombok.RequiredArgsConstructor;
import org.hncdev.usom.model.Intelligence;
import org.hncdev.usom.service.IntelligenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/intelligences")
@RequiredArgsConstructor
public class IntelligenceController {

    private final IntelligenceService intelligenceService;

    @GetMapping("/fetch")
    public String fetchUsomIntelligences() {
        intelligenceService.save();
        return "Intelligence fetched and saved.";
    }

    @GetMapping("/get/{intelligenceID}")
    public ResponseEntity<Intelligence> getUsomIntelligences(@PathVariable Long intelligenceID) {
        return ResponseEntity.ok(intelligenceService.findIntelligence(intelligenceID));
    }
}
