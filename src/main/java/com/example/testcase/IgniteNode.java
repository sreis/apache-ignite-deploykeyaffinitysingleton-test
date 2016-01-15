package com.example.testcase;


import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicWriteOrderMode;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IgniteNode {

    private static final Logger log = LoggerFactory.getLogger(IgniteNode.class);

    private Ignite ignite;
    private IgniteCache<String, Integer> cache;

    public void start() {
        log.info("Initializing Apache Ignite service..");

        // Ignite configuration
        final IgniteConfiguration igniteConfig = new IgniteConfiguration();

        // discovery
        final TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
        final TcpDiscoverySpi discovery = new TcpDiscoverySpi();
        ipFinder.setMulticastGroup("228.10.10.101");
        discovery.setIpFinder(ipFinder);

        igniteConfig.setDiscoverySpi(discovery);

        // disable metrics
        igniteConfig.setMetricsLogFrequency(0);

        // go, go Ignite
        ignite = Ignition.start(igniteConfig);

        // done
        log.info("Apache Ignite started. Setting up cache...");

        final CacheConfiguration<String, Integer> cacheConfig = new CacheConfiguration<>();
        cacheConfig.setCacheMode(CacheMode.PARTITIONED);
        cacheConfig.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        cacheConfig.setAtomicWriteOrderMode(CacheAtomicWriteOrderMode.PRIMARY);
        cacheConfig.setBackups(2);
        cacheConfig.setWriteSynchronizationMode(CacheWriteSynchronizationMode.PRIMARY_SYNC);
        cacheConfig.setStartSize(10^6); // 1 million
        cacheConfig.setOffHeapMaxMemory(0);
        cacheConfig.setSwapEnabled(false);

        cacheConfig.setName("cache");

        ignite.addCacheConfiguration(cacheConfig);
        cache = ignite.getOrCreateCache(cacheConfig);

        log.info("Cache setup complete.");
    }

    public void stop() {
        ignite.close();
    }

    public Ignite getIgnite() {
        return ignite;
    }
}
