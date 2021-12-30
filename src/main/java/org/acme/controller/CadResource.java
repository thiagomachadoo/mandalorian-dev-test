package org.acme.controller;

import org.acme.model.Task;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/cadastro")
public class CadResource {
    public static List<Task> task = new ArrayList<>();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCad(){
        return Response.ok(task).build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/size")
    public Integer countCad(){
        return  task.size();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCad(Task newCad){
        task.add(newCad);
        return Response.ok(task).build();
    }


    @PUT
    @Path("{id}/{texto}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCad(@PathParam("id") Long id,
                                @QueryParam("texto") String texto){
        task =  task.stream().map(task -> {
            if (Objects.equals(task.getId(), id)){
                task.setTexto(texto);
            }
            return task;
        }).collect(Collectors.toList());
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteCad(@PathParam("id")  Long id){
       Optional<Task> cadToDelete =  task.stream().filter(task -> Objects.equals(task.getId(), id))
                .findFirst();
       boolean removed = false;
       if (cadToDelete.isPresent()){
           removed = task.remove(cadToDelete.get());
       }
       if (removed){
           return Response.noContent().build();
       }
       return Response.status(Response.Status.BAD_REQUEST).build();
    }


}
