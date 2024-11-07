package org.hncdev.usom.client;

import org.hncdev.usom.dto.UsomResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "usom-client", url = "https://www.usom.gov.tr/api/address/index")
public interface UsomClient {

    @GetMapping
    UsomResponse getIntelligences(@RequestParam int page);
}
