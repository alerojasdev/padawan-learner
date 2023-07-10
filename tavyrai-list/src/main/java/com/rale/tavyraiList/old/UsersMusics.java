package com.rale.tavyraiList.old;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rale.tavyraiList.ralelyricsfinder.ShazamApi;
import com.rale.tavyraiList.spotifyapi.SpotifyApi;
import com.rale.tavyraiList.spotifyapi.modelsdto.ItemFromPlaylist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class UsersMusics {
    @Autowired
    SpotifyApi spotifyApi;

    private List<String> getPlaylistIdFromUser(){

        List<ItemFromPlaylist> userPlayList = spotifyApi.getUserPlayList();
        List<String> ids = userPlayList.stream().map(i->i.id).toList();
        return ids;
    }

    public List<String> getMusicsIdsFromUser(){
        List<String> idlist = getPlaylistIdFromUser();
        StringBuilder musics = new StringBuilder();

        for (String id: idlist){
            musics.append(spotifyApi.getMusicsFromPlaylist(id));
        }

        String musicsIdBody = musics.toString();

        String jsonIdBody = musicsIdBody.replaceAll("Track", "");

        ObjectMapper objectMapper = new ObjectMapper();

//        List<String> idList = Arrays.stream(jsonIdBody.split("\\}, Track\\{"))
//                .map(item -> item.split("id='")[1].split("',")[0])
//                .collect(Collectors.toList());

        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile("'https://api\\.spotify\\.com/v1/tracks/(.*?)'");
        Matcher matcher = pattern.matcher(jsonIdBody);
        while (matcher.find()) {
            String id = matcher.group(1);
            list.add(id);
        }
        return list;
    }


    public List<String> getMusicNameAndArtist(){
        //Ids de las musicas a ordenar
        List<String> ids = getMusicsIdsFromUser();
        String nameAndArtist = spotifyApi.getMusicNameAndArtist(ids).toString();
        //nombre y artista de la cancion
        String updatedText = nameAndArtist.replaceAll("[{}\\[\\]=]", " ");

        List<String> toList = Arrays.asList(updatedText.split(","));

        return toList;
    }

    public Map<String, String> getIdTrackAndTrackName(){
        Map<String, String> idAndSongName = new HashMap<>();
        List<String> key = getMusicsIdsFromUser();
        List<String> value = getMusicNameAndArtist();

        for (int i = 0; i < key.size(); i++){
            idAndSongName.put(key.get(i), value.get(i));
        }
        return idAndSongName;
    }
}
