package com.example.testcase;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.services.Service;
import org.apache.ignite.services.ServiceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestService implements Service {

    private static final Logger log = LoggerFactory.getLogger(TestService.class);

    @IgniteInstanceResource
    private Ignite ignite;

    private static IgniteCache<String, Integer> cache;

    @Override
    public void cancel(ServiceContext ctx) {
        log.info("Service canceled.");
    }

    @Override
    public void init(ServiceContext ctx) throws Exception {
        log.info("Service init!");
        cache = ignite.cache("cache");
    }

    @Override
    public void execute(ServiceContext ctx) throws Exception {
        log.info("Service execute!");
        Integer a = cache.get("a");
        if (a == null) {
            a = 0;
        }
        while (!ctx.isCancelled()) {
            Thread.sleep(1000);
            log.info("Service executing something important...");
            cache.put("a", a++);
        }
    }
}
