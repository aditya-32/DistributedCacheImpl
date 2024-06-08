package com.example.DistributedCacheTest;

import com.example.DistributedCacheTest.enums.EvictionPolicyEnum;

import com.example.DistributedCacheTest.model.storage.MapStorage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootTest
class DistributedCacheTestApplicationTests {

    private static CacheManager<String, String> cacheManager;
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    @BeforeAll
    public static void setUp() {
        cacheManager = new CacheManager<>(EvictionPolicyEnum.LRU, new MapStorage<>());
    }

    @Test
    public void testGetKey() throws InterruptedException, ExecutionException {
        List<Callable<String>> callableList = new ArrayList<>();
        for (int i = 0; i < cacheManager.getCacheSize() + 3; i++) {
            int finalI = i;
            callableList.add(() -> cacheManager.put(String.valueOf(finalI), String.valueOf(finalI)));
        }
        List<Future<String>> futures = executor.invokeAll(callableList);
        for (Future<String> future : futures) {
            future.get();
        }
    }

    @AfterAll
    public static void cleanTest() {
        executor.shutdown();
    }
}
