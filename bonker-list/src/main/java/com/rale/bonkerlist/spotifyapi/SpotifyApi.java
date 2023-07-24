package com.rale.bonkerlist.spotifyapi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rale.bonkerlist.spotifyapi.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import java.text.SimpleDateFormat;
import java.util.*;
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
    public List<Track> getUserLiked(){
        List<Track> tracks = new ArrayList<>();
        boolean existsMoreTracks = true;
        int offset = 0;
        int limit = 50;
        while (existsMoreTracks){
            List<Track> tracksLikedFromUser = userLikedMusicRequest(offset, limit);
            tracks.addAll(tracksLikedFromUser);
            if (tracksLikedFromUser.size()<50){
                existsMoreTracks = false;
            }
            offset = offset + 50;
        }
        return tracks;
    }
    private List<Track> userLikedMusicRequest(int offSet, int limit){
        String resourceUri = "/me/tracks";
        String bodyRequest = webClient
                .get()
                .uri(uf->
                        uf.path(resourceUri)
                                .queryParam("offset", offSet)
                                .queryParam("limit", limit)
                                .build()
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RootFromUserLiked rootFromUserLiked = objectMapper.readValue(bodyRequest, RootFromUserLiked.class);
            List<Track> tracks = new ArrayList<>();
            rootFromUserLiked.items.forEach((t) -> tracks.add(t.track));
            return tracks;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
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
    public void insertMusic(String playlistId, List<String> tracksURI){
        boolean isMoreToInsert = true;
        int offset = 0;
        int upTo = 100;
        while(isMoreToInsert){
            if(tracksURI.size()-offset<=100){
                isMoreToInsert = false;
                upTo=tracksURI.size();
            }
            List<String> tracksToInsert = new ArrayList<>();
            for(int i=offset; i<upTo; i++){
                tracksToInsert.add(tracksURI.get(i));
            }
            insertMusicInternal(playlistId, tracksToInsert, offset);
            upTo = upTo + 100;
            offset = offset + 100;
        }
    }
    private void insertMusicInternal(String playlistId, List<String> tracksURI, int position){
        System.out.println("esta es la lista de uris to insert" + Arrays.toString(tracksURI.toArray()));
        String resourceUri = "/playlists/{playlistId}/tracks";

        Map map = Map.of(
                "uris", tracksURI,
                "position", position
        );
        String s = "";
        try{
            s = new ObjectMapper().writeValueAsString(map);
        }catch (Exception e){
            e.printStackTrace();
        }

        String body = webClient
                .post()
                .uri(uf->
                        uf.path(resourceUri)
                                .build(playlistId)
                )
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(s))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
//    private void insertMusicInternal(String playlistID, List<String> tracksUri){
//        String resourceUri = "/playlists/{playlistId}/tracks";
//        StringBuilder tracksUriToSend = new StringBuilder();
//        boolean firstTime = true;
//        for (String t: tracksUri){
//            if (!firstTime){
//                tracksUriToSend.append(",");
//            }
//            firstTime = false;
//            tracksUriToSend.append("\"");
//            tracksUriToSend.append(t);
//            tracksUriToSend.append("\"");
//        }
//        String bodyContent = String.format("""
//                {
//                    "uris": [%s],
//                    "position": 0
//                }
//                """, tracksUriToSend
//        );
//
//        System.out.println();
//        System.out.println("body content of uris.......................");
//        System.out.println();
//        System.out.println(bodyContent);
//        System.out.println();
//        System.out.println("body content of uris.......................");
//        System.out.println();
//
//        String requestBody = webClient
//                .post()
//                .uri(uf->
//                        uf.path(resourceUri)
//                                .build(playlistID)
//                )
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(bodyContent))
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//
//        System.out.println();
//        System.out.println("URL OF INSERT MUSICS.......................");
//        System.out.println();
//        System.out.println(requestBody);
//        System.out.println();
//        System.out.println("URL OF INSERT MUSICS.......................");
//        System.out.println();
//
//    }
    public void deletePlaylist(String playlistId){
        System.out.println("Borrando playlistId: " + playlistId);

        String resourceUri = "/playlists/{playlistId}/followers";
        webClient
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
}