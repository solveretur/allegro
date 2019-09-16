package com.brzezinski.allegrocontent.webapi;

import com.brzezinski.allegrocontent.video.Video;
import com.brzezinski.allegrocontent.video.VideoService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.UUID;

import static com.brzezinski.allegrocontent.video.Category.MUSIC;
import static com.brzezinski.allegrocontent.webapi.VideoController.VIDEOS_RESOURCE_URL;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VideoControllerIntegrationTest {
    private VideoService videoService;
    private WebTestClient webTestClient;

    @Autowired
    public void setVideoService(final VideoService videoService) {
        this.videoService = videoService;
    }

    @Autowired
    public void setWebTestClient(final WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    @Test
    public void contextLoad() {

    }

    @Test
    public void addVideoShouldSaveVideoAndReturnItJson() {
        final Video video = Video.builder()
                .title("testTitle")
                .author("testAuthor")
                .category(MUSIC)
                .dataUrl("http://test.com")
                .description("testDescription")
                .build();

        webTestClient.post().uri(VIDEOS_RESOURCE_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(video), Video.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.title").isEqualTo("testTitle")
                .jsonPath("$.category").isEqualTo("MUSIC")
                .jsonPath("$.author").isEqualTo("testAuthor")
                .jsonPath("$.description").isEqualTo("testDescription")
                .jsonPath("$.dataUrl").isEqualTo("http://test.com")
                .jsonPath("$.uploadedAt").isNotEmpty();
    }

    @Test
    public void addVideoShouldReturnHttpBadRequestWhenVideoIsNotValid() {
        final Video video = Video.builder()
                .title(null)
                .author("testAuthor")
                .category(MUSIC)
                .dataUrl("http://test.com")
                .description("testDescription")
                .build();

        webTestClient.post().uri(VIDEOS_RESOURCE_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(video), Video.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void addVideoShouldReturnHttpBadRequestWhenVideoCategoryIsNull() {
        final Video video = Video.builder()
                .title("testTitle")
                .author("testAuthor")
                .category(null)
                .dataUrl("http://test.com")
                .description("testDescription")
                .build();

        webTestClient.post().uri(VIDEOS_RESOURCE_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(video), Video.class)
                .exchange()
                .expectStatus().isBadRequest();
    }


    @Test
    public void addVideoShouldReturnHttpBadRequestWhenTitleIsEmptyString() {
        final Video video = Video.builder()
                .title("")
                .author("testAuthor")
                .category(MUSIC)
                .dataUrl("http://test.com")
                .description("testDescription")
                .build();

        webTestClient.post().uri(VIDEOS_RESOURCE_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(video), Video.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void addVideoShouldReturnHttpBadRequestWhenDataUrlIsWrongFormat() {
        final Video video = Video.builder()
                .title("testTitle")
                .author("testAuthor")
                .category(MUSIC)
                .dataUrl("wrongFormat")
                .description("testDescription")
                .build();

        webTestClient.post().uri(VIDEOS_RESOURCE_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(video), Video.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void addVideoShouldReturnHttpBadRequestWhenDataUrlIsEmpty() {
        final Video video = Video.builder()
                .title("testTitle")
                .author("testAuthor")
                .category(MUSIC)
                .dataUrl("")
                .description("testDescription")
                .build();

        webTestClient.post().uri(VIDEOS_RESOURCE_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(video), Video.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void getAllVideosShouldReturnJsonListOfVideos() {
        webTestClient.get().uri(VIDEOS_RESOURCE_URL)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Video.class);
    }

    @Test
    public void getVideoByIdShouldReturnJsonOfVideo() {
        final Video video = videoService.save(
                Video.builder()
                        .title("testTitle")
                        .author("testAuthor")
                        .category(MUSIC)
                        .description("testDescription")
                        .build()
        ).block();

        webTestClient.get()
                .uri(VIDEOS_RESOURCE_URL + "/{id}", Collections.singletonMap("id", video.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> Assertions.assertThat(response.getResponseBody()).isNotNull());
    }

    @Test
    public void getVideoByIdShouldReturnHttpNotFoundWhenVideoIsNotInRepo() {
        webTestClient.get()
                .uri(VIDEOS_RESOURCE_URL + "/{id}", Collections.singletonMap("id", UUID.randomUUID()))
                .exchange()
                .expectStatus().isNotFound();
    }

}
