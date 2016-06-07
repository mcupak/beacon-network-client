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

import com.dnastack.bob.service.dto.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * Underlying Beacon Network API for Retrofit.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
interface BeaconNetworkRetroService {
    @GET("beacons")
    Call<List<BeaconDto>> getBeacons();

    @GET("beacons/{beaconId}")
    Call<BeaconDto> getBeacon(@Path("beaconId") String beaconId);

    @GET("organizations")
    Call<List<OrganizationDto>> getOrganizations();

    @GET("organizations/{organizationId}")
    Call<OrganizationDto> getOrganization(@Path("organizationId") String organizationId);

    @GET("responses")
    Call<List<BeaconResponseDto>> getResponses(@Query("beacon") String beaconsIdsList,
                                               @Query("chrom") ChromosomeDto chromosome, @Query("pos") Integer position,
                                               @Query("allele") AlleleDto allele, @Query("ref") ReferenceDto reference);

    @GET("responses/{beaconId}")
    Call<BeaconResponseDto> getResponse(@Path("beaconId") String beaconId, @Query("chrom") ChromosomeDto chromosome,
                                        @Query("pos") Integer position, @Query("allele") AlleleDto allele,
                                        @Query("ref") ReferenceDto reference);
}
