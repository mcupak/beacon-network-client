package com.dnastack.bob.client;

import com.dnastack.bob.client.exceptions.ForbiddenException;
import com.dnastack.bob.client.exceptions.InternalException;
import com.dnastack.bob.client.exceptions.NotFoundException;
import com.dnastack.bob.client.exceptions.UnexpectedErrorStatusException;
import com.dnastack.bob.service.dto.*;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
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
     * Sets the service base url the client should be talking to.<p>
     * Note, this operation is not thread-safe.
     *
     * @param serviceBaseUrl - service base url.
     */
    public void setServiceBaseUrl(String serviceBaseUrl) {
        beaconNetworkRetroService = BeaconNetworkRetroServiceFactory.create(serviceBaseUrl);
    }

    public List<BeaconDto> getBeacons() throws ForbiddenException, InternalException {
        return executeCallNoErrorsOrForbidden(beaconNetworkRetroService.getBeacons());
    }

    public List<OrganizationDto> getOrganizations() throws ForbiddenException, InternalException {
        return executeCallNoErrorsOrForbidden(beaconNetworkRetroService.getOrganizations());
    }

    public List<BeaconResponseDto> getResponses() throws ForbiddenException, InternalException {
        return executeCallNoErrorsOrForbidden(beaconNetworkRetroService.getResponses());
    }

    public List<ChromosomeDto> getChromosomes() throws ForbiddenException, InternalException {
        return executeCallNoErrorsOrForbidden(beaconNetworkRetroService.getChromosomes());
    }

    public List<AlleleDto> getAlleles() throws ForbiddenException, InternalException {
        return executeCallNoErrorsOrForbidden(beaconNetworkRetroService.getAlleles());
    }

    public List<ReferenceDto> getReferences() throws ForbiddenException, InternalException {
        return executeCallNoErrorsOrForbidden(beaconNetworkRetroService.getReferences());
    }

    public BeaconDto getBeacon(String beacon) throws ForbiddenException, NotFoundException, InternalException {
        CallResult<BeaconDto> callResult = executeCall(beaconNetworkRetroService.getBeacon(beacon));
        if (callResult.isSuccessful()) {
            return callResult.okDto;
        }

        switch (callResult.errorStatus) {
            case NOT_FOUND:
                throw new NotFoundException(callResult.errorMessage);
            default:
                throw new UnexpectedErrorStatusException(callResult.rawErrorStatus, callResult.errorMessage);
        }
    }

    public OrganizationDto getOrganization(String organization) throws ForbiddenException, NotFoundException,
            InternalException {
        CallResult<OrganizationDto> callResult = executeCall(beaconNetworkRetroService.getOrganization(organization));
        if (callResult.isSuccessful()) {
            return callResult.okDto;
        }

        switch (callResult.errorStatus) {
            case NOT_FOUND:
                throw new NotFoundException(callResult.errorMessage);
            default:
                throw new UnexpectedErrorStatusException(callResult.rawErrorStatus, callResult.errorMessage);
        }
    }

    public BeaconResponseDto getResponse(String response) throws ForbiddenException, NotFoundException,
            InternalException {
        CallResult<BeaconResponseDto> callResult = executeCall(beaconNetworkRetroService.getResponse(response));
        if (callResult.isSuccessful()) {
            return callResult.okDto;
        }

        switch (callResult.errorStatus) {
            case NOT_FOUND:
                throw new NotFoundException(callResult.errorMessage);
            default:
                throw new UnexpectedErrorStatusException(callResult.rawErrorStatus, callResult.errorMessage);
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

    private static <T> CallResult<T> createRecognizedErrorResult(ResponseBody errorBody) throws InternalException {
        ErrorDto errorDto = convertToErrorDto(errorBody);

        if (errorDto == null) {
            throw new InternalException("Received neither successful response nor error status from server.");
        }

        ErrorStatus errorStatus = ErrorStatus.fromString(errorDto.getStatus());
        if (errorStatus == null) {
            String message = String.format(
                    "Received unrecognized error status from server. Status %s. Server message: %s.",
                    errorDto.getStatus(), errorDto.getMessage());
            throw new InternalException(message);
        }

        return CallResult.error(errorStatus, errorDto.getStatus(), errorDto.getMessage());
    }

    private static ErrorDto convertToErrorDto(ResponseBody responseBody) throws InternalException {
        try {
            return ErrorResponseConverter.convert(responseBody);
        } catch (IOException e) {
            throw new InternalException("Could not convert the response to error dto", e);
        }
    }

    private static class CallResult<T> {
        T okDto;
        ErrorStatus errorStatus;
        String rawErrorStatus;
        String errorMessage;

        boolean isSuccessful() {
            return errorStatus == null;
        }

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
    }
}