package com.rale.bonkerlist.repository.dao;

import com.rale.bonkerlist.repository.entity.SongsMetrics;
import jakarta.persistence.PersistenceUnit;

import java.util.List;

@PersistenceUnit
public interface SongsMetricsDAO {
    void saveSong(SongsMetrics songsMetrics);
    List findSong();
    String findLanguage(String spotify_id);
    int findReleaseDate(String spotify_id);

}
