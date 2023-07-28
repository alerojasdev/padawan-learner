package com.rale.bonkerlist.repository.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "songs_metrics")
public class SongsMetrics {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="spotify_id")
    private String spotify_id;
    @Column(name="song_name")
    private String name;
    @Column(name="artist")
    private String artist;
    @Column(name="language")
    private String language;
    @Column(name="release_date")
    private int release_date;
    @Column(name="lyrics")
    private String lyrics;
    public SongsMetrics(){}
    public SongsMetrics(String spotify_id, String language, int release_date, String lyrics, String artist, String name) {
        this.spotify_id = spotify_id;
        this.language = language;
        this.release_date = release_date;
        this.lyrics = lyrics;
        this.artist = artist;
        this.name = name;
    }
    public String getSpotify_id() {return spotify_id;}
    public void setSpotify_id(String spotify_id) {this.spotify_id = spotify_id;}
    public String getLanguage() {return language;}
    public void setLanguage(String language) {this.language = language;}
    public int getRelease_date() {return release_date;}
    public void setRelease_date(int release_date) {this.release_date = release_date;}
    public String getLyrics() {return lyrics;}
    public void setLyrics(String lyrics) {this.lyrics = lyrics;}
    public String getArtist() {return artist;}
    public void setArtist(String artist) {this.artist = artist;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getSong_id() {return id;}
    public void setSong_id(int song_id) {this.id = song_id;}
}
