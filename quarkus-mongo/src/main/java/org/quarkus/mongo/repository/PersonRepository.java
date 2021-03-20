package org.quarkus.mongo.repository;

import org.quarkus.mongo.model.Person;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

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
