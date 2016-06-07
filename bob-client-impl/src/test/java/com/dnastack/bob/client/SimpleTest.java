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

import com.dnastack.bob.client.exceptions.ErrorStatusException;
import com.dnastack.bob.client.exceptions.InternalException;
import org.testng.annotations.Test;

/**
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class SimpleTest {
    @Test
    public void test() throws ErrorStatusException, InternalException {
        BeaconNetworkClient client = new BeaconNetworkClientImpl();
        client.getBeacons().forEach(System.out::println);
    }
}
