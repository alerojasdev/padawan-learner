package com.rale.tavyraiList.ralelyricsfinder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Component
public class ShazamApi implements LyricsProvider{
    private static final String SEARCH_KEY_FIRST_PART_URL = "https://www.shazam.com/services/search/v4/en-US/PY/web/search";
    private static final String LYRICS_URL = "https://www.shazam.com/discovery/v5/en-US/PY/web/-/track/";


//    public static void main(String[] args) {
//
//        LyricsProvider lp = new ShazamApi();
//        System.out.println(lp.fetchLyrics("iron man"));
//
//        ShazamApi sa = new ShazamApi();
//        System.out.println(sa.getMetadata("caraluna"));
//    }

    @Override
    public String fetchLyrics(String songName) {

        return getMetadata(songName).lyrics;
    }

    @ToString
    public static class ShazamMetadata {
        public Date releaseDate;
        public String lyrics;
    }

    public ShazamMetadata getMetadata(String songName) {

        int key = getMusicId(songName);

        String body = HttpRequest.get(LYRICS_URL + key, true,
                "shazamapiversion", "v3",
                "video", "v3")
                .body();

        RawMetadataDTO responseLyrics = new RawMetadataDTO();
        try {
            ObjectMapper objMap = new ObjectMapper();
            objMap.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            responseLyrics = objMap.readValue(body, RawMetadataDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }



        ShazamMetadata sm = extractRelevantData(responseLyrics);
        return sm;
    }

    private ShazamMetadata extractRelevantData(RawMetadataDTO responseLyrics) {

        try {
            String releasedate = responseLyrics.releasedate;
            ArrayList<String> lyrics = responseLyrics.sections.get(1).text;
            ShazamMetadata insert = new ShazamMetadata();
            if (lyrics != null) {
                insert.lyrics = String.join(" ", lyrics);
            }
            if (releasedate != null) {
                insert.releaseDate = new SimpleDateFormat("dd-MM-yyyy").parse(releasedate);
            }
            return insert;
        } catch( Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static int getMusicId(String songName)  {
        HttpRequest keySearchResponse = HttpRequest.get(SEARCH_KEY_FIRST_PART_URL, true,
                "term", songName,
                "numResults", "1",
                "offset", "0",
                "types", "artists,songs",
                "limit", "1"
        );

        String responseContentUrl = keySearchResponse.body();

        Integer id = null;
        try {
            ObjectMapper om = new ObjectMapper();
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            SearchResultsDTO responseKey = om.readValue(responseContentUrl, SearchResultsDTO.class);
            id = Integer.parseInt(responseKey.tracks.hits.get(0).track.key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
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
}
