package com.brzezinski.allegrocontent.video;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
final class VideoRepositoryFacadeService implements VideoService {
    private final MongoDBVideoRepository mongoDBVideoRepository;

    @Override
    public Flux<Video> findAll() {
        return mongoDBVideoRepository.findAll();
    }

    @Override
    public Mono<Video> findById(final UUID id) {
        return mongoDBVideoRepository.findById(id);
    }

    @Override
    public Mono<Video> save(final Video video) {
        return mongoDBVideoRepository.save(video);
    }

}
