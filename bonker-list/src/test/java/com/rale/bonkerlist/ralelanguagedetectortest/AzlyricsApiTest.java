package com.rale.bonkerlist.ralelanguagedetectortest;

import com.rale.bonkerlist.lyricsfinder.AzlyricsApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AzlyricsApiTest {

    @Autowired
    AzlyricsApi azlyricsApi;

    @Test
    public void shouldReturnEnglishLanguage()  {
        String caraluna = azlyricsApi.fetchLyrics("caraluna");
        assertThat(caraluna).isNotEmpty();
    }

}
