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

package com.dnastack.bob.client.responses

import com.dnastack.bob.client.BaseBobTest
import com.dnastack.bob.service.dto.AlleleDto
import com.github.tomakehurst.wiremock.common.Json

import static com.dnastack.bob.client.BeaconNetworkRetroService.*
import static com.dnastack.bob.client.ITTestData.TEST_RESPONSES
import static com.github.tomakehurst.wiremock.client.WireMock.*
import static org.assertj.core.api.Assertions.assertThat

/**
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
class ResponsesAllBeaconsSuccessTest extends BaseBobTest {
    @Override
    void setupMappings() {
        MOCK_BOB_SERVER.stubFor(get(urlPathEqualTo("/$RESPONSES_PATH"))
                .withQueryParam(ALLELE_KEY, equalTo(TEST_RESPONSES.query.first().allele))
                .withQueryParam(CHROMOSOME_KEY, equalTo(TEST_RESPONSES.query.first().chromosome.toString()))
                .withQueryParam(POSITION_KEY, equalTo(TEST_RESPONSES.query.first().position.toString()))
                .withQueryParam(REFERENCE_KEY, equalTo(TEST_RESPONSES.query.first().reference.toString()))

                .willReturn(aResponse()
                .withBody(Json.write(TEST_RESPONSES))))

    }

    @Override
    void doTest() {
        def responses = CLIENT.getResponses(
                TEST_RESPONSES.query.first().chromosome,
                TEST_RESPONSES.query.first().position,
                AlleleDto.fromString(TEST_RESPONSES.query.first().allele),
                TEST_RESPONSES.query.first().reference,
                null)
        if (MOCKED_TESTING) {
            assertThat(responses).isEqualTo(TEST_RESPONSES)
        }
    }
}