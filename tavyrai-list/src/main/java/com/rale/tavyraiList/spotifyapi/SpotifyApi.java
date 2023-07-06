package com.rale.tavyraiList.spotifyapi;

import com.rale.tavyraiList.spotifyapi.modelsdto.ItemFromPlaylist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Component
public class SpotifyApi {
    @Autowired
    WebClient webClient;

    public List<ItemFromPlaylist> getUserPlayList(){

        List<ItemFromPlaylist> playlists = new ArrayList<>();
        boolean existMorePlayList = true;
        int offSet = 0;
        int limit = 50;
        return null;
    }

}
