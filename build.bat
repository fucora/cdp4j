mvn clean
mvn package -Duser.name=cdp4j
mvn reproducible-build:strip-jar
mvn deploy