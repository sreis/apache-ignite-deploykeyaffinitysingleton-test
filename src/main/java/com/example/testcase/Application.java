package com.example.testcase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(TestService.class);

    public static void test() throws Exception {

        final IgniteNode node = new IgniteNode();
        node.start();

        node.getIgnite().services().deployKeyAffinitySingleton("key", new TestService(), "cache", "key");

        Thread.sleep(1000);
        if (node.getIgnite().services().service("key") == null) {
            throw new RuntimeException("Service not deployed.");
        }

        Thread.sleep(1000);
        node.stop();
    }

    public static void standalone() throws Exception {
        log.info("Launching standalone node...");
        final IgniteNode node = new IgniteNode();
        node.start();
    }

    public static void main(String[] args) throws Exception {

        if ((args.length == 1) && (args[0].equals("standalone"))) {
            standalone();
        } else {
            test();
        }
    }
}
