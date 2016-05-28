package com.dnastack.bob.client;

import okhttp3.ResponseBody;
import retrofit2.Converter;

import java.io.IOException;

/**
 * This converter is responsible for converting raw response bodies to {@link ErrorDto}.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
class ErrorResponseConverter {
    /**
     * Converter is obtained from JacksonConverterFactory, which is thread-safe
     * and returns thread-safe converters. Can declare it static.
     */
    private static final Converter<ResponseBody, ErrorDto> ERROR_RESPONSE_CONVERTER =
            BeaconNetworkRetroServiceFactory.getResponseConverter(ErrorDto.class);

    public static ErrorDto convert(ResponseBody responseBody) throws IOException {
        return ERROR_RESPONSE_CONVERTER.convert(responseBody);
    }
}
