package com.example;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.xd.dirt.server.singlenode.SingleNodeApplication;
import org.springframework.xd.dirt.test.SingleNodeIntegrationTestSupport;
import org.springframework.xd.dirt.test.SingletonModuleRegistry;
import org.springframework.xd.dirt.test.process.SingleNodeProcessingChain;
import org.springframework.xd.module.ModuleType;
import org.springframework.xd.test.RandomConfigurationSupport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.xd.dirt.test.process.SingleNodeProcessingChainSupport.chain;

/**
 * Created by greguska on 4/4/16.
 */
public class UpperCaseProcessorIntegrationTest {

    private static SingleNodeApplication application;

    private static int RECEIVE_TIMEOUT = 5000;

    private static String moduleName = "upperCaser";

    SingleNodeProcessingChain chain;

    @BeforeClass
    public static void setUp() {
        new RandomConfigurationSupport();
        application = new SingleNodeApplication().run();
        SingleNodeIntegrationTestSupport singleNodeIntegrationTestSupport = new SingleNodeIntegrationTestSupport
                (application);
        singleNodeIntegrationTestSupport.addModuleRegistry(new SingletonModuleRegistry(ModuleType.processor,
                moduleName));

    }

    @Test
    public void testDefault() {

        String processingChainUnderTest = String.format("%s", moduleName);

        chain = chain(application, "testdefault", processingChainUnderTest);

        chain.sendPayload("hello");
        Object result = chain.receivePayload(RECEIVE_TIMEOUT);

        assertTrue(result instanceof String);
        assertEquals("HELLO", result.toString());
    }

    @After
    public void tearDown() {
        if (chain != null) {
            chain.destroy();
        }
    }
}
