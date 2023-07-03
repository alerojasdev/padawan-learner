package com.rale.tavyraiList.rest;

import com.github.kevinsawicki.http.HttpRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpClientConnect {
    public static void main(String[] args) throws Exception {

    }
    public static String getSongLyrics(String songName){
        //        Obtain X value
        String getValueSearchUrl = "https://www.azlyrics.com/geo.js";
        HttpRequest getValueSearchRequest = HttpRequest.get(getValueSearchUrl);
        String x = findValueX(getValueSearchRequest.body());
        //        Obtain URL of the lyrics
        String host = "https://search.azlyrics.com/search.php?q=";
        String valueX = "&x=" + x;
        HttpRequest lyricsUrlRequest = HttpRequest.get(host + songName + valueX);

        try {
//            if (lyricsUrlRequest.message().equals("OK")){
                String linkLyrics = extractLyricsUrl(lyricsUrlRequest.body());
                //        Obtain Lyrics
                HttpRequest lyricsRequest = HttpRequest.get(linkLyrics);
                String body = lyricsRequest.body();
//                return extractLyricsFromBody(body);

            } catch(NullPointerException ex){
            System.out.println("letras no encontradas");
            throw new RuntimeException("LA LETRA DE LA CANCION NO SE ENCONTRO");
        }
            HttpRequest lyricsUrlRequest2 = HttpRequest.get(host + songName + valueX);
            String linkLyrics = extractLyricsUrl(lyricsUrlRequest2.body());
            HttpRequest lyricsRequest2 = HttpRequest.get(linkLyrics);
            //        Obtain Lyrics
            String body = lyricsRequest2.body();
            return extractLyricsFromBody(body);
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
            return url;
        }
        return null;
    }
    public static String extractLyricsFromBody(String body){
        String s = body;
        String regex = "</span>\\R</div>\\R+<b>(.*)\\R</div>\\R+<br><br>\\R";
//        MULTILINE allow regex java to by multiline
//        DOTALL allow .* to by multiline
        Pattern p = Pattern.compile(regex,  Pattern.MULTILINE | Pattern.DOTALL   ); //Pattern.DOTALL |
//                System.out.println("regexmatches?: " + p.matcher(s).find());
        Matcher matcher = p.matcher(body);
        if (matcher.find()) {
            return matcher.group(1).replaceAll("<.*>", "");
        }
        return "lyrics";
    }
}

