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

package com.dnastack.bob.client.response

import com.dnastack.bob.client.BaseBobTest
import com.dnastack.bob.client.exceptions.ForbiddenException
import com.dnastack.bob.service.dto.AlleleDto
import com.github.tomakehurst.wiremock.common.Json
import org.apache.http.HttpStatus

import static com.dnastack.bob.client.BeaconNetworkRetroService.*
import static com.dnastack.bob.client.ITTestData.TEST_RESPONSE_AMPLAB
import static com.dnastack.bob.client.TestData.TEST_ERROR_FORBIDDEN
import static com.github.tomakehurst.wiremock.client.WireMock.*
import static org.assertj.core.api.Assertions.assertThat
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown

/**
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
class ResponseForbiddenTest extends BaseBobTest {
    @Override
    void setupMappings() {
        MOCK_BOB_SERVER.stubFor(get(urlPathEqualTo("/$RESPONSES_PATH/$TEST_RESPONSE_AMPLAB.beacon.id"))
                .withQueryParam(CHROMOSOME_KEY, equalTo(TEST_RESPONSE_AMPLAB.query.chromosome.toString()))
                .withQueryParam(POSITION_KEY, equalTo(TEST_RESPONSE_AMPLAB.query.position.toString()))
                .withQueryParam(ALLELE_KEY, equalTo(TEST_RESPONSE_AMPLAB.query.allele))
                .withQueryParam(REFERENCE_KEY, equalTo(TEST_RESPONSE_AMPLAB.query.reference.toString()))

                .willReturn(aResponse()
                .withStatus(HttpStatus.SC_FORBIDDEN)
                .withBody(Json.write(TEST_ERROR_FORBIDDEN))))
    }

    @Override
    boolean isIntegrationTestingSupported() {
        return false
    }

    @Override
    void doTest() {
        try {
            CLIENT.getResponse(
                    TEST_RESPONSE_AMPLAB.query.chromosome,
                    TEST_RESPONSE_AMPLAB.query.position,
                    AlleleDto.fromString(TEST_RESPONSE_AMPLAB.query.allele),
                    TEST_RESPONSE_AMPLAB.query.reference,
                    TEST_RESPONSE_AMPLAB.beacon.id)
            failBecauseExceptionWasNotThrown(ForbiddenException.class)
        } catch (Throwable thrown) {
            assertThat(thrown).isInstanceOf(ForbiddenException.class)
            assertThat(thrown).withFailMessage(TEST_ERROR_FORBIDDEN.message)
        }
    }
}
