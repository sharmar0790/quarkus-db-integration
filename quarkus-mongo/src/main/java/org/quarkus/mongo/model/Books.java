package org.quarkus.mongo.model;

public class Books {

    private Long id;
    private String name;


    public Books(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Books() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
