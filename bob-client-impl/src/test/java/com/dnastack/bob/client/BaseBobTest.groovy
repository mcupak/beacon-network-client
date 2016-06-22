/*
 * Copyright 2016 Artem (tema.voskoboynick@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dnastack.bob.client

import com.github.tomakehurst.wiremock.WireMockServer
import org.apache.commons.lang.StringUtils
import org.testng.annotations.AfterMethod
import org.testng.annotations.AfterSuite
import org.testng.annotations.BeforeSuite
import org.testng.annotations.Test

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig

/**
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
abstract class BaseBobTest {
    static final def MOCK_BOB_PORT = 8089
    static final def MOCK_BOB_SERVER = new WireMockServer(wireMockConfig().port(MOCK_BOB_PORT))
    static final def CLIENT
    static final boolean MOCKED_TESTING

    /**
     * Define if the testing will be against real Beacon Network server, or the mocked one.
     */
    static {
        def beaconNetworkTestUrl = System.properties.getProperty("beaconNetwork.test.url")
        MOCKED_TESTING = StringUtils.isBlank(beaconNetworkTestUrl)
        CLIENT = MOCKED_TESTING ?
                new BeaconNetworkClientImpl(new URL("http", "localhost", MOCK_BOB_PORT, "")) :
                new BeaconNetworkClientImpl(beaconNetworkTestUrl)

    }

    @BeforeSuite
    void startServer() {
        if (MOCKED_TESTING) {
            MOCK_BOB_SERVER.start();
        }
    }

    @AfterSuite
    void stopServer() {
        if (MOCKED_TESTING) {
            MOCK_BOB_SERVER.stop();
        }
    }

    @AfterMethod
    void resetMappings() {
        if (MOCKED_TESTING) {
            MOCK_BOB_SERVER.resetMappings();
        }
    }

    @Test
    void test() {
        if (!MOCKED_TESTING && !isIntegrationTestingSupported()) {
            return
        }

        if (MOCKED_TESTING) {
            setupMappings()
        }
        doTest()
    }

    void setupMappings() {}

    boolean isIntegrationTestingSupported() { return true }

    abstract void doTest();
}
