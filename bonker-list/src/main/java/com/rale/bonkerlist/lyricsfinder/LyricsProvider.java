package com.rale.bonkerlist.lyricsfinder;

public interface LyricsProvider {
    String fetchLyrics(String songName);
    default String fetchLyrics(String artist, String songName) {
        return fetchLyrics(artist + " " + songName);
    }
}