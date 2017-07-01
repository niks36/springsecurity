Test

openssl req -new -x509 -keyout ca-key -out ca-cert -days 365 -passin pass:123456 -passout pass:123456 -dname "CN=Common Namr,OU=Orgganization Unit,O=Origination,L=Ahmedabada,S=GJ,C=IN"
