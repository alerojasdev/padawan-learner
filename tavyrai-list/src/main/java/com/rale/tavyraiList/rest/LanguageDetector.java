package com.rale.tavyraiList.rest;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class LanguageDetector {
    public static void main(String[] args) throws IOException, InterruptedException {
        String lyrics = HttpClientConnect.getSongLyrics("high hopes");
        String singleLineText = lyrics
                .replaceAll("(\\R)", " ")
                .replaceAll("\"", "")
                .trim();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://text-analysis12.p.rapidapi.com/language-detection/api/v1.1"))
//                .header("content-type", "application/json")
//                .header("X-RapidAPI-Key", "4d3fc3bd2dmsh9cd010298680ba7p1694e8jsn8f6f95cacbba")
//                .header("X-RapidAPI-Host", "text-analysis12.p.rapidapi.com")
//                .method("POST", HttpRequest.BodyPublishers.ofString("{\r\"text\": \""+ singleLineText +"\"\r}"))
//            .build();
//    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//    response.body();
//    System.out.println(response.body());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://google-translate105.p.rapidapi.com/v1/rapid/detect"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("X-RapidAPI-Key", "4d3fc3bd2dmsh9cd010298680ba7p1694e8jsn8f6f95cacbba")
                .header("X-RapidAPI-Host", "google-translate105.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString("text=" + singleLineText))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String abc = response.body();
        Gson gson = new Gson();
        Map staff = gson.fromJson(abc, Map.class);

        if (staff.get("language_code").equals("es")){
            System.out.println();
            System.out.println("The language of the song is: ");
            System.out.println("Spanish");
            System.out.println();
        } else if (staff.get("language_code").equals("en")){
            System.out.println();
            System.out.println("The language of the song is: ");
            System.out.println("English");
            System.out.println();

        } else {
            System.out.println("The language of the song is in another language that is not Spanish or English");
            System.out.println();
        }
        System.out.println("language code = " + staff.get("language_code"));

    }
}
