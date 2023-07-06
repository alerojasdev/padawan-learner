package com.rale.tavyraiList.ralelanguagedetectortest;

import com.rale.tavyraiList.ralelanguagedetector.TextAnalysisApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TextAnalysisApiTest {

    @Autowired
    TextAnalysisApi taApi;

    @Test
    public void shouldReturnEnglishLanguage()  {
        String text = "Hello I'm Alan and I am writing in english";
        String language = taApi.detectLanguage(text);
        assertThat(language).isEqualTo("en");
        System.out.println("yes");
    }

}
