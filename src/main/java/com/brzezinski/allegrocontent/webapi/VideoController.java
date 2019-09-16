package com.brzezinski.allegrocontent.webapi;

import com.brzezinski.allegrocontent.video.Video;
import com.brzezinski.allegrocontent.video.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.notFound;

@Slf4j
@RestController
@RequiredArgsConstructor
public final class VideoController {
    static final String VIDEOS_RESOURCE_URL = "/videos";

    private final VideoService videoService;

    @GetMapping(VIDEOS_RESOURCE_URL)
    public Flux<Video> getAllVideos() {
        return videoService.findAll();
    }

    @GetMapping(VIDEOS_RESOURCE_URL + "/{id}")
    public Mono<ResponseEntity<Video>> getVideoById(@PathVariable("id") final String id) {
        return videoService.findById(UUID.fromString(id))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    @PostMapping(VIDEOS_RESOURCE_URL)
    public Mono<ResponseEntity<Video>> addVideo(@Valid @RequestBody final Video video) {
        log.info("Received {}", video.toString());
        return videoService.save(video)
                .map(savedVideo -> new ResponseEntity<>(savedVideo, HttpStatus.CREATED))
                .defaultIfEmpty(badRequest().build());
    }

}
