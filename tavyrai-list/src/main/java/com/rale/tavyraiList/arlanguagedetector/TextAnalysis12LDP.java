package com.rale.tavyraiList.arlanguagedetector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rale.tavyraiList.arlanguagedetector.dto.Root;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
public class TextAnalysis12LDP implements LanguageDetectorProvider{
    private static final String HOST = "text-analysis12.p.rapidapi.com";
    private static final String BASE_URL = "https://"+HOST+"/language-detection/api/v1.1";
    private static final String API_KEY = "4fcfdb753emsh882b7d49add6962p14778fjsn2657db596412";
    @Override
    public String languageDetector(String songLyrics) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", API_KEY)
                .header("X-RapidAPI-Host", HOST)
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"text\": \""+ songLyrics +"\"}"))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String responseContent = response.body();
        Root resp = new ObjectMapper().readValue(responseContent, Root.class);
        String lang = findPrimaryLanguage(resp);
        System.out.println(resp);
        System.out.println(lang);
        return lang;
    }
    private String findPrimaryLanguage(Root resp) {
        Double max = null;
        String lang = null;
        for (String k : resp.language_probability.keySet()) {
            if (max == null || resp.language_probability.get(k) > max) {
                max = resp.language_probability.get(k);
                lang = k;
            }
        }
        return lang;
    }
}
