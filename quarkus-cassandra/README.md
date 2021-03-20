# Quarkus-cassandra

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Creating the application
```
mvn io.quarkus:quarkus-maven-plugin:1.12.2.Final:create \
    -DprojectGroupId=org.quarkus.cassandra \
    -DprojectArtifactId=quarkus-cassandra \
    -DprojectVersion=1.0-SNAPSHOT \
    -DclassName="org.quarkus.mongo.controller.PersonController" \
    -Dextensions="rest-assured,resteasy-jsonb,cassandra-quarkus-client" 
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

## Packaging the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.


## Setup and run the cassandra in the container mode  
* Run the cassandra container
```
$ docker network create cassandra-network
$ docker run --name cassandra -p 9042:9042 --network cassandra-network -d cassandra
```
* Find the cassandra container
```
$ docker ps | grep cassandra
25b78366a590        cassandra           "docker-entrypoint.s…"   32 minutes ago      Up 32 minutes       7000-7001/tcp, 7199/tcp, 9160/tcp, 0.0.0.0:9042->9042/tcp   cassandra
```
* Create the keyspace and table inside the cassandra container
```
$ docker exec -it cassandra bash
root@2be15b7055dc:/# cqlsh
Connected to Test Cluster at 127.0.0.1:9042.
[cqlsh 5.0.1 | Cassandra 3.11.10 | CQL spec 3.4.4 | Native protocol v4]
Use HELP for help.
cqlsh> 
cqlsh> CREATE KEYSPACE IF NOT EXISTS testkeyspace WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = true;
cqlsh> DROP TABLE IF EXISTS testkeyspace.person;
cqlsh> CREATE TABLE IF NOT EXISTS testkeyspace.person (
   ...     id int,
   ...     name text,
   ...     gender text,
   ...     age int,
   ...     PRIMARY KEY (id)
   ... );
cqlsh> INSERT into testkeyspace.person(id, name, age,gender) values (1,'Kelly',34,'Male');
cqlsh> 
cqlsh> 
cqlsh> use testkeyspace;
cqlsh> 
cqlsh:testkeyspace> select * from person;

 id | age | gender | name
----+-----+--------+-------
  1 |  34 |   Male | Kelly

(1 rows)
cqlsh:testkeyspace> 
```

## Properties to connect to cassandra database
* For local instance
```
quarkus.cassandra.contact-points=127.0.0.1:9042
quarkus.cassandra.local-datacenter=datacenter1
quarkus.cassandra.keyspace=testkeyspace
```

* For cassandra running in cloud
```
#A sample configuration for DataStax Astra should look like this:
#quarkus.cassandra.cloud.secure-connect-bundle=/path/to/secure-connect-bundle.zip
#quarkus.cassandra.auth.username=john
#quarkus.cassandra.auth.password=s3cr3t
#quarkus.cassandra.keyspace=k1
```


# Querying the cassandra from app
* With CqlSession
```
@Inject
QuarkusCqlSession session;

public Optional<Person> getPersonsById(Integer id) {
    ResultSet execute = session.execute("Select * from testkeyspace.person  where id="+id);
    Row row = execute.one();
    Person p = null;
    if(row != null) {
        p = new Person(row.getInt("id"),
                row.getString("name"),
                row.getString("gender"),
                row.getInt("age"));
    }
    return Optional.ofNullable(p);
}

```
* Without CqlSession
```
@Dao
public interface PersonDao {

    @Select(allowFiltering = true)
    PagingIterable<Person> getAllPersons();

    @Select(allowFiltering = true,customWhereClause = "name=:name")
    Optional<Person> getPersonsByName(@CqlName("name") final String name);

    @Insert
    void save(Person person);

    @Delete(entityClass = Person.class,
            customWhereClause = " id=:id")
    void deleteByName(@CqlName("id") final Integer id);

}
```


## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-cassandra-1.0-SNAPSHOT-runner`

## Adding metrics and health dependency
```
./mvnw quarkus:add-extension -Dextensions="smallrye-health, smallrye-metrics"
```

## Metrics and health properties
```
#Metric Properties
#URL = http://localhost:8080/q/metrics
quarkus.cassandra.metrics.enabled=true
quarkus.cassandra.metrics.session-enabled=connected-nodes,bytes-sent
quarkus.cassandra.metrics.node-enabled=pool.open-connections

#Health Properties
#URL=http://localhost:8080/q/health/ready
quarkus.cassandra.health.enabled=true
```

### Endpoints:
* http://localhost:8080/q/metrics
* http://localhost:8080/q/health/ready

## App Endpoints

* http://localhost:8080/persons/searchById/1
* http://localhost:8080/persons/searchByName/Kelly
* http://localhost:8080/persons [GET]
* http://localhost:8080/persons [POST]
* http://localhost:8080/persons/delete/2