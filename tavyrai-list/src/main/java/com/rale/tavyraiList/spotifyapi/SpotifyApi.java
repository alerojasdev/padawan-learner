package com.rale.tavyraiList.spotifyapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rale.tavyraiList.spotifyapi.modelsdto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SpotifyApi {
    @Autowired
    WebClient webClient;

    public List<ItemFromPlaylist> getUserPlayList(){

        List<ItemFromPlaylist> playlists = new ArrayList<>();
        boolean existMorePlayList = true;
        int offSet = 0;
        int limit = 50;
        while (existMorePlayList){
            List<ItemFromPlaylist> playListFromSpotify = getUserPlaylistPortion(offSet, limit);
            playlists.addAll(playListFromSpotify);
            if (playListFromSpotify.size()<50){
                existMorePlayList = false;
            }
            offSet = offSet + 50;
        }
        return playlists;
    }

    private List<ItemFromPlaylist> getUserPlaylistPortion(int offset, int limit) {
        String resourceUri = "/me/playlists";
        String body = webClient
                .get()
                .uri(uf->
                        uf.path(resourceUri)
                                .queryParam("offset", offset)
                                .queryParam("limit", limit)
                                .build()
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();
        UriBuilder uriBuilder;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RootFromPlaylist rootFromMusicOfPlaylist = objectMapper.readValue(body, RootFromPlaylist.class);
            System.out.println(rootFromMusicOfPlaylist.items);
            return rootFromMusicOfPlaylist.items;
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Track> getMusicsFromPlaylist(String playlistId){
        List<Track> tracks = new ArrayList<>();
        boolean existMoreTracks = true;
        int offSet = 0;
        int limit = 50;
        while (existMoreTracks){
            List<Track> tracksFromSpotify = musicsFromPlaylistRequest(playlistId, offSet, limit);
            tracks.addAll(tracksFromSpotify);
            if (tracksFromSpotify.size()<50){
                existMoreTracks = false;
            }
            offSet = offSet + 50;
        }
        return tracks;
    }
    private List<Track> musicsFromPlaylistRequest(String playlistId, int offset, int limit){
        String resourceUri = "/playlists/{playlistId}/tracks";
        String bodyRequest = webClient
                .get()
                .uri(uf->
                        uf.path(resourceUri)
                                .queryParam("offset", offset)
                                .queryParam("limit", limit)
                                .build(playlistId)
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            objectMapper.setDateFormat(dateFormat);
            RootFromMusicOfPlaylist rootFromMusicOfPlaylist = objectMapper.readValue(bodyRequest, RootFromMusicOfPlaylist.class);
            System.out.println(rootFromMusicOfPlaylist.items);
            List<Track> tracks = new ArrayList<>();
            rootFromMusicOfPlaylist.items.forEach((t)-> tracks.add(t.track));
            return tracks;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

//    public List<Track> getUserLiked(){
//        List<Track> tracks = new ArrayList<>();
//        boolean existsMoreTracks = true;
//        int offset = 0;
//        int limit = 50;
//        while (existsMoreTracks){
//            List<Track> tracksLikedFromUser = userLikedMusicRequest(offset, limit);
//            tracks.addAll(tracksLikedFromUser);
//            if (tracksLikedFromUser.size()<50){
//                existsMoreTracks = false;
//            }
//            offset = offset + 50;
//        }
//        return tracks;
//    }

//    private List<Track> userLikedMusicRequest(int offSet, int limit){
//        String resourceUri = "/me/tracks";
//        String bodyRequest = webClient
//                .get()
//                .uri(uf->
//                        uf.path(resourceUri)
//                                .queryParam("offset", offSet)
//                                .queryParam("limit", limit)
//                                .build()
//                )
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            RootFromUserLiked rootFromUserLiked = objectMapper.readValue(bodyRequest, RootFromUserLiked.class);
//            List<Track> tracks = new ArrayList<>();
//            rootFromUserLiked.items.forEach((t) -> tracks.add(t.track));
//            return tracks;
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
    public String createPlaylist(String name, String description){
        String resourceUri = "/users/{userId}/playlists";
        String bodyContent = String.format("""
                {
                "name": "%s",
                "description": "%s",
                "public": false
                }
                """
                , name
                , description
        );
        try {
            String userId = getUserId();
            String bodyRequest = webClient
                    .post()
                    .uri(uf->
                            uf.path(resourceUri)
                                    .build(userId)
                    )
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(bodyContent))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            ObjectMapper objectMapper = new ObjectMapper();
            RootFromPlaylistCreated rootFromPlaylistCreated = objectMapper.readValue(bodyRequest, RootFromPlaylistCreated.class);
            System.out.println(rootFromPlaylistCreated.id);
            return rootFromPlaylistCreated.id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void insertMusic(String playlistId, List<String> tracksUri){
        insertMusicInternal(playlistId, tracksUri);
    }
    private void insertMusicInternal(String playlistID, List<String> tracksUri){
        String resourceUri = "/playlists/{playlistId}/tracks";
        StringBuilder tracksUriToSend = new StringBuilder();
        boolean firstTime = true;
        for (String t: tracksUri){
            if (!firstTime){
                tracksUriToSend.append(",");
            }
            firstTime = false;
            tracksUriToSend.append("\"");
            tracksUriToSend.append(t);
            tracksUriToSend.append("\"");
        }
        String bodyContent = String.format("""
                {
                    "uris": [%s],
                    "position": 0
                }
                """, tracksUriToSend
        );
        System.out.println(bodyContent);
        String bodyContentRequest = webClient
                .post()
                .uri(uf->
                        uf.path(resourceUri)
                                .build(playlistID)
                )
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(bodyContent))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
    public void deletePlaylist(String playlistId){
        String resourceUri = "/playlists/{playlistId}/followers";
        String bodyContent = webClient
                .delete()
                .uri(uf->
                        uf.path(resourceUri)
                                .build(playlistId)
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
    private String getUserId(){
        String resourceUri = "/me";
        String bodyContent = webClient
                .get()
                .uri(resourceUri)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String pattern = "\"id\"\\s*:\\s*\"([^\"]+)\"";

        Pattern reggex = Pattern.compile(pattern);
        Matcher matcher = reggex.matcher(bodyContent);
        if (matcher.find()){
            String id = matcher.group(1);
            System.out.println(id);
            return id;
        }
        return null;
    }

    public List<String> getMusicNameAndArtist(List<String> musicsId){
        List<String> nameAndArtist = new ArrayList<>();
        int offset = 0;
        int limit = 50;
        for (String idList : musicsId){
            nameAndArtist.add(requestAllUserMusicsNameAndArtist(idList, offset, limit).toString());
        }
        return nameAndArtist;
    }

    private List<String> requestAllUserMusicsNameAndArtist(String musicId, int offset, int limit){
        String resource = "/tracks/{musicId}";
        String responseBody = webClient
                .get()
                .uri(uf-> uf.path(resource)
                        .queryParam("offset", offset)
                        .queryParam("limit", limit)
                        .build(musicId))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Track responseContainerInTrackObject = objectMapper.readValue(responseBody, Track.class);
            List<String> trackProof = new ArrayList<>();
            Map<String, String> track = new HashMap<>();
            responseContainerInTrackObject.artists.forEach((t) -> track.put(responseContainerInTrackObject.name, t.name));
            trackProof.add(track.toString());
            return trackProof;
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}


