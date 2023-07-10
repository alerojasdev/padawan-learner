package com.rale.tavyraiList.spotifyapi;

import com.rale.tavyraiList.ralelanguagedetector.TextAnalysisApi;
import com.rale.tavyraiList.ralelyricsfinder.ShazamApi;
import com.rale.tavyraiList.spotifyapi.modelsdto.Artist;
import com.rale.tavyraiList.spotifyapi.modelsdto.ItemFromPlaylist;
import com.rale.tavyraiList.spotifyapi.modelsdto.Track;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class OrganizeAndSynchronize {

    @Autowired
    SpotifyApi spotifyApi;

    @Autowired
    ShazamApi shazamApi;

    @Autowired
    TextAnalysisApi textAnalysisApi;
    public void orgAndSync() {

        // - otener liked musics

        System.out.println("OrganizeAndSyncronize.orgAndSync()");

        System.out.println("1. id, nombre de playlists");
        List<ItemFromPlaylist> userPlayList = spotifyApi.getUserPlayList();
        Map<String, String> playlistId_name = userPlayList.stream().collect(Collectors.toMap(i -> i.id, i -> i.name));
        System.out.println("ids y nombres de playlists: "+ playlistId_name);

        System.out.println("2. Borrar playlists q comienzan con t_ *");
        playlistId_name.values().stream()
                .filter(i->i.startsWith("t_"))
                .toList().forEach(i -> spotifyApi.deletePlaylist(i));

        System.out.println("3. id de musicas en playlists encontrados - nombre y artista de musica de spotify");

        List<SpotifyMusic> musicsFromSpotifyPlaylist = new ArrayList<>();
        for (String idPlaylist: playlistId_name.keySet()){
            List<Track> musicsFromPlaylist = spotifyApi.getMusicsFromPlaylist(idPlaylist);
            for (Track track: musicsFromPlaylist){
                musicsFromSpotifyPlaylist.add(new SpotifyMusic(track));
            }
        }
        System.out.println("lista de musicas de los playlists encontrados"+ musicsFromSpotifyPlaylist);

        System.out.println("4. buscar letras y release en shazam ** xx, 1900");

        Map<String, String> musicId_lan = new HashMap<>();
        Map<String, Date> musicId_releaseDate = new HashMap<>();
        for (SpotifyMusic spotifyMusic: musicsFromSpotifyPlaylist){
            int i = 1;
            System.out.println(i);
            if (shazamApi.fetchLyrics(spotifyMusic.title, spotifyMusic.artistNames) != null) {

                try { Thread.sleep(1000L); } catch (Exception e) {}
                System.out.println();
                System.out.println("song " + spotifyMusic.title);
                String lyrics = shazamApi.fetchLyrics(spotifyMusic.title + " " + spotifyMusic.artistNames);
                String lan = textAnalysisApi.detectLanguage(lyrics);
                Date releaseDate = shazamApi.getMetadata(spotifyMusic.title + " " + spotifyMusic.artistNames).releaseDate;
                musicId_lan.put(spotifyMusic.musicId, lan);
                musicId_releaseDate.put(spotifyMusic.musicId, releaseDate);

            } else {
                musicId_lan.put(spotifyMusic.musicId, "desc");
            }
            i  = i + 1;
        }

        System.out.println("Lista de musicas con sus idiomas" + musicId_lan);
        System.out.println("*************************");
        System.out.println("Lista de musicas con su fecha de lanzamiento" + musicId_releaseDate);


//        List<Object> flat = musicsIds.stream().flatMap(List::stream).collect(Collectors.toList());

//        try { Thread.sleep(1000L); } catch (Exception e) {}

        // - buscar letras y release en shazam ** "xx", "1990"
        // - buscar idioma con textanalysis ** "desc"


        // - conglomerar en una lista DTO, idDeSpotify, release, idioma
        // - encontrar idiomas con X o mas musicas
        // - crear playlists con los idiomas encontrados y playlist de releaseDate
        // - insertar musicas por idioma en las playlists
        // - insertar muscisas por release date

    }


    @ToString
    private static class SpotifyMusic {

        /*
        1. no me importa el dinero - autenticos decadentes
        2. no me importa el dinero linve, las vegas - autenticos decadentes julieta venegas
         */
//        String playlistId;
        String musicId;
        String title;
        String artistNames;

        String lyrics;
        String language;

        public SpotifyMusic(String language, Date releaseDate) {
            this.language = language;
            this.releaseDate = releaseDate;
        }

        Date releaseDate;

        public SpotifyMusic(Track track) {
            this.musicId = track.id;
            this.title = track.name;
            this.artistNames = track.artists.stream().map(artist-> artist.name).collect(Collectors.joining(" "));
        }
    }

}


