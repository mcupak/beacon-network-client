# Beacon Network Java Client
Java library for accessing Beacon Network API.

## Requirements
### Imported SSL Certificate
Beacon Network requires https for communication. Unfortunately, the ssl certificate currently used by the Beacon Network
is not present in the default Java keystore. Therefore, for the client to be working with the Beacon Network from your
machine, you need to add the certificate manually, otherwise the client will fail to establish a connection with the
Beacon Network.

Follow the next steps to download and add the certificate to your Java keystore.

1. Go to the [http://www.startssl.com/certs/ca.pem](http://www.startssl.com/certs/ca.pem) and download the file;
2. Locate the JRE you are using. You can do that by executing `java -XshowSettings:properties -version` and finding the
value of the `java.home` property in the output.
3. Execute the following to add the downloaded certificate: `keytool -import -keystore "PATH_TO_YOUR_JRE/lib/security/cacerts"
-storepass changeit -noprompt -alias StartComCertificate -file "PATH_TO_DOWNLOADED_CERTIFICATE_FOLDER/ca.pem"`. The
default Java keystore password `changeit` is used here. Use your password, if you've changed it.
4. If the certificate've been successfully added, you'll see the `Certificate was added to keystore` message.

### Beacon Network DTO
The Beacon Network client API project is dependent on the Beacon Network DTO project. So you need to have it built in
your local repository to successfully build the Beacon Network client.

To do that, you can execute the following commands:
```
git clone https://github.com/mcupak/beacon-network-dto
cd beacon-network-dto
mvn install
```