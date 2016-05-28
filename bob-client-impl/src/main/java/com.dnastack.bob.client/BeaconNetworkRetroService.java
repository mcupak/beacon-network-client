package com.dnastack.bob.client;

import com.dnastack.bob.service.dto.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

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

    @GET("beacons/{beacon}")
    Call<BeaconDto> getBeacon(@Path("beacon") String beacon);

    @GET("organizations")
    Call<List<OrganizationDto>> getOrganizations();

    @GET("organizations/{organization}")
    Call<OrganizationDto> getOrganization(@Path("organization") String organization);

    @GET("responses")
    Call<List<BeaconResponseDto>> getResponses();

    @GET("responses/{beacon}")
    Call<BeaconResponseDto> getResponse(@Path("beacon") String beacon);

    @GET("chromosomes")
    Call<List<ChromosomeDto>> getChromosomes();

    @GET("alleles")
    Call<List<AlleleDto>> getAlleles();

    @GET("references")
    Call<List<ReferenceDto>> getReferences();
}
