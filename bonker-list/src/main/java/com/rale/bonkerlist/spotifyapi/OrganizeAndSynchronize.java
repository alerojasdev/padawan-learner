package com.rale.bonkerlist.spotifyapi;

import com.rale.bonkerlist.languagedetector.TextAnalysisApi;
import com.rale.bonkerlist.lyricsfinder.ShazamApi;
import com.rale.bonkerlist.spotifyapi.dto.ItemFromPlaylist;
import com.rale.bonkerlist.spotifyapi.dto.SpotifyMusic;
import com.rale.bonkerlist.spotifyapi.dto.Track;
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
        List<ItemFromPlaylist> userPlayList = spotifyApi.getUserPlayList();
        Map<String, String> playlist_Id_name = userPlayList.stream().collect(Collectors.toMap(i -> i.id, i -> i.name));

        for (Map.Entry<String, String> entry : playlist_Id_name.entrySet()){
            if (entry.getValue().startsWith("bl_")){
                spotifyApi.deletePlaylist(entry.getKey());
            }
        }
        List<SpotifyMusic> musicsFromSpotifyPlaylist = getSongsFromPlaylistsAndLiked(playlist_Id_name);
        fillInLanguageAndReleaseDates(musicsFromSpotifyPlaylist);
        List<String> languageAmount = getAmountOfMusicsByLanguage(musicsFromSpotifyPlaylist, 20);
        createPlaylistAndInsertSongsByLan(musicsFromSpotifyPlaylist, languageAmount);
        createPlaylistAndInsertSongsByReleaseDate(musicsFromSpotifyPlaylist);
    }
    private void createPlaylistAndInsertSongsByReleaseDate(List<SpotifyMusic> musicsFromSpotifyPlaylist) {
        sortSongsByReleaseDate(musicsFromSpotifyPlaylist);
        List<String> urisByRelease = new ArrayList<>();
        for (SpotifyMusic spotifyMusic : musicsFromSpotifyPlaylist){
            urisByRelease.add(spotifyMusic.spotifyMusicUri);
        }
        System.out.println("Creating playlist by release..................");
        String id = spotifyApi.createPlaylist("bl_release_date", "sorted by release date");
        System.out.println("Inserting songs in playlist by release..................");
        spotifyApi.insertMusic(id, urisByRelease);
    }
    private void createPlaylistAndInsertSongsByLan(List<SpotifyMusic> musicsFromSpotifyPlaylist, List<String> languageAmount) {
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
        Map<String, List<String>> modifiedMap = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : lang_ListOfSongUri.entrySet()){
            List<String> value = entry.getValue();
            String newKey = spotifyApi.createPlaylist("bl_" + entry.getKey(), "Playlist ordered by " + entry.getKey());
            modifiedMap.put(newKey, value);
            System.out.println("Creating playlist by language............");
            System.out.println();
        }
        for (Map.Entry<String, List<String>> entry : modifiedMap.entrySet()){
            spotifyApi.insertMusic(entry.getKey(), entry.getValue());
            System.out.println("Inserting songs in playlist.......");
            System.out.println();
        }
    }
    private List<String> getAmountOfMusicsByLanguage(List<SpotifyMusic> musicsFromSpotifyPlaylist, int threshold) {
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

    private void fillInLanguageAndReleaseDates(List<SpotifyMusic> musicsFromSpotifyPlaylist) {
        System.out.println();
        System.out.println("4. buscar letras y release en shazam ** xx, 1900");
        System.out.println();
        for (SpotifyMusic spotifyMusic: musicsFromSpotifyPlaylist){
            try { Thread.sleep(1000L); } catch (Exception e) {}
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
            System.out.println(spotifyMusic.title + " / by / " + spotifyMusic.artistNames + " " + spotifyMusic.releaseDate + " " + spotifyMusic.language);
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
        List<Track> likedMusics;
        likedMusics = spotifyApi.getUserLiked();
        System.out.println("Inserting liked songs.....");
        for (Track track: likedMusics){
            if (!musicsFromSpotifyPlaylist.contains(track)){
                musicsFromSpotifyPlaylist.add(new SpotifyMusic(track));
            }
        }
        return musicsFromSpotifyPlaylist;
    }
    private static void sortSongsByReleaseDate(List<SpotifyMusic> musicsFromSpotifyPlaylist) {
        musicsFromSpotifyPlaylist.sort(new Comparator<SpotifyMusic>() {
            public int compare(SpotifyMusic o1, SpotifyMusic o2) {
                return o2.releaseDate - o1.releaseDate;
            }
        });
    }
}
