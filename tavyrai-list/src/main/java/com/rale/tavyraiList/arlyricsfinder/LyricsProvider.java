package com.rale.tavyraiList.arlyricsfinder;

public interface LyricsProvider {
    String fetchLyrics(String songName);
    String fetchLyrics(String artist, String songName);
}