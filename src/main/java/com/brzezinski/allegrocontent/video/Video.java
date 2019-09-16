package com.brzezinski.allegrocontent.video;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.Instant;
import java.util.UUID;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

@Data
@NoArgsConstructor
@Document
@AllArgsConstructor
@Builder
public final class Video {
    @Id
    private final UUID id = randomUUID();
    @NotEmpty
    private String title;
    @NotEmpty
    @URL
    private String dataUrl;
    private String description;
    private String author;
    @NotNull
    private Category category;
    @Past
    private final Instant uploadedAt = now();
}
