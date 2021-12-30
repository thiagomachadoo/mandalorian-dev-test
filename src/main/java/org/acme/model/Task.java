package org.acme.model;


import javax.validation.constraints.Size;

public class Task {

    private long id;


    @Size(min = 10)
    private String texto;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String title) {
        this.texto = title;
    }

}
