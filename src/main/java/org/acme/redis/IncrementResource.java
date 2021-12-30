package org.acme.redis;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import java.util.List;

import io.smallrye.mutiny.Uni;

@Path("/increments")
public class IncrementResource {

    @Inject
    IncrementService service;

    @GET
    public Uni<List<String>> keys() {
        return service.keys();
    }

    @POST
    public Increment create(Increment increment) {
        service.set(increment.key, increment.value);
        return increment;
    }

    @GET
    @Path("/{key}")
    public Increment get(@PathParam("key") String key) {
        return new Increment(key, Integer.valueOf(service.get(key)));
    }

    @PUT
    @Path("/{key}")
    public void increment(@PathParam("key") String key, Integer value) {
        service.increment(key, value);
    }

    @DELETE
    @Path("/{key}")
    public Uni<Void> delete(@PathParam("key") String key) {
        return service.del(key);
    }
}