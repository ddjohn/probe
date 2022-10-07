
OBJECTS = \
	  client_truststore.bks server_truststore.bks client_identity.bks server_identity.bks \

	  #supicioustruststore.bks supicious_client.bks suspicoius_client_ca.bks suspicious_server.bks suspicious_server_ca.bks

all: $(OBJECTS)

client_key.pem:
	openssl genrsa -des3 -out client_key.pem -passout pass:summer 2048

server_key.pem:
	openssl genrsa -des3 -out server_key.pem -passout pass:winter 2048

client_certificate.pem: client_key.pem
	openssl req -new -x509 -key client_key.pem -passin pass:summer -out client_certificate.pem -days 365 \
	       	-subj "/C=SE/ST=VastraGotaland/L=Gothenburg/CN=Aptiv/O=ASUX/OU=David Johansson/emailAddress=david.johansson@aptiv.com" 

server_certificate.pem: server_key.pem
	openssl req -new -x509 -key server_key.pem -passin pass:winter -out server_certificate.pem -days 365 \
	       	-subj "/C=US/ST=California/L=MountainView/CN=Google/O=Automotive/OU=Bruno de Matino/emailAddress=brunodm@wastebasket.com" 

client_truststore.bks: server_certificate.pem
	keytool -importcert -noprompt -trustcacerts -keystore client_truststore.bks -storetype bks -storepass snowstorm -file server_certificate.pem \
	       	-provider org.bouncycastle.jce.provider.BouncyCastleProvider -providerpath bcprov-jdk18on-171.jar

server_truststore.bks: client_certificate.pem
	keytool -importcert -noprompt -trustcacerts -keystore server_truststore.bks -storetype bks -storepass rainstorm -file client_certificate.pem \
	       	-provider org.bouncycastle.jce.provider.BouncyCastleProvider -providerpath bcprov-jdk18on-171.jar

client_identity.p12: client_certificate.pem client_key.pem
	openssl pkcs12 -export -inkey client_key.pem -passin pass:summer -passout pass:purple -in client_certificate.pem -out client_identity.p12

server_identity.p12: server_certificate.pem server_key.pem 
	openssl pkcs12 -export -inkey server_key.pem -passin pass:winter -passout pass:magenta -in server_certificate.pem -out server_identity.p12

client_identity.bks: client_identity.p12
	keytool -importkeystore -noprompt -srckeystore client_identity.p12 -srcstoretype pkcs12 -srcstorepass purple \
		-deststorepass orange -destkeystore client_identity.bks -deststoretype bks \
	       	-provider org.bouncycastle.jce.provider.BouncyCastleProvider -providerpath bcprov-jdk18on-171.jar

server_identity.bks: server_identity.p12
	keytool -importkeystore -noprompt -srckeystore server_identity.p12 -srcstoretype pkcs12 -srcstorepass magenta \
		-deststorepass banana -destkeystore server_identity.bks -deststoretype bks \
	       	-provider org.bouncycastle.jce.provider.BouncyCastleProvider -providerpath bcprov-jdk18on-171.jar

####################################

suspicious_server.bks: suspicious_server.p12
	keytool -importkeystore -noprompt -srckeystore suspicious_server.p12 -srcstoretype pkcs12 -srcstorepass magenta \
		-deststorepass banana -destkeystore suspicious_server.bks -deststoretype bks \
	       	-provider org.bouncycastle.jce.provider.BouncyCastleProvider -providerpath bcprov-jdk18on-171.jar

suspicious_server_ca.bks: suspicious_server_ca.p12
	keytool -importkeystore -noprompt -srckeystore suspicious_server_ca.p12 -srcstoretype pkcs12 -srcstorepass magenta \
		-deststorepass banana -destkeystore suspicious_server_ca.bks -deststoretype bks \
	       	-provider org.bouncycastle.jce.provider.BouncyCastleProvider -providerpath bcprov-jdk18on-171.jar

suspicious_key.pem: 
	openssl genrsa -des3 -out suspicious_key.pem -passout pass:winter 2048

suspicious.pem: suspicious_key.pem
	openssl req -new -x509 -key suspicious_key.pem -passin pass:winter -out suspicious.pem -days 365 \
	       	-subj "/C=SE/ST=VastraGotaland/L=Gothenburg/CN=Aptiv/O=ASUX/OU=Magnus Alinder/emailAddress=magnus.alinder@aptiv.com" 

supicioustruststore.bks: suspicious.pem
	keytool -importcert -noprompt -trustcacerts -keystore suspicioustruststore.bks -storetype bks -storepass rainstorm -file suspicious.pem \
	       	-provider org.bouncycastle.jce.provider.BouncyCastleProvider -providerpath bcprov-jdk18on-171.jar

suspicious_client.p12: client.pem suspicious_key.pem
	openssl pkcs12 -export -inkey suspicious_key.pem -passin pass:summer -passout pass:purple -in client.pem -out suspicious_client.p12

suspicious_client_ca.p12: suspicious.pem client_key.pem 
	openssl pkcs12 -export -inkey client_key.pem -passin pass:summer -passout pass:purple -in suspicious.pem -out suspicious_client_ca.p12:

suspicious_server.p12: server.pem suspicious_key.pem
	openssl pkcs12 -export -inkey suspicious_key.pem -passin pass:winter -passout pass:magenta -in server.pem -out suspicious_server.p12

suspicious_server_ca.p12: suspicious.pem server_key.pem
	openssl pkcs12 -export -inkey server_key.pem -passin pass:winter -passout pass:magenta -in suspicious.pem -out suspicious_server_ca.p12

supicious_client.bks: suspicious_client.p12
	keytool -importkeystore -noprompt -srckeystore suspicious_client.p12 -srcstoretype pkcs12 -srcstorepass purple \
		-deststorepass orange -destkeystore supicious_client.bks -deststoretype bks \
	       	-provider org.bouncycastle.jce.provider.BouncyCastleProvider -providerpath bcprov-jdk18on-171.jar

suspicoius_client_ca.bks: suspicious_client_ca.p12
	keytool -importkeystore -noprompt -srckeystore suspicious_client_ca.p12 -srcstoretype pkcs12 -srcstorepass purple \
		-deststorepass orange -destkeystore suspicoius_client_ca.bks -deststoretype bks \
	       	-provider org.bouncycastle.jce.provider.BouncyCastleProvider -providerpath bcprov-jdk18on-171.jar

clean:
	rm -f $(OBJECTS)
	rm -f *.pem
	rm -f *.p12

