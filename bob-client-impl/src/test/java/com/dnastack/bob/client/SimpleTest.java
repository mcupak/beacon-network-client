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
