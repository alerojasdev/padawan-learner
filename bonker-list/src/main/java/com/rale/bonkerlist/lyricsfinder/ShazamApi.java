package com.rale.bonkerlist.lyricsfinder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Component
public class ShazamApi implements LyricsProvider{
    private static final String SEARCH_KEY_FIRST_PART_URL = "https://www.shazam.com/services/search/v4/en-US/PY/web/search";
    private static final String LYRICS_URL = "https://www.shazam.com/discovery/v5/en-US/PY/web/-/track/";

    @Override
    public String fetchLyrics(String songName) {
        return getMetadata(songName).lyrics;
    }
    public ShazamMetadata getMetadata(String songName) {
        try {
            Integer key = getMusicId(songName);
            String body = HttpRequest.get(LYRICS_URL + key, true,
                    "shazamapiversion", "v3",
                    "video", "v3")
                    .body();
            RawMetadataDTO responseLyrics;
            ObjectMapper objMap = new ObjectMapper();
            objMap.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            responseLyrics = objMap.readValue(body, RawMetadataDTO.class);
            ShazamMetadata sm = extractRelevantData(responseLyrics);
            return sm;
        } catch (Exception e) {
            System.out.println();
            System.out.println("Find some issues obtaining metadata from shazam for: " + songName);
        }
        System.out.println();
        System.out.println("Required meta data for " + songName + " filled with default values ........");
            ShazamMetadata shazamMetadata = new ShazamMetadata();
            shazamMetadata.lyrics = "notLyricsFound";
            shazamMetadata.releaseDate = 123;
            return shazamMetadata;
    }
    private ShazamMetadata extractRelevantData(RawMetadataDTO responseLyrics) throws ParseException {
        String releasedate = responseLyrics.releasedate;
        ArrayList<String> lyrics = responseLyrics.sections.get(1).text;
        ShazamMetadata insert = new ShazamMetadata();
        insert.lyrics = String.join(" ", lyrics);
        insert.releaseDate = new SimpleDateFormat("dd-MM-yyyy").parse(releasedate).getYear() + 1900;
        return insert;
    }

    private static Integer getMusicId(String songName) throws JsonProcessingException {
        HttpRequest keySearchResponse = HttpRequest.get(SEARCH_KEY_FIRST_PART_URL, true,
                "term", songName,
                "numResults", "1",
                "offset", "0",
                "types", "artists,songs",
                "limit", "1"
        );
        String responseContentUrl = keySearchResponse.body();
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SearchResultsDTO responseKey = om.readValue(responseContentUrl, SearchResultsDTO.class);
        return Integer.parseInt(responseKey.tracks.hits.get(0).track.key);
    }
    private static class SearchResultsDTO {
        public Tracks tracks;
        public static class Tracks{
            public ArrayList<Hit> hits;
        }
        public static class Hit{
            public Track track;
        }
        public static class Track{
            public String key;
        }
    }
    @ToString
    private static class RawMetadataDTO {
        public ArrayList<Section> sections;
        public String releasedate;
        @ToString public static class Section{
            public ArrayList<String> text;
        }
    }
    public static class ShazamMetadata{
        public String lyrics;
        public int releaseDate;
        public int getReleaseDate() {
            return releaseDate;
        }

    }
}
