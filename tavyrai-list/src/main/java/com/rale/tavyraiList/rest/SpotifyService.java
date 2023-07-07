package com.rale.tavyraiList.rest;

import com.rale.tavyraiList.spotifyapi.SpotifyApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class SpotifyService {
    @Autowired
    SpotifyApi spotifyApi;
    @GetMapping(value = "/user-playlists")
    public String getUserPlaylist() {
        return Arrays.toString(spotifyApi.getUserPlayList().toArray());
    }
    @GetMapping(value = "/user-musics")
    public String getUserMusics() {
        return spotifyApi.getMusicsFromPlaylist("4la5n3NiOkJxePwtWTWsqv").toString();
    }
    @GetMapping(value = "/create-playlist")
    public String createPlaylist() {
        return spotifyApi.createPlaylist("new playlist_tavyrailist", "musics lofi");
    }
    @GetMapping(value = "/delete-playlist")
    public void deletePlaylist() {
        spotifyApi.deletePlaylist("4la5n3NiOkJxePwtWTWsqv");
    }
}