package com.rale.bonkerlist.spotifyapi.dto;

import java.util.stream.Collectors;

public class SpotifyMusic{
    public String spotifyMusicUri;
    public String title;
    public String artistNames;
    public String lyrics;
    public int releaseDate;
    public String language;
    public SpotifyMusic(String idioma) {
        language = idioma;
    }
    public SpotifyMusic(Track track) {
        this.spotifyMusicUri = "spotify:track:"+track.id;
        this.title = track.name;
        this.artistNames = track.artists.stream().map(artist-> artist.name).collect(Collectors.joining(" "));
    }
    private String getShortLyrics() {
        if (lyrics == null)
            return "null";
        return lyrics.substring(0, Math.min(10, lyrics.length()));
    }
    @Override
    public String toString() {
        return "SpotifyMusic{" +
                "stotifyMusicUri='" + spotifyMusicUri + '\'' +
                ", title='" + title + '\'' +
                ", artistNames='" + artistNames + '\'' +
                ", lyrics='" + getShortLyrics() + '\'' +
//                    ", releaseDate=" + releaseDate +
                ", language='" + language + '\'' +
                '}';
    }
}