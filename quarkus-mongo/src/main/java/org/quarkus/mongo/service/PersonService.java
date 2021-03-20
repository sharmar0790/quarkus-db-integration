package org.quarkus.mongo.service;

import org.quarkus.mongo.model.Person;
import org.quarkus.mongo.repository.PersonRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class PersonService {

    @Inject
    PersonRepository personRepository;

    public List<Person> getAllPersons() {
        return personRepository.getAllPersons();
    }

    public Person getPersonsByName(String name) {
        return personRepository.getPersonByName(name);
    }

    public void save(Person person) {
        personRepository.save(person);
    }

    public Long deleteByName(String name) {
        return personRepository.deleteByName(name);
    }
}
