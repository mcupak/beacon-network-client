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

import com.dnastack.bob.client.exceptions.ForbiddenException;
import com.dnastack.bob.client.exceptions.InternalException;
import com.dnastack.bob.client.exceptions.NotFoundException;
import com.dnastack.bob.service.dto.*;

import java.util.List;

/**
 * Beacon Network client API.
 * All methods throw either operation specific exceptions or {@link InternalException}.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public interface BeaconNetworkClient {
    /**
     * Lists available beacons.
     */
    List<BeaconDto> getBeacons() throws ForbiddenException, InternalException;

    /**
     * Shows information on the beacon specified by its id.
     */
    BeaconDto getBeacon(String beaconId) throws ForbiddenException, NotFoundException, InternalException;

    /**
     * Lists available organizations.
     */
    List<OrganizationDto> getOrganizations() throws ForbiddenException, InternalException;

    /**
     * Shows information on the organization specified by its id.
     */
    OrganizationDto getOrganization(String organizationId) throws ForbiddenException, NotFoundException, InternalException;

    /**
     * Requests specified beacons information on the specified genetic mutation.<p>
     * The response on the query will be put in {@link BeaconResponseDto#response}.<p>
     * {@link BeaconResponseDto#response} - true, when the response is YES.<p>
     * {@link BeaconResponseDto#response} - false or null, when the response is NO, or the beacon had problems answering
     * the query.
     *
     * @param beaconsIds ids of beacons to request. If not specified, all beacons will be requested.
     */
    List<BeaconResponseDto> getResponses(ChromosomeDto chromosome, Long position, AlleleDto allele,
                                         ReferenceDto reference, List<String> beaconsIds) throws ForbiddenException,
            InternalException, NotFoundException;

    /**
     * Requests all beacons information on the specified genetic mutation.<p>
     * The response on the query will be put in {@link BeaconResponseDto#response}.<p>
     * {@link BeaconResponseDto#response} - true, when the response is YES.<p>
     * {@link BeaconResponseDto#response} - false or null, when the response is NO, or the beacon had problems answering
     * the query.
     */
    BeaconResponseDto getResponse(ChromosomeDto chromosome, Long position, AlleleDto allele,
                                  ReferenceDto reference, String beaconId) throws ForbiddenException, NotFoundException,
            InternalException;
}
