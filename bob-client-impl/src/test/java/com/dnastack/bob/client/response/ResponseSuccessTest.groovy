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
import com.dnastack.bob.service.dto.AlleleDto
import com.github.tomakehurst.wiremock.common.Json

import static com.dnastack.bob.client.BeaconNetworkRetroService.*
import static com.dnastack.bob.client.ITTestData.TEST_RESPONSE_AMPLAB
import static com.github.tomakehurst.wiremock.client.WireMock.*
import static org.assertj.core.api.Assertions.assertThat

/**
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
class ResponseSuccessTest extends BaseBobTest {
    @Override
    void setupMappings() {
        MOCK_BOB_SERVER.stubFor(get(urlPathEqualTo("/$RESPONSES_PATH/$TEST_RESPONSE_AMPLAB.beacon.id"))
                .withQueryParam(CHROMOSOME_KEY, equalTo(TEST_RESPONSE_AMPLAB.query.chromosome.toString()))
                .withQueryParam(POSITION_KEY, equalTo(TEST_RESPONSE_AMPLAB.query.position.toString()))
                .withQueryParam(ALLELE_KEY, equalTo(TEST_RESPONSE_AMPLAB.query.allele))
                .withQueryParam(REFERENCE_KEY, equalTo(TEST_RESPONSE_AMPLAB.query.reference.toString()))

                .willReturn(aResponse()
                .withBody(Json.write(TEST_RESPONSE_AMPLAB))))

    }

    @Override
    void doTest() {
        def response = CLIENT.getResponse(
                TEST_RESPONSE_AMPLAB.query.chromosome,
                TEST_RESPONSE_AMPLAB.query.position,
                AlleleDto.fromString(TEST_RESPONSE_AMPLAB.query.allele),
                TEST_RESPONSE_AMPLAB.query.reference,
                TEST_RESPONSE_AMPLAB.beacon.id)
        assertThat(response).isEqualTo(TEST_RESPONSE_AMPLAB);
    }
}
