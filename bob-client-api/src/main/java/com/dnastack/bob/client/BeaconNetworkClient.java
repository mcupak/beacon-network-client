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
 * All methods throw either business error (operation specific) exceptions or {@link InternalException}
 * when the client couldn't perform the requested operation correctly due to internal errors.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public interface BeaconNetworkClient {
    List<BeaconDto> getBeacons() throws ForbiddenException, InternalException;

    BeaconDto getBeacon(String beacon) throws ForbiddenException, NotFoundException, InternalException;

    List<OrganizationDto> getOrganizations() throws ForbiddenException, InternalException;

    OrganizationDto getOrganization(String organization) throws ForbiddenException, NotFoundException, InternalException;

    List<BeaconResponseDto> getResponses(List<String> beaconsIds, AlleleDto allele, ChromosomeDto chromosome,
                                         Long position, ReferenceDto reference) throws ForbiddenException,
            InternalException, NotFoundException;

    BeaconResponseDto getResponse(String beaconId, AlleleDto allele, ChromosomeDto chromosome, Long position,
                                  ReferenceDto reference) throws ForbiddenException, NotFoundException, InternalException;
}
