package com.rale.bonkerlist.repository.dao;
import com.rale.bonkerlist.repository.entity.SongsMetrics;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SongsMetricsImpl implements SongsMetricsDAO{
    private EntityManager entityManager;
    @Autowired
    public SongsMetricsImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    @Override
    @Transactional
    public void saveSong(SongsMetrics songsMetrics) {
        entityManager.persist(songsMetrics);
    }
    @Override
    public List findSong() {
        Query query = entityManager.createQuery("SELECT e FROM SongsMetrics e", SongsMetrics.class);
        return query.getResultList();
    }

    @Override
    public String findLanguage(String spotify_id) {
        SongsMetrics song = entityManager.find(SongsMetrics.class, spotify_id);
        return song.getLanguage();
    }

    @Override
    public int findReleaseDate(String spotify_id) {
        SongsMetrics song = entityManager.find(SongsMetrics.class, spotify_id);
        return song.getRelease_date();
    }
}
