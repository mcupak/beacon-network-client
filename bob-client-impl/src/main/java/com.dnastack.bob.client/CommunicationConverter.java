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

package com.dnastack.bob.client;

import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * Responsible for any additional conversions of parameters/bodies of requests/responses.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class CommunicationConverter {
    /**
     * Converter is obtained from JacksonConverterFactory, which is thread-safe
     * and returns thread-safe converters. Can declare it static.
     */
    private static final retrofit2.Converter<ResponseBody, ErrorDto> ERROR_RESPONSE_CONVERTER =
            BeaconNetworkRetroServiceFactory.getResponseConverter(ErrorDto.class);

    private CommunicationConverter() {
    }

    public static ErrorDto convertToErrorDto(ResponseBody responseBody) throws IOException {
        return ERROR_RESPONSE_CONVERTER.convert(responseBody);
    }

    /**
     * Converts the list to the format required by Beacon Network - to the [n1,n2,...nN] format without whitespaces
     * between the elements. The default {@link Object#toString()} can not be used as it inserts whitespaces.
     */
    public static String convertToString(List<String> list) {
        if (list == null || list.size() == 0) {
            return null;
        }

        StringBuilder result = new StringBuilder("[");

        for (int i = 0; i < list.size() - 1; i++) {
            result.append(list.get(i)).append(",");
        }
        // append the last one
        result.append(list.get(list.size() - 1));
        result.append("]");

        return result.toString();
    }
}
