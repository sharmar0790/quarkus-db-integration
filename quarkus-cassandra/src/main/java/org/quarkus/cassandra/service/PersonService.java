package org.quarkus.cassandra.service;

import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.quarkus.runtime.api.session.QuarkusCqlSession;
import org.quarkus.cassandra.dao.PersonDao;
import org.quarkus.cassandra.model.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PersonService {

    @Inject
    PersonDao personDao;

    @Inject
    QuarkusCqlSession session;


    public List<Person> getAllPersons() {
        return personDao.getAllPersons().all();
    }

    public Optional<Person> getPersonsByName(String name) {
        return personDao.getPersonsByName(name);
    }

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

    public void save(Person person) {
        personDao.save(person);
    }


    public void deleteByName(Integer id) {
        personDao.deleteByName(id);
    }
}
