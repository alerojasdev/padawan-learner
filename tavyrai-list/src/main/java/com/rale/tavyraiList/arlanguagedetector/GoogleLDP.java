package com.rale.tavyraiList.arlanguagedetector;

import com.google.gson.Gson;
import com.rale.tavyraiList.arlyricsfinder.AzLyricsLyricsProvider;
import com.rale.tavyraiList.arlyricsfinder.LyricsProvider;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
public class GoogleLDP implements LanguageDetectorProvider{
    private static final String HOST = "google-translate1.p.rapidapi.com";
    private static final String BASE_URL = "https://"+HOST+"/language/translate/v2/detect";
    private static final String API_KEY = "4fcfdb753emsh882b7d49add6962p14778fjsn2657db596412";
    public String languageDetector(String songLyrics) throws Throwable {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("Accept-Encoding", "application/gzip")
                .header("X-RapidAPI-Key", API_KEY)
            .header("X-RapidAPI-Host", HOST)
                .method("POST", HttpRequest.BodyPublishers.ofString("q="+songLyrics))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String abc = response.body();
        Gson gson = new Gson();
        Map staff = gson.fromJson(abc, Map.class);

        String lang = (String) ((Map)((List)((List)((Map)staff.get("data")).get("detections")).get(0)).get(0)).get("language");

        return lang;
    }
}
