
# SSL using Spring boot

This example shows how to set up client server communication using Spring boot security.

# Getting Started

   Very first step for using SSL is create a certificate.
   
   There is two very import things 
   * Keystore - contains private key
   * Truststore - contains public key of trusted entity

   There are different way to create certificate. You can find n number of ways to create it.
   Here, we are creating certificate with having CA between client and server.
   
# Generating CA Certificate
   
Here, we are creating self signed certificate.
Below are the steps to create CA certificate
```bash
DNAME_CA="CN=yourdomainname.com,O=Organization,L=Ahmedabad,C=IN"
CA_KEY_PASSWORD="123456"
CA_KEY_STORE_PASSWORD="123456"
CA_KEY_STORE="ca.keystore.jks"
CA_CERT="ca.cert"
CA_KEY="ca"

#Step1 -  Generating Key for CA
keytool -genkey -alias $CA_KEY -ext BC=ca:true -dname "$DNAME_CA" -keyalg RSA -keysize 4096 -sigalg SHA512withRSA -keypass "$CA_KEY_PASSWORD" -validity 3650 -keystore "$CA_KEY_STORE" -storepass "$CA_KEY_STORE_PASSWORD"

#Step2 - Export public key to certificate
keytool -export -alias $CA_KEY -file "$CA_CERT"  -dname "$DNAME_CA" -rfc -keystore "$CA_KEY_STORE" -storepass "$CA_KEY_STORE_PASSWORD"

```
    
## Generating Server Certificate 
```bash

DNAME="CN=yourdomainname.com,OU=Server,O=Organization,L=Ahmedabad,ST=Gujarat,C=IN"
SAN="dns:fqn.domain1.com,dns:fqn.domain2.com,dns:localhost"
KEY_STORE="server.keystore.jks"
TRUST_STORE="server.truststore.jks"
KEY_ALIAS="server-key"
KEY_PASSWORD="123456"
KEY_STORE_PASSWORD="123456"
TRUST_STORE_PASSWORD="123456"



# Step 1 - Generating Server key 
keytool -genkey -alias "$KEY_ALIAS" -dname "$DNAME" -keyalg RSA -keysize 4096 -sigalg SHA512withRSA -keypass "$KEY_PASSWORD" -validity 3650 -keystore "$KEY_STORE" -storepass "$KEY_STORE_PASSWORD"
# Step 2 - Request for a certificate
keytool -certreq -alias "$KEY_ALIAS" -ext BC=ca:true -ext "SAN=$SAN" -keyalg RSA -keysize 4096 -sigalg SHA512withRSA -keypass "$KEY_PASSWORD" -validity 3650 -keystore "$KEY_STORE" -storepass "$KEY_STORE_PASSWORD" -file server-request.csr
# Step 3 - Sign request cert with ca.cert
keytool -gencert -alias $CA_KEY -validity 3650 -sigalg SHA512withRSA -ext "SAN=$SAN" -infile server-request.csr -outfile server-response.crt -rfc -keypass "$CA_KEY_PASSWORD" -keystore "$CA_KEY_STORE" -storepass "$CA_KEY_STORE_PASSWORD"
# Step 4 - Import CA Cert to server keystore
keytool -import -trustcacerts -noprompt -alias $CA_KEY -file "$CA_CERT" -keystore "$KEY_STORE" -storepass "$KEY_STORE_PASSWORD"
# Step 5 - Import Signed certificate to Server keystore
keytool -import -trustcacerts -alias "$KEY_ALIAS" -file server-response.crt -keypass "$KEY_PASSWORD" -keystore "$KEY_STORE" -storepass "$KEY_STORE_PASSWORD"
# Step 6 - Import CA Cert to Server truststore
keytool -import -trustcacerts -noprompt -alias $CA_KEY -file "$CA_CERT" -keystore "$TRUST_STORE" -storepass "$TRUST_STORE_PASSWORD"
rm server-request.csr server-response.crt

```
## Generating Client Certificate
```bash
# Client Options
DNAME="CN=yourdomainname.com,OU=Client,O=Organization,L=Ahmedabad,ST=Gujarat,C=IN"
SAN="dns:fqn.domain1.com,dns:fqn.domain2.com,dns:localhost"
KEY_STORE="client.keystore.jks"
TRUST_STORE="client.truststore.jks"
KEY_ALIAS="client-key"
KEY_PASSWORD="123456"
KEY_STORE_PASSWORD="123456"
TRUST_STORE_PASSWORD="123456"

# Step 1 - Generating Client key 
keytool -genkey -alias "$KEY_ALIAS" -dname "$DNAME" -keyalg RSA -keysize 4096 -sigalg SHA512withRSA -keypass "$KEY_PASSWORD" -validity 3650 -keystore "$KEY_STORE" -storepass "$KEY_STORE_PASSWORD"
# Step 2 - Request for a certificate
keytool -certreq -alias "$KEY_ALIAS" -ext BC=ca:true -ext "SAN=$SAN" -keyalg RSA -keysize 4096 -sigalg SHA512withRSA -keypass "$KEY_PASSWORD" -validity 3650 -keystore "$KEY_STORE" -storepass "$KEY_STORE_PASSWORD" -file client-request.csr
# Step 3 - Sign request cert with ca.cert
keytool -gencert -alias $CA_KEY -validity 3650 -sigalg SHA512withRSA -ext "SAN=$SAN" -infile client-request.csr -outfile client-response.crt -rfc -keypass "$CA_KEY_PASSWORD" -keystore "$CA_KEY_STORE" -storepass "$CA_KEY_STORE_PASSWORD"
# Step 4 - Import CA Cert to client keystore
keytool -import -trustcacerts -noprompt -alias $CA_KEY -file "$CA_CERT" -keystore "$KEY_STORE" -storepass "$KEY_STORE_PASSWORD"
# Step 5 - Import Signed certificate to Client keystore
keytool -import -trustcacerts -alias "$KEY_ALIAS" -file client-response.crt -keypass "$KEY_PASSWORD" -keystore "$KEY_STORE" -storepass "$KEY_STORE_PASSWORD"
# Step 6 - Import CA Cert to Client truststore
keytool -import -trustcacerts -noprompt -alias $CA_KEY -file "$CA_CERT" -keystore "$TRUST_STORE" -storepass "$TRUST_STORE_PASSWORD"
rm client-request.csr client-response.crt

```

