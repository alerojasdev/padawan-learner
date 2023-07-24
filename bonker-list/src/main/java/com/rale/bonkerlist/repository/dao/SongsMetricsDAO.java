package com.rale.bonkerlist.repository.dao;

import com.rale.bonkerlist.repository.entity.SongsMetrics;

import java.util.List;

public interface SongsMetricsDAO {
    List<SongsMetrics> getAllSongsSpotifyId();
    String getLanguageBySpotifyId(String spotifyId);
    String getReleaseDateBySpotifyId(String spotifyId);
    String getSongNameSpotifyId(String spotifyId);
    String getArtistBySpotifyId(String spotifyId);

}
