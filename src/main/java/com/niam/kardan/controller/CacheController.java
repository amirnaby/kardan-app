package com.niam.kardan.controller;

import com.niam.common.model.response.ServiceResponse;
import com.niam.common.utils.ResponseEntityUtil;
import com.niam.kardan.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/cache")
public class CacheController {
    private final CacheService cacheService;
    private final ResponseEntityUtil responseEntityUtil;

    @GetMapping
    public ResponseEntity<ServiceResponse> getAllCaches() {
        List<String> caches = cacheService.getAllCacheNames();
        return responseEntityUtil.ok(caches);
    }

    @GetMapping("/stats")
    public ResponseEntity<ServiceResponse> getCacheStats() {
        Map<String, Integer> stats = cacheService.getCacheStats();
        return responseEntityUtil.ok(stats);
    }

    @DeleteMapping
    public ResponseEntity<ServiceResponse> clearAllCaches() {
        cacheService.clearAllCaches();
        return responseEntityUtil.ok("All caches cleared successfully");
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<ServiceResponse> clearCacheByName(@PathVariable String name) {
        cacheService.clearCacheByName(name);
        return responseEntityUtil.ok("Cache '" + name + "' cleared successfully");
    }

    @GetMapping("/{name}/keys")
    public ResponseEntity<ServiceResponse> getCacheKeys(@PathVariable String name) {
        List<Object> keys = cacheService.getCacheKeys(name);
        return responseEntityUtil.ok(keys);
    }
}