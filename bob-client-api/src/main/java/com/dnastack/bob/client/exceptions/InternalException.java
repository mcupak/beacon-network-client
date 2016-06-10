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
 * This exception is thrown when the Beacon Network client fails to perform a requested operation due to, for example,
 * errors during communication to the server, unknown response from the server or any other internal errors.<p>
 * For operation specific errors, children of {@link ErrorStatusException} are thrown.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class InternalException extends Exception {
    public InternalException(String message) {
        super(message);
    }

    public InternalException(String message, Throwable cause) {
        super(message, cause);
    }
}
