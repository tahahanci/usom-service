package org.hncdev.usom.service.concretes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hncdev.usom.client.UsomClient;
import org.hncdev.usom.dto.IntelligenceDto;
import org.hncdev.usom.dto.UsomResponse;
import org.hncdev.usom.model.Intelligence;
import org.hncdev.usom.repository.IntelligenceRepository;
import org.hncdev.usom.service.IntelligenceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class IntelligenceServiceImpl implements IntelligenceService {

    private final IntelligenceRepository intelligenceRepository;
    private final UsomClient usomClient;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void save() {
        int currentPage = 0;
        int totalPage = 10;

        try {
            while (currentPage < totalPage) {
                int finalCurrentPage = currentPage;
                Callable<List<Intelligence>> task = () -> convertIntelligenceList(getIntelligenceDto(finalCurrentPage));
                ScheduledFuture<List<Intelligence>> future = scheduler.schedule(task, 2, TimeUnit.SECONDS);

                List<Intelligence> intelligences;
                try {
                    intelligences = future.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException("Error fetching intelligence data", e);
                }

                if (intelligences.isEmpty()) {
                    break;
                }

                List<Intelligence> newIntelligences = intelligences.stream()
                        .filter(intelligence -> !isIntelligenceExist(intelligence))
                        .toList();

                if (!newIntelligences.isEmpty()) {
                    intelligenceRepository.saveAll(newIntelligences);
                    log.info("Saved page {}", currentPage);
                } else {
                    log.info("No new records to save on page {}", currentPage);
                }

                currentPage++;
            }
        } finally {
            scheduler.shutdown();
        }
    }

    private boolean isIntelligenceExist(Intelligence intelligence) {
        return intelligenceRepository.findByIntelligenceID(intelligence.getIntelligenceID()).isPresent();
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
}
