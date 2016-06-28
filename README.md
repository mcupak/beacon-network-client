# Beacon Network Client [![Build Status](https://travis-ci.org/mcupak/beacon-network-client.svg?branch=develop)](https://travis-ci.org/mcupak/beacon-network-client) [![GitHub license](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/mcupak/beacon-network-client/develop/LICENSE)
Java library for accessing Beacon Network API.

## Building
- Prerequisites: Java 8, Maven 3.
- Dependencies: [Beacon Network DTO](https://github.com/mcupak/beacon-network-dto).
- Building with Maven - in the root of the project, execute `mvn package`.

## Testing
- Unit tests are run with your default Maven profile:
```
mvn test
```

- Integration tests against a running Beacon Network service can be executed with the `it` profile (server URL defaults to `https://beacon-network.org/api/`):
```
mvn test -P it -DbeaconNetwork.test.url=[server_url].
```

## Usage
- Make sure Beacon Network certificate is in your keystore. Depending on your JDK, you might need to download and [import](https://docs.oracle.com/javase/tutorial/security/toolsign/rstep2.html) it.
- Add the relevant dependencies to your project:
```xml
<dependency>
<groupId>com.dnastack</groupId>
<artifactId>bob-client-api</artifactId>
<version>1.0-SNAPSHOT</version>
<scope>compile</scope>
</dependency>
<dependency>
<groupId>com.dnastack</groupId>
<artifactId>bob-client-impl</artifactId>
<version>1.0-SNAPSHOT</version>
<!-- runtime in container-managed environment -->
<scope>compile</scope>
</dependency>
```

- Execute requests through an instance of `BeaconNetworkClient`. Example (BRCA2 query):
```java
BeaconNetworkClient bnc = new BeaconNetworkClientImpl();
BeaconResponseDto response = bnc.getResponse(ChromosomeDto.CHR13, 32936732L, AlleleDto.C, ReferenceDto.HG19, "amplab");
```

## Documentation
- [Javadoc](https://mcupak.github.io/beacon-network-client/)
