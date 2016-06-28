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

/**
 * Business logic error statuses that are received in the http body from the server.
 * These statuses are resolved to the corresponding exceptions.
 * These statuses don't include any other error statuses, for example related to internal server errors or
 * mismatch between the Beacon Network client and the server workflow logic (for ex: different versions).
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public enum ErrorStatus {
    FORBIDDEN("403"),
    NOT_FOUND("404");

    private String id;

    public static ErrorStatus fromString(String id) {
        if (id != null) {
            for (ErrorStatus errorStatus : ErrorStatus.values()) {
                if (id.equalsIgnoreCase(errorStatus.id)) {
                    return errorStatus;
                }
            }
        }
        return null;
    }

    ErrorStatus(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }
}
