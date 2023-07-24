package com.rale.bonkerlist.repository.dao;

import com.rale.bonkerlist.repository.entity.SongsMetrics;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public class SongsMetricsImpl implements SongsMetricsDAO{
    private EntityManager entityManager;
    public SongsMetricsImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    @Override
    public List<SongsMetrics> getAllSongsSpotifyId() {
        TypedQuery<SongsMetrics> theQuery = entityManager.createQuery("FROM Songs_Metrics", SongsMetrics.class);
        return theQuery.getResultList();
    }
    @Override
    public String getLanguageBySpotifyId(String spotifyId) {
        return null;
    }
    @Override
    public String getReleaseDateBySpotifyId(String spotifyId) {
        return null;
    }
    @Override
    public String getSongNameSpotifyId(String spotifyId) {
        return null;
    }
    @Override
    public String getArtistBySpotifyId(String spotifyId) {
        return null;
    }
}
