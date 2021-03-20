package org.quarkus.cassandra.mapper;

import com.datastax.oss.quarkus.runtime.api.session.QuarkusCqlSession;
import org.quarkus.cassandra.dao.PersonDao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Produces;

public class PersonDaoProducer {

    private final PersonDao personDao;

    @Inject
    public PersonDaoProducer(QuarkusCqlSession session) {
        // create a mapper
        PersonMapper mapper = new PersonMapperBuilder(session).build();
        // instantiate our Daos
        personDao = mapper.personDao("testkeyspace", "person");
    }

    @Produces
    @ApplicationScoped
    PersonDao producePersonDao() {
        return personDao;
    }
}
