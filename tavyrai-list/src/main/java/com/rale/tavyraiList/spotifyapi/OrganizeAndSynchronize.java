package com.rale.tavyraiList.spotifyapi;

import com.rale.tavyraiList.ralelanguagedetector.TextAnalysisApi;
import com.rale.tavyraiList.ralelyricsfinder.ShazamApi;
import com.rale.tavyraiList.spotifyapi.modelsdto.ItemFromPlaylist;
import com.rale.tavyraiList.spotifyapi.modelsdto.Track;
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
        System.out.println("OrganizeAndSyncronize.orgAndSync()");

        System.out.println("1. id, nombre de playlists");
        List<ItemFromPlaylist> userPlayList = spotifyApi.getUserPlayList();
        Map<String, String> playlistId_name = userPlayList.stream().collect(Collectors.toMap(i -> i.id, i -> i.name));
        System.out.println("ids y nombres de playlists: "+ playlistId_name);

//        // 1. todo esto no funciona.
//        System.out.println("2. Borrar playlists q comienzan con tl_ *");
//        playlistId_name.values().stream()
//                .filter(i->i.startsWith("tavyraiList_"))
//                .toList().forEach(i -> spotifyApi.deletePlaylist(i));

        List<SpotifyMusic> musicsFromSpotifyPlaylist = getSongsFromPlaylistsAndLiked(playlistId_name);
        fillInLanguageAndReleaseDates(musicsFromSpotifyPlaylist);
        List<String> languageAmount = getAmountOfMusicsByLanguage(musicsFromSpotifyPlaylist, 20);
        createPlaylistAndInsertSortedMusics(musicsFromSpotifyPlaylist, languageAmount);

        // todo
        // las listas tienen nombre dinamico por idioma en base a un prefijo tl_idioma
        // crear las playlist segun idioma y release date con sortSongs()
        // encontrar idiomas que tienen mas de 20 musicas en ese idioma
        // - SI se encuentra una playlist con el mismo nombre eliminar su contenido
        // - crear cache con un archivo
    }
    private List<String> getAmountOfMusicsByLanguage(List<SpotifyMusic> musicsFromSpotifyPlaylist, int threshold) {
        // Amount Of Musics By Language
        Map<String, Integer> langsAndTimes = new HashMap<>();
        for (SpotifyMusic spotifyMusic : musicsFromSpotifyPlaylist){
            if (!langsAndTimes.containsKey(spotifyMusic.language)){
                langsAndTimes.put(spotifyMusic.language, 1);
            } else {
                langsAndTimes.replace(spotifyMusic.language, langsAndTimes.get(spotifyMusic.language), langsAndTimes.get(spotifyMusic.language) + 1);
            }
        }
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> map : langsAndTimes.entrySet()){
            if (map.getValue() >= threshold){
                result.add(map.getKey());
            }
        }
        return result;
    }
    private void createPlaylistAndInsertSortedMusics(List<SpotifyMusic> musicsFromSpotifyPlaylist, List<String> languageAmount) {

        // idioma/listadeURLs
        Map<String, List<String>> lang_ListOfSongUri = new HashMap<>();
        for (SpotifyMusic spotifyMusic : musicsFromSpotifyPlaylist) {
            String idioma = spotifyMusic.language;
            String musicUri = spotifyMusic.spotifyMusicUri;
            if (languageAmount.contains(spotifyMusic.language)) {
                lang_ListOfSongUri.computeIfAbsent(idioma, k -> new ArrayList<>()).add(musicUri);
            } else {
                musicUri = spotifyMusic.spotifyMusicUri;
                lang_ListOfSongUri.computeIfAbsent("Others", k -> new ArrayList<>()).add(musicUri);
            }
        }

        //replace language for playlist id
        Map<String, List<String>> modifiedMap = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : lang_ListOfSongUri.entrySet()){

            String oldKey = entry.getKey();
            List<String> value = entry.getValue();

            String newKey = spotifyApi.createPlaylist("tl_" + entry.getKey(), "Playlist ordered by " + entry.getKey());

            modifiedMap.put(newKey, value);
            System.out.println("Creating playlist.......");

        }

        for (Map.Entry<String, List<String>> entry : modifiedMap.entrySet()){
            spotifyApi.insertMusic(entry.getKey(), entry.getValue());
            System.out.println("Inserting musics in playlist.......");
        }
    }



    private void fillInLanguageAndReleaseDates(List<SpotifyMusic> musicsFromSpotifyPlaylist) {
        System.out.println("4. buscar letras y release en shazam ** xx, 1900");
        for (SpotifyMusic spotifyMusic: musicsFromSpotifyPlaylist){
            try { Thread.sleep(1000L); } catch (Exception e) {}
            // 3. TODO hacer que getmetadata no retorne null nunca
            ShazamApi.ShazamMetadata songMetaData = shazamApi.getMetadata(spotifyMusic.title +" " +spotifyMusic.artistNames);
            if (songMetaData == null) {
                spotifyMusic.lyrics = "notLyricsFound";
                spotifyMusic.language = "noLang";
                spotifyMusic.releaseDate = 123;
                continue;
            }
            String lan = textAnalysisApi.detectLanguage(songMetaData.lyrics);
            spotifyMusic.language = lan;
            spotifyMusic.releaseDate = songMetaData.getReleaseDate();
            spotifyMusic.lyrics = songMetaData.lyrics;
            System.out.println();
            System.out.println(spotifyMusic.title + " " + spotifyMusic.releaseDate + " " + spotifyMusic.language);
        }
    }
    private List<SpotifyMusic> getSongsFromPlaylistsAndLiked(Map<String, String> playlistId_name) {
        List<SpotifyMusic> musicsFromSpotifyPlaylist = new ArrayList<>();
        System.out.println("3. id de musicas en playlists encontrados - nombre y artista de musica de spotify");
        for (String idPlaylist: playlistId_name.keySet()){
            List<Track> musicsFromPlaylist = spotifyApi.getMusicsFromPlaylist(idPlaylist);
            for (Track track: musicsFromPlaylist){
                musicsFromSpotifyPlaylist.add(new SpotifyMusic(track));
            }
        }
        // 2. TODO falta agregar los liked a musicsFromSpotifyPlaylist
        System.out.println("lista de musicas de los playlists encontrados"+ musicsFromSpotifyPlaylist);
        return musicsFromSpotifyPlaylist;
    }
    private static void sortSongs(List<SpotifyMusic> musicsFromSpotifyPlaylist, List<String> languagesFound, List<String> playlistByReleaseDate) {
        musicsFromSpotifyPlaylist.sort(new Comparator<SpotifyMusic>() {
            public int compare(SpotifyMusic o1, SpotifyMusic o2) {
                return o2.releaseDate - o1.releaseDate;
            }
        });
    }
    private static class SpotifyMusic{
        String spotifyMusicUri;
        String title;
        String artistNames;
        String lyrics;
        int releaseDate;
        String language;
        public SpotifyMusic(String idioma) {
            language = idioma;
        }
        public SpotifyMusic(Track track) {
            this.spotifyMusicUri = "spotify:track:"+track.id;
            this.title = track.name;
            this.artistNames = track.artists.stream().map(artist-> artist.name).collect(Collectors.joining(" "));
        }
        private String getShortLyrics() {
            if (lyrics == null)
                return "null";
            return lyrics.substring(0, Math.min(10, lyrics.length()));
        }
        @Override
        public String toString() {
            return "SpotifyMusic{" +
                    "stotifyMusicUri='" + spotifyMusicUri + '\'' +
                    ", title='" + title + '\'' +
                    ", artistNames='" + artistNames + '\'' +
                    ", lyrics='" + getShortLyrics() + '\'' +
//                    ", releaseDate=" + releaseDate +
                    ", language='" + language + '\'' +
                    '}';
        }
    }
}




