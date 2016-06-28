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
import com.dnastack.bob.client.exceptions.UnexpectedErrorStatusException;
import com.dnastack.bob.service.dto.*;
import com.google.common.base.Preconditions;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Beacon Network client implementation.
 * The implementation is thread-safe except one operation - change of the service base url.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class BeaconNetworkClientImpl implements BeaconNetworkClient {

    public static final String DEFAULT_SERVICE_BASE_URL = "https://beacon-network.org/api/";

    private BeaconNetworkRetroService beaconNetworkRetroService;

    private static class CallResult<T> {

        T okDto;
        ErrorStatus errorStatus;
        String rawErrorStatus;
        String errorMessage;

        static <T> CallResult<T> ok(T okDto) {
            CallResult<T> result = new CallResult<>();
            result.okDto = okDto;
            return result;
        }

        static <T> CallResult<T> error(ErrorStatus errorStatus, String rawErrorStatus, String errorMessage) {
            CallResult<T> result = new CallResult<>();
            result.errorStatus = errorStatus;
            result.rawErrorStatus = rawErrorStatus;
            result.errorMessage = errorMessage;
            return result;
        }

        boolean isSuccessful() {
            return errorStatus == null;
        }
    }

    /**
     * Executes the call and expects either a successful response or the forbidden error status.<p>
     * Otherwise {@link InternalException} is thrown.
     */
    private static <T> T executeCallNoErrorsOrForbidden(Call<T> call) throws ForbiddenException, InternalException {
        CallResult<T> callResult = executeCall(call);
        if (callResult.isSuccessful()) {
            return callResult.okDto;
        } else if (ErrorStatus.FORBIDDEN == callResult.errorStatus) {
            throw new ForbiddenException(callResult.errorMessage);
        } else {
            throw new UnexpectedErrorStatusException(callResult.rawErrorStatus, callResult.errorMessage);
        }
    }

    /**
     * Performs actual communication to the server.<p>
     * Any errors during communication and DTOs conversion are wrapped and thrown in {@link InternalException}.<p>
     * In case of error responses, empty and unrecognized ones result in {@link InternalException},
     * the others are considered to be business logic specific and left to be processed by the caller.
     */
    private static <T> CallResult<T> executeCall(Call<T> call) throws InternalException {
        Response<T> response;
        try {
            response = call.execute();
        } catch (IOException | RuntimeException e) {
            throw new InternalException("Error during communication to server.", e);
        }

        if (response.isSuccessful()) {
            return CallResult.ok(response.body());
        } else {
            return createRecognizedErrorResult(response.errorBody());
        }
    }

    /**
     * Checks that the error response contains error status and that this status is known to the client.
     * Otherwise {@link InternalException} is thrown.
     */
    private static <T> CallResult<T> createRecognizedErrorResult(ResponseBody errorBody) throws InternalException {
        ErrorDto errorDto = convertToErrorDto(errorBody);

        if (errorDto == null) {
            throw new InternalException("Received neither successful response nor error status from server.");
        }

        ErrorStatus errorStatus = ErrorStatus.fromString(errorDto.getStatus());
        if (errorStatus == null) {
            String message = String.format(
                    "Received unrecognized error status from server. Status %s. Server message: %s.",
                    errorDto.getStatus(),
                    errorDto.getMessage());
            throw new InternalException(message);
        }

        return CallResult.error(errorStatus, errorDto.getStatus(), errorDto.getMessage());
    }

    private static ErrorDto convertToErrorDto(ResponseBody responseBody) throws InternalException {
        try {
            return CommunicationConverter.convertToErrorDto(responseBody);
        } catch (IOException e) {
            throw new InternalException("Could not convert server response to error dto", e);
        }
    }

    /**
     * Creates a Beacon Network client with the default
     * service base url: {@value BeaconNetworkClientImpl#DEFAULT_SERVICE_BASE_URL}.
     */
    public BeaconNetworkClientImpl() {
        setServiceBaseUrl(DEFAULT_SERVICE_BASE_URL);
    }

    /**
     * Creates a Beacon Network Client with the specified service base url.
     *
     * @param serviceBaseUrl service base url.
     */
    public BeaconNetworkClientImpl(String serviceBaseUrl) {
        setServiceBaseUrl(serviceBaseUrl);
    }

    /**
     * Creates a Beacon Network Client with the specified service base url.
     *
     * @param serviceBaseUrl service base url.
     */
    public BeaconNetworkClientImpl(URL serviceBaseUrl) {
        Preconditions.checkNotNull(serviceBaseUrl, "serviceBaseUrl URL mustn't be null");
        setServiceBaseUrl(serviceBaseUrl.toString());
    }

    /**
     * Sets the service base url the client should be talking to.<p>
     * Note, this operation is not thread-safe.
     *
     * @param serviceBaseUrl - service base url.
     */
    public void setServiceBaseUrl(String serviceBaseUrl) {
        Preconditions.checkArgument(StringUtils.isNotBlank(serviceBaseUrl), "serviceBaseUrl mustn't be null or empty.");
        beaconNetworkRetroService = BeaconNetworkRetroServiceFactory.create(serviceBaseUrl);
    }

    @Override
    public List<BeaconDto> getBeacons() throws ForbiddenException, InternalException {
        return executeCallNoErrorsOrForbidden(beaconNetworkRetroService.getBeacons());
    }

    @Override
    public List<OrganizationDto> getOrganizations() throws ForbiddenException, InternalException {
        return executeCallNoErrorsOrForbidden(beaconNetworkRetroService.getOrganizations());
    }

    @Override
    public BeaconDto getBeacon(String beaconId) throws ForbiddenException, NotFoundException, InternalException {
        Preconditions.checkArgument(StringUtils.isNotBlank(beaconId), "Beacon id mustn't be null or empty.");

        CallResult<BeaconDto> callResult = executeCall(beaconNetworkRetroService.getBeacon(beaconId));
        if (callResult.isSuccessful()) {
            return callResult.okDto;
        }

        switch (callResult.errorStatus) {
            case FORBIDDEN:
                throw new ForbiddenException(callResult.errorMessage);
            case NOT_FOUND:
                throw new NotFoundException(callResult.errorMessage);
            default:
                throw new UnexpectedErrorStatusException(callResult.rawErrorStatus, callResult.errorMessage);
        }
    }

    @Override
    public OrganizationDto getOrganization(String organizationId) throws ForbiddenException, NotFoundException, InternalException {
        Preconditions.checkArgument(StringUtils.isNotBlank(organizationId),
                                    "Organization id mustn't be null or empty.");

        CallResult<OrganizationDto> callResult = executeCall(beaconNetworkRetroService.getOrganization(organizationId));
        if (callResult.isSuccessful()) {
            return callResult.okDto;
        }

        switch (callResult.errorStatus) {
            case FORBIDDEN:
                throw new ForbiddenException(callResult.errorMessage);
            case NOT_FOUND:
                throw new NotFoundException(callResult.errorMessage);
            default:
                throw new UnexpectedErrorStatusException(callResult.rawErrorStatus, callResult.errorMessage);
        }
    }

    @Override
    public BeaconResponseDto getResponse(ChromosomeDto chromosome, Long position, AlleleDto allele, ReferenceDto reference, String beaconId) throws ForbiddenException, NotFoundException, InternalException {
        Preconditions.checkNotNull(chromosome, "Chromosome mustn't be null or empty.");
        Preconditions.checkNotNull(position, "Position mustn't be null or empty.");
        Preconditions.checkNotNull(allele, "Allele mustn't be null or empty.");
        Preconditions.checkNotNull(reference, "Reference mustn't be null or empty.");
        Preconditions.checkArgument(StringUtils.isNotBlank(beaconId), "Beacon id mustn't be null or empty.");

        CallResult<BeaconResponseDto> callResult = executeCall(beaconNetworkRetroService.getResponse(beaconId,
                                                                                                     chromosome,
                                                                                                     position,
                                                                                                     allele,
                                                                                                     reference));
        if (callResult.isSuccessful()) {
            return callResult.okDto;
        }

        switch (callResult.errorStatus) {
            case FORBIDDEN:
                throw new ForbiddenException(callResult.errorMessage);
            case NOT_FOUND:
                throw new NotFoundException(callResult.errorMessage);
            default:
                throw new UnexpectedErrorStatusException(callResult.rawErrorStatus, callResult.errorMessage);
        }
    }

    @Override
    public List<BeaconResponseDto> getResponses(ChromosomeDto chromosome, Long position, AlleleDto allele, ReferenceDto reference, List<String> beaconsIds) throws ForbiddenException, InternalException, NotFoundException {
        Preconditions.checkNotNull(chromosome, "Chromosome mustn't be null or empty.");
        Preconditions.checkNotNull(position, "Position mustn't be null or empty.");
        Preconditions.checkNotNull(allele, "Allele mustn't be null or empty.");
        Preconditions.checkNotNull(reference, "Reference mustn't be null or empty.");

        String beaconsIdsList = CommunicationConverter.convertToString(beaconsIds);
        CallResult<List<BeaconResponseDto>> callResult = executeCall(beaconNetworkRetroService.getResponses(chromosome,
                                                                                                            position,
                                                                                                            allele,
                                                                                                            reference,
                                                                                                            beaconsIdsList));
        if (callResult.isSuccessful()) {
            return callResult.okDto;
        }

        switch (callResult.errorStatus) {
            case FORBIDDEN:
                throw new ForbiddenException(callResult.errorMessage);
            case NOT_FOUND:
                throw new NotFoundException(callResult.errorMessage);
            default:
                throw new UnexpectedErrorStatusException(callResult.rawErrorStatus, callResult.errorMessage);
        }
    }
}