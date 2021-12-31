package org.acme.controller;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import java.util.List;

import io.smallrye.mutiny.Uni;
import org.acme.model.TaskTO;
import org.acme.service.TaskService;

@Singleton
public class TaskController {

     @Inject
     TaskService service;



    @GET
    public Uni<List<String>> keys() {
        return service.keys();
    }

    @POST
    public TaskTO create(TaskTO task) {
        service.set(task.key, task.value);
        return task;
    }

    @GET
    @Path("/{key}")
    public TaskTO get(@PathParam("key") String key) {
        return new TaskTO(key, String.valueOf(service.get(key)));
    }


    @DELETE
    @Path("/{key}")
    public Uni<Void> delete(@PathParam("key") String key) {
        return service.del(key);
    }
}