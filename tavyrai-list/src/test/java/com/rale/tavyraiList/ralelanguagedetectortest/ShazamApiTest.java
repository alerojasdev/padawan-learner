package com.rale.tavyraiList.ralelanguagedetectortest;

import com.rale.tavyraiList.ralelanguagedetector.TextAnalysisApi;
import com.rale.tavyraiList.ralelyricsfinder.ShazamApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ShazamApiTest {

    @Autowired
    ShazamApi shazamApi;

    @Test
    public void shouldReturnEnglishLanguage()  {
        ShazamApi.ShazamMetadata caraluna = shazamApi.getMetadata("caraluna");
        assertThat(caraluna.lyrics).isNotEmpty();
    }

}
