package com.rale.tavyraiList.arlyricsfinder;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface LyricsProvider {
    String fetchLyrics(String songName);
    default String fetchLyrics(String artist, String songName) {
        return fetchLyrics(artist + " " + songName);
    }
}