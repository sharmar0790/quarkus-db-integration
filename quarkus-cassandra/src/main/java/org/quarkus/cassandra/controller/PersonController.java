package org.quarkus.cassandra.controller;

import com.datastax.oss.quarkus.runtime.api.session.QuarkusCqlSession;
import org.quarkus.cassandra.model.Person;
import org.quarkus.cassandra.service.PersonService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonController {

    @Inject
    PersonService personService;

    @Inject
    QuarkusCqlSession session;

    @GET
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GET
    @Path("/searchByName/{name}")
    public Person getPersonsByName(@PathParam("name") String name) {
        return personService.getPersonsByName(name).orElse(new Person("custom name"));
    }

    @GET
    @Path("/searchById/{id}")
    public Person getPersonById(@PathParam("id") Integer id) {
        return personService.getPersonsById(id).orElse(new Person("custom id"));
    }

    @POST
    public void save(Person person) {
        personService.save(person);
    }

    @DELETE
    @Path("/delete/{id}")
    public void deleteByName(@PathParam("id") Integer id) {
        personService.deleteByName(id);
    }
}