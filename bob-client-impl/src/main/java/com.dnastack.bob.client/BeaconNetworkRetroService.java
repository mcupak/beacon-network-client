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
public interface BeaconNetworkRetroService {
    String BEACONS_PATH = "beacons";
    String ORGANIZATIONS_PATH = "organizations";
    String RESPONSES_PATH = "responses";

    String BEACONS_IDS_LIST_KEY = "beacon";
    String CHROMOSOME_KEY = "chrom";
    String POSITION_KEY = "pos";
    String ALLELE_KEY = "allele";
    String REFERENCE_KEY = "ref";


    @GET(BEACONS_PATH)
    Call<List<BeaconDto>> getBeacons();

    @GET(BEACONS_PATH + "/{beaconId}")
    Call<BeaconDto> getBeacon(@Path("beaconId") String beaconId);

    @GET(ORGANIZATIONS_PATH)
    Call<List<OrganizationDto>> getOrganizations();

    @GET(ORGANIZATIONS_PATH + "/{organizationId}")
    Call<OrganizationDto> getOrganization(@Path("organizationId") String organizationId);

    @GET(RESPONSES_PATH)
    Call<List<BeaconResponseDto>> getResponses(@Query(CHROMOSOME_KEY) ChromosomeDto chromosome,
                                               @Query(POSITION_KEY) Long position,
                                               @Query(ALLELE_KEY) AlleleDto allele,
                                               @Query(REFERENCE_KEY) ReferenceDto reference,
                                               @Query(BEACONS_IDS_LIST_KEY) String beaconsIdsList);

    @GET(RESPONSES_PATH + "/{beaconId}")
    Call<BeaconResponseDto> getResponse(@Path("beaconId") String beaconId,
                                        @Query(CHROMOSOME_KEY) ChromosomeDto chromosome,
                                        @Query(POSITION_KEY) Long position,
                                        @Query(ALLELE_KEY) AlleleDto allele,
                                        @Query(REFERENCE_KEY) ReferenceDto reference);
}
