# Quarkus-Mongo

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .


## Creating the application
```
mvn io.quarkus:quarkus-maven-plugin:1.12.2.Final:create \
    -DprojectGroupId=org.quarkus.mongo \
    -DprojectArtifactId=quarkus-mongo \
    -DprojectVersion=1.0-SNAPSHOT \
    -DclassName="org.quarkus.mongo.controller.QuarkusMongoController" \
    -Dextensions="rest-assured,resteasy-jsonb,mongodb-panache,mongodb-client" 
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

## Running the mongo in the container mode
```
$ docker run -p 27017:27017 --name mongo -d mongo
```


## Querying the mongo from app
* With Panache library
```
//Annotate and extend the model class
@MongoEntity(collection = "persons")
public class Person extends PanacheMongoEntity {
.....
}


@ApplicationScoped
public class PersonRepository {

    public List<Person> getAllPersons() {
        return Person.listAll();
    }

    public Person getPersonByName(String name) {
        return Person.find("name", name).firstResult();
    }

    public void save(Person person) {
        Person.persistOrUpdate(person);
    }

    public Long deleteByName(String name) {
        return Person.delete("name", name);
    }
}


```

* With mongo client
```
@Inject
MongoClient mongoClient;

public void add(Books Books){
   Document document = new Document()
           .append("name", Books.getName())
           .append("id", Books.getId());
  getCollection().insertOne(document);
}
    
    
private MongoCollection getCollection(){
    return mongoClient.getDatabase("Books").getCollection("Books");
}        
    
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

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-mongo-1.0.0-SNAPSHOT-runner`

