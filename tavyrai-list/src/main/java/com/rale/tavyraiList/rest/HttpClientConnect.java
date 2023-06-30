package com.rale.tavyraiList.rest;

import com.github.kevinsawicki.http.HttpRequest;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpClientConnect {
    public static void main(String[] args) throws Exception {
//        Obtain X value
        String getValueSearchUrl = "https://www.azlyrics.com/geo.js";
        HttpRequest getValueSearchRequest = HttpRequest.get(getValueSearchUrl);
        String x = findValueX(getValueSearchRequest.body());

//        Obtain URL of the lyrics
        String host = "https://search.azlyrics.com/search.php?q=";
        String searchInput = "counting stars";
        String valueX = "&x=" + x;
        HttpRequest lyricsUrlRequest = HttpRequest.get(host + searchInput + valueX);
        String linkLyrics = extractLyricsUrl(lyricsUrlRequest.body());

//        Obtain Lyrics
        HttpRequest lyricsRequest = HttpRequest.get(linkLyrics);
        String body = lyricsRequest.body();

        Files.writeString(Paths.get("C:\\dev\\repos\\padawan-learner\\tavyrai-list\\page.txt"), body, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        System.out.println(extractLyricsFromBody(body));

    }

    public static String findValueX(String body){
        String pattern = "\"value\", \"(.*)\"";

        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(body);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static String extractLyricsUrl(String body){
        String pattern = "href=\"(.*)\">1.";

        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(body);
        if (matcher.find()) {
            String url = matcher.group(1);
            System.out.println(url);
            return url;
        }
        return null;
    }
    public static String extractLyricsFromBody(String body){
        String s = body;
        String regex = "</span>\\R</div>\\R+<b>(.*)\\R</div>\\R+<br><br>\\R";
        Pattern p = Pattern.compile(regex,  Pattern.MULTILINE | Pattern.DOTALL   ); //Pattern.DOTALL |
//                System.out.println("regexmatches?: " + p.matcher(s).find());
        Matcher matcher = p.matcher(body);
        if (matcher.find()) {
            return matcher.group(1).replaceAll("<.*>", "");
        }
        return "lyrics";

    }
}

