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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.lang.annotation.Annotation;

/**
 * This class is responsible for configuring and creating actual Beacon Network http clients that will be communicating
 * to the server directly. These http clients are implemented by Retrofit at runtime.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class BeaconNetworkRetroServiceFactory {
    /**
     * JacksonConverterFactory is thread-safe. Can declare it static.
     */
    private static final JacksonConverterFactory JACKSON_CONVERTER_FACTORY = JacksonConverterFactory
            .create(new BeaconNetworkObjectMapper());

    /**
     * OkHttpClient is thread-safe. Can declare it static.
     */
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder().addNetworkInterceptor(chain -> {
        Request request = chain.request().newBuilder()
                .addHeader("Accept", "application/json")
                .build();
        return chain.proceed(request);
    }).build();

    private BeaconNetworkRetroServiceFactory() {
    }

    public static BeaconNetworkRetroService create(String serviceBaseUrl) {
        return new Retrofit.Builder()
                .client(HTTP_CLIENT)
                .addConverterFactory(JACKSON_CONVERTER_FACTORY)
                .baseUrl(serviceBaseUrl)
                .build()
                .create(BeaconNetworkRetroService.class);
    }

    public static <T> Converter<ResponseBody, T> getResponseConverter(Class<T> clazz) {
        //noinspection unchecked
        return (Converter<ResponseBody, T>) JACKSON_CONVERTER_FACTORY
                .responseBodyConverter(clazz, new Annotation[0], null);
    }

    private static final class BeaconNetworkObjectMapper extends ObjectMapper {
        public BeaconNetworkObjectMapper() {
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
    }
}
