package com.rale.bonkerlist.languagedetector;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
@Component
public class TextAnalysisApi implements LanguageDetector {
    @Value("${textanalysis.apikey}")
    private String apiKey;
    private static final String HOST = "text-analysis12.p.rapidapi.com";
    private static final String BASE_URL = "https://"+HOST+"/language-detection/api/v1.1";
    @Override
    public String detectLanguage(String songLyrics) {
        String jsonBody = new Gson().toJson(Map.of("text", songLyrics));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", HOST)
                .method("POST", HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        String lang = null;
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String responseContent = response.body();
            ObjectMapper om = new ObjectMapper();
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            TextAnalysis12LDProviderDTO resp = om.readValue(responseContent, TextAnalysis12LDProviderDTO.class);
            lang = findPrimaryLanguage(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lang;
    }
    private String findPrimaryLanguage(TextAnalysis12LDProviderDTO resp) {
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
    private static class TextAnalysis12LDProviderDTO{
        public Map<String, Double> language_probability;
    }
}






