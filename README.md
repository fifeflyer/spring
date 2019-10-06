# spring-hibernate-jpa

A proof of concept using Spring 4 (MVC), Hibernate 5, JPA (2.2 API) and H2.

## Creating an Oracle Docker Image

1. Download [oracle-database-xe-18c-1.0-1.x86_64.rpm](https://www.oracle.com/technetwork/database/database-technologies/express-edition/downloads/index.html)
1. Setup docker network: 
```docker network create oracle_network```
1. Create a folder ```mkdir ~/docker/oracle-xe``` which will store your Oracle XE data to be preserved after the container is destroyed.
1. ```git clone git@github.com:fuzziebrain/docker-oracle-xe.git```
1. ```cd docker-oracle-xe/```
1. ```cp ~/Downloads/oracle-database-xe-18c-1.0-1.x86_64.rpm files/```
1. ```docker build -t oracle-xe:18c .```
1. Run the container:
    ```
    docker run -d \
      -p 32118:1521 \
      -p 35518:5500 \
      --name=oracle-xe \
      --volume ~/docker/oracle-xe:/opt/oracle/oradata \
      --network=oracle_network \
      oracle-xe:18c
    ```
1. As this takes a long time to run you can keep track of the initial installation by running:
```docker logs oracle-xe```
1. ```docker ps```
1. ```docker start oracle-xe```
1. ```docker stop -t 200 oracle-xe```
1. ```sqlplus sys/Oracle18@localhost:32118/XE as sysdba```

### Creating tne Schema (User)
```
ALTER SESSION SET "_ORACLE_SCRIPT"=true;

CREATE USER bcrm IDENTIFIED BY demo;

GRANT CREATE SESSION TO bcrm WITH ADMIN OPTION;

GRANT CREATE TABLE TO bcrm;

GRANT SELECT, INSERT, UPDATE, DELETE ON bcrm.account TO bcrm;

ALTER USER bcrm QUOTA UNLIMITED ON users;
```
## Useful Links

* [Using Dozer](https://howtodoinjava.com/automation/dozer-bean-mapping-examples/)
* [Docker for Oracle XE](https://github.com/fuzziebrain/docker-oracle-xe)
* [Hibernate Logging Guide](https://docs.jboss.org/hibernate/orm/5.1/topical/html_single/logging/Logging.html)
* [JDBC Driver Connections](https://vladmihalcea.com/jdbc-driver-connection-url-strings/)
* [Maven OJDBC Driver](https://www.onlinetutorialspoint.com/maven/how-to-add-ojdbc-jar-to-maven-repository.html)
* [Spring Profile Properties](http://www.littlebigextra.com/how-to-read-different-properties-file-based-on-spring-profile-in-a-spring-mvc-project/)