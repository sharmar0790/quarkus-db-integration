package org.quarkus.mongo.controller;

import org.quarkus.mongo.model.Books;
import org.quarkus.mongo.service.BooksServiceWithoutPanache;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/customer")
@Produces("application/json")
@Consumes("application/json")
public class BooksControllerWithoutPanache {

    @Inject
    BooksServiceWithoutPanache service;

    @GET
    public List<Books> list() {
        return service.list();
    }

    @POST
    public List<Books> add(Books book) {
        service.add(book);
        return list();
    }

    @PUT
    public List<Books> put(Books book) {
        service.update(book);
        return list();
    }

    @DELETE
    public List<Books> delete(Books book) {
        service.delete(book);
        return list();
    }
}
