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

package com.dnastack.bob.client.organization

import com.dnastack.bob.client.BaseMockedBobTest
import com.dnastack.bob.client.exceptions.ForbiddenException
import com.github.tomakehurst.wiremock.common.Json
import org.apache.http.HttpStatus

import static com.dnastack.bob.client.BeaconNetworkRetroService.ORGANIZATIONS_PATH
import static com.dnastack.bob.client.TestData.TEST_ERROR_FORBIDDEN
import static com.dnastack.bob.client.TestData.TEST_ORGANIZATION_AMPLAB
import static com.github.tomakehurst.wiremock.client.WireMock.*
import static org.assertj.core.api.Assertions.assertThat
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown

/**
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
class OrganizationForbiddenTest extends BaseMockedBobTest {
    void setupMappings() {
        MOCK_BOB_SERVER.stubFor(get(urlEqualTo("/$ORGANIZATIONS_PATH/$TEST_ORGANIZATION_AMPLAB.id"))

                .willReturn(aResponse()
                .withStatus(HttpStatus.SC_FORBIDDEN)
                .withBody(Json.write(TEST_ERROR_FORBIDDEN))))
    }

    void doTest() {
        try {
            CLIENT.getOrganization(TEST_ORGANIZATION_AMPLAB.id)
            failBecauseExceptionWasNotThrown(ForbiddenException.class)
        } catch (Throwable thrown) {
            assertThat(thrown).isInstanceOf(ForbiddenException.class)
            assertThat(thrown).withFailMessage(TEST_ERROR_FORBIDDEN.message)
        }
    }
}

