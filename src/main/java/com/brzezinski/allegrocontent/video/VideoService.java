package com.brzezinski.allegrocontent.video;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface VideoService {
    Flux<Video> findAll();

    Mono<Video> findById(final UUID id);

    Mono<Video> save(final Video video);
}
