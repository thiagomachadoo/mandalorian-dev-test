package org.acme.service;

import io.quarkus.redis.client.RedisClient;
import io.quarkus.redis.client.reactive.ReactiveRedisClient;
import io.smallrye.mutiny.Uni;

import io.vertx.mutiny.redis.client.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Singleton
public
class TaskService {

    @Inject
    RedisClient redisClient;

    @Inject
    ReactiveRedisClient reactiveRedisClient;


    public Uni<Void> del(String key) {
        return reactiveRedisClient.del(Arrays.asList(key))
                .map(response -> null);
    }

    public String get(String key) {
        return redisClient.get(key).toString();
    }

    public void set(String key, @Size(min = 10) @Pattern(regexp = "^[a-zA-Z ][a-zA-Z0-9 ]+$") String value) {
        redisClient.set(Arrays.asList(key, value.toString()));
    }

    void increment(String key, Integer incrementBy) {
        redisClient.incrby(key, incrementBy.toString());
    }

    public Uni<List<String>> keys() {
        return reactiveRedisClient
                .keys("*")
                .map(response -> {
                    List<String> result = new ArrayList<>();
                    for (Response r : response) {
                        result.add(r.toString());
                    }
                    return result;
                });
    }
}