package com.rale.tavyraiList.spotifyapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.rale.tavyraiList.spotifyapi.modelsdto.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class UsersMusics {
    @Autowired
    SpotifyApi spotifyApi;


    private List<String> getPlaylistIdFromUser(){

        String text = spotifyApi.getUserPlayList().toString();

        List<String> idList = Arrays.stream(text.split("\\), ItemFromPlaylist\\("))
                .filter(item -> item.contains("id="))
                .map(item -> item.split("id=")[1].split(",")[0])
                .collect(Collectors.toList());

        return idList;
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
