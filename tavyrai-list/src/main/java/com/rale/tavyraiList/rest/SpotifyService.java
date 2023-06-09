package com.rale.tavyraiList.rest;

import com.rale.tavyraiList.old.UsersMusics;
import com.rale.tavyraiList.spotifyapi.OrganizeAndSynchronize;
import com.rale.tavyraiList.spotifyapi.SpotifyApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class SpotifyService {
    @Autowired
    SpotifyApi spotifyApi;
    @Autowired
    UsersMusics usersMusics;
    @Autowired
    TaskExecutor te;
    @Autowired
    OrganizeAndSynchronize oas;
    @GetMapping(value = "/user-playlists")
    public String getUserPlaylist() {
        return Arrays.toString((spotifyApi.getUserPlayList().toArray()));
    }
    @GetMapping(value = "/ord-sinc")
    public String getUserMusics() {
       oas.orgAndSync();
//        te.execute( () -> oas.orgAndSync());
        return "Done";
    }
    @GetMapping(value = "/create-playlist")
    public String createPlaylist() {
        return spotifyApi.createPlaylist("new playlist_tavyrailist", "usersMusics lofi");
    }
//    @GetMapping(value = "/delete-playlist")
//    public void deletePlaylist() {
//        spotifyApi.deletePlaylist("4pX7OzJRG18SVFloqILHDZ");
//    }
}