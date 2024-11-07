package org.hncdev.usom.service.concretes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hncdev.usom.client.UsomClient;
import org.hncdev.usom.dto.IntelligenceDto;
import org.hncdev.usom.dto.UsomResponse;
import org.hncdev.usom.model.Intelligence;
import org.hncdev.usom.repository.IntelligenceRepository;
import org.hncdev.usom.service.IntelligenceService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class IntelligenceServiceImpl implements IntelligenceService {

    private final IntelligenceRepository intelligenceRepository;
    private final UsomClient usomClient;

    @Override
    @Scheduled(cron = "0 0 8 1/1 * ? *")
    public void save() {
        IntStream.rangeClosed(1, 10).parallel().forEach(currentPage -> {
            try {
                List<IntelligenceDto> intelligenceDtos = getIntelligenceDto(currentPage);
                if (intelligenceDtos.isEmpty())
                    return;

                List<Intelligence> intelligences = convertIntelligenceList(intelligenceDtos);
                Set<Long> existingIDs = getIntelligenceIDs(intelligences);

                List<Intelligence> newIntelligences = filterNewIntelligences(intelligences, existingIDs);

                if (!newIntelligences.isEmpty()) {
                    intelligenceRepository.saveAll(newIntelligences);
                }

                log.info("Intelligence data saved successfully. Page: {}", currentPage);

            } catch (Exception e) {
                log.error("An error occurred while saving intelligence data. Page: {}", currentPage, e);
            }
        });
    }

    private List<IntelligenceDto> getIntelligenceDto(int page) {
        UsomResponse usomResponse = usomClient.getIntelligences(page);
        return usomResponse.models();
    }


    private List<Intelligence> convertIntelligenceList(List<IntelligenceDto> intelligenceDtos) {
        return intelligenceDtos.stream()
                .map(IntelligenceDto::convert)
                .toList();
    }

    private Set<Long> getIntelligenceIDs(List<Intelligence> intelligences) {
        Set<Long> ids = intelligences.stream()
                .map(Intelligence::getIntelligenceID)
                .collect(Collectors.toSet());

        return intelligenceRepository.findExistingIds(ids);
    }

    private List<Intelligence> filterNewIntelligences(List<Intelligence> intelligences, Set<Long> existingIDs) {
        return intelligences.stream()
                .filter(intelligence -> !existingIDs.contains(intelligence.getIntelligenceID()))
                .toList();
    }

}
