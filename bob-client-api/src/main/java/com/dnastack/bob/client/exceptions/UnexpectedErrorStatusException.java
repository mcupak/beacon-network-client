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

package com.dnastack.bob.client.exceptions;

/**
 * This exception is thrown when the Beacon Network client receives a recognized business error,
 * but didn't expect it from the requested operation.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class UnexpectedErrorStatusException extends InternalException {
    public UnexpectedErrorStatusException(String rawErrorStatus, String errorMessage) {
        super(String.format(
                "Received known, but unexpected error status from server. Status %s. Server message: %s.",
                rawErrorStatus, errorMessage));
    }
}
