package org.quarkus.mongo.controller;

import org.quarkus.mongo.model.Person;
import org.quarkus.mongo.service.PersonService;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/persons")
@Produces(MediaType.APPLICATION_JSON)
public class PersonsController {

    @Inject
    PersonService personService;

    @GET
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GET
    @Path("/{name}")
    public Person getPersonsByName(@PathParam("name") String name) {
        return personService.getPersonsByName(name);
    }

    @POST
    public void save(Person person) {
        personService.save(person);
    }

    @DELETE
    @Path("/delete/{name}")
    public Long deleteByName(@PathParam("name") String name) {
        return personService.deleteByName(name);
    }
}