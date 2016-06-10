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
import org.slf4j.LoggerFactory
import org.testng.annotations.AfterMethod
import org.testng.annotations.AfterSuite
import org.testng.annotations.BeforeSuite
import org.testng.annotations.Test

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig

/**
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
abstract class BaseMockedBobTest {
    static final def log = LoggerFactory.getLogger(getClass())

    static final def MOCK_BOB_PORT = 8089
    static final def MOCK_BOB_SERVER = new WireMockServer(
            wireMockConfig().port(MOCK_BOB_PORT)
    )
    static final def CLIENT = new BeaconNetworkClientImpl(
            new URL("http", "localhost", MOCK_BOB_PORT, "")
    )

    @BeforeSuite
    void startServer() {
        MOCK_BOB_SERVER.start();
    }

    @AfterSuite
    void stopServer() {
        MOCK_BOB_SERVER.stop();
    }

    @AfterMethod
    void resetMappings() {
        MOCK_BOB_SERVER.resetMappings();
    }

    @Test
    void test() {
        setupMappings()
        doTest()
    }

    void setupMappings() {}

    abstract void doTest();
}
