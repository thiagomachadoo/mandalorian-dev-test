package org.acme.web.resource;

import org.acme.model.TaskTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/api")
public class TaskResource {
    public static List<TaskTO> taskTO = new ArrayList<>();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCad(){
        return Response.ok(taskTO).build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/size")
    public Integer countCad(){
        return  taskTO.size();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCad(TaskTO newCad){
        taskTO.add(newCad);
        return Response.ok(taskTO).build();
    }


    @PUT
    @Path("{id}/{texto}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCad(@PathParam("id") Long id,
                                @QueryParam("texto") String texto){
        taskTO =  taskTO.stream().map(taskTO -> {
            if (Objects.equals(taskTO.getId(), id)){
                taskTO.setValue(taskTO.value);
            }
            return taskTO;
        }).collect(Collectors.toList());
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteCad(@PathParam("id")  Long id){
       Optional<TaskTO> cadToDelete =  taskTO.stream().filter(taskTO -> Objects.equals(taskTO.getId(), id))
                .findFirst();
       boolean removed = false;
       if (cadToDelete.isPresent()){
           removed = taskTO.remove(cadToDelete.get());
       }
       if (removed){
           return Response.noContent().build();
       }
       return Response.status(Response.Status.BAD_REQUEST).build();
    }


}
