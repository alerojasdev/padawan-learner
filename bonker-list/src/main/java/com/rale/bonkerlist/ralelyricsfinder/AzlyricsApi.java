package com.rale.bonkerlist.ralelyricsfinder;

import com.github.kevinsawicki.http.HttpRequest;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AzlyricsApi implements LyricsProvider{
    private static final String GEOURL = "https://www.azlyrics.com/geo.js";
    private static final String SEARCHURL = "https://search.azlyrics.com/search.php";

    @Override
    public String fetchLyrics(String artist, String songName) {
        return fetchLyrics(artist+" "+songName);
    }
    public String fetchLyrics(String songName) {

        String token = getCsrfToken();

        String lyricsQueryResult = HttpRequest.get(SEARCHURL, true, "q", songName, "x", token).body();

        String firstLyricsUrl = getFirstUrl(lyricsQueryResult);

        String lyricsPageBody = HttpRequest.get(firstLyricsUrl).body();

        String songLyrics =  extractLyrics(lyricsPageBody);

        return songLyrics;
    }
    private static final Pattern TOKEN_PATTERN = Pattern.compile("\"value\", \"(.*)\"");
    private String getCsrfToken(){
        HttpRequest csrfTokenRequest = HttpRequest.get(GEOURL);
        String body = csrfTokenRequest.body();

        Matcher matcher = TOKEN_PATTERN.matcher(body);
        String token = null;
        if (matcher.find()) {
            token = matcher.group(1);
        }
        return token;
    }

    private static final Pattern FIRST_URL_PATTERN = Pattern.compile("href=\"(.*)\">1.");
    private String getFirstUrl(String lyricsQueryResult){
        Matcher matcher = FIRST_URL_PATTERN.matcher(lyricsQueryResult);
        String firstUrl = null;
        if (matcher.find()) {
            firstUrl = matcher.group(1);
        }
        return firstUrl;
    }
    private static final Pattern LYRICS_CONTENT_PATTERN =
            Pattern.compile(
                    "</span>\\R</div>\\R+<b>(.*)\\R</div>\\R+<br><br>\\R",
                    Pattern.MULTILINE | Pattern.DOTALL
            );
    private String extractLyrics(String lyricsPageBody){
        Matcher matcher = LYRICS_CONTENT_PATTERN.matcher(lyricsPageBody);
        String lyrics = null;
        if (matcher.find()) {
            lyrics = matcher.group(1).replaceAll("<.*>", "");
        }
        return lyrics;
    }

}

