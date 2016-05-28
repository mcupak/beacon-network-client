package com.dnastack.bob.client;

import com.dnastack.bob.client.exceptions.ForbiddenException;
import com.dnastack.bob.client.exceptions.InternalException;
import com.dnastack.bob.client.exceptions.NotFoundException;
import com.dnastack.bob.service.dto.*;

import java.util.List;

/**
 * Beacon Network client.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public interface BeaconNetworkClient {
    List<BeaconDto> getBeacons() throws ForbiddenException, InternalException;

    BeaconDto getBeacon(String beacon) throws ForbiddenException, NotFoundException, InternalException;

    List<OrganizationDto> getOrganizations() throws ForbiddenException, InternalException;

    OrganizationDto getOrganization(String organization) throws ForbiddenException, NotFoundException, InternalException;

    List<BeaconResponseDto> getResponses() throws ForbiddenException, InternalException;

    BeaconResponseDto getResponse(String beacon) throws ForbiddenException, NotFoundException, InternalException;

    List<ChromosomeDto> getChromosomes() throws ForbiddenException, InternalException;

    List<AlleleDto> getAlleles() throws ForbiddenException, InternalException;

    List<ReferenceDto> getReferences() throws ForbiddenException, InternalException;
}
