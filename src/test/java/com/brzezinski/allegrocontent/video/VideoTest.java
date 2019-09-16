package com.brzezinski.allegrocontent.video;

import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;

import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.MINUTES;
import static org.hamcrest.core.IsNull.notNullValue;

public class VideoTest {

    @Test
    public void videoShouldHaveIdWhenCreated() {
        //when
        final Video video = new Video();
        //then
        Assert.assertThat(video.getId(), notNullValue());
    }

    @Test
    public void videoShouldHaveUploadedAtWhenCreatedAndEqualsNow() {
        final Video video = new Video();
        final Instant now = now().truncatedTo(MINUTES);
        //
        Assert.assertThat(video.getUploadedAt(), notNullValue());
        Assert.assertEquals(now, video.getUploadedAt().truncatedTo(MINUTES));
    }

}
