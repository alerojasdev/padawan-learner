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

        // 1. todo esto no funciona.
        System.out.println("2. Borrar playlists q comienzan con tl_ *");
        playlistId_name.values().stream()
                .filter(i->i.startsWith("tavyraiList_"))
                .toList().forEach(i -> spotifyApi.deletePlaylist(i));

        List<SpotifyMusic> musicsFromSpotifyPlaylist = getSongsFromPlaylistsAndLiked(playlistId_name);

        fillInLanguageAndReleaseDates(musicsFromSpotifyPlaylist);

        System.out.println(groupByLangWithCount(musicsFromSpotifyPlaylist));


        // encontrar idiomas que tienen mas de 20 musicas en ese idioma

//        System.out.println("5. separar las musicas por lenguajes y ordenarlos en playslist");
//        List<String> playlistByEn = new ArrayList<>();
//        List<String> playlistByEs = new ArrayList<>();
//        List<String> playlistByOthersLanguages = new ArrayList<>();
//        List<String> playlistByReleaseDate = new ArrayList<>();
//        sortSongs(musicsFromSpotifyPlaylist, playlistByEn, playlistByEs, playlistByOthersLanguages, playlistByReleaseDate);
//
//
//        System.out.println("6. crear playlists con los idiomas encontrados y playlist de releaseDate");
//        spotifyApi.insertMusicIntoNewPlaylist("tavyraiList_EN", "playlist en ingles", playlistByEn);
//        spotifyApi.insertMusicIntoNewPlaylist("tavyraiList_ES", "playlist en espanol", playlistByEs);
//        spotifyApi.insertMusicIntoNewPlaylist("tavyraiList_OTHERS_L", "playlist en otros idiomas", playlistByOthersLanguages);
//        spotifyApi.insertMusicIntoNewPlaylist("tavyraiList_byReleaseDate", "playlist por ano de lanzamiento", playlistByReleaseDate);

        // - SI se encuentra una playlist con el mismo nombre eliminar su contenido
        // - crear cache con un archivo
    }

    private static void insertMusicsInPlaylsits(List<SpotifyMusic> musicsFromSpotifyPlaylist, List<String> languagesFound) {

//        List<String> playlisLantUris = null;
//
//        SpotifyApi spotifyApi1 = null;
//
//        for (String e : languagesFound){
//            for (SpotifyMusic spotifyMusic : musicsFromSpotifyPlaylist){
//                if (e == spotifyMusic.language){
//                    playlisLantUris.add(spotifyMusic.spotifyMusicUri);
//                }
//            }
//            spotifyApi1.insertMusicIntoNewPlaylist("tl_"+ e, "playlist by " + e, playlisLantUris);
//            playlisLantUris.clear();
//        }

//        List<String> playlistReleaseUris = null;
//
//
//        for (String e : languagesFound){
//            for (SpotifyMusic spotifyMusic : musicsFromSpotifyPlaylist){
//                if (e != spotifyMusic.language){
//                    playlistReleaseUris.add(spotifyMusic.spotifyMusicUri);
//                }
//            }
//            spotifyApi1.insertMusicIntoNewPlaylist("tl_"+ e, "playlist by " + e, playlistReleaseUris);
//            playlistReleaseUris.clear();
//        }

    }

    public static void main(String[] args) {
        List<SpotifyMusic> songs = new ArrayList<>();

        songs.add(new SpotifyMusic("es"));
        songs.add(new SpotifyMusic("es"));
        songs.add(new SpotifyMusic("es"));
        songs.add(new SpotifyMusic("es"));
        songs.add(new SpotifyMusic("en"));
        songs.add(new SpotifyMusic("en"));
        songs.add(new SpotifyMusic("en"));
        songs.add(new SpotifyMusic("de"));
        songs.add(new SpotifyMusic("fr"));
        songs.add(new SpotifyMusic("pr"));

        Map<String, Integer> groupped = new OrganizeAndSynchronize().groupByLangWithCount(songs);
        groupped.entrySet().stream()
                .forEach(e->System.out.println("idioma: "+ e.getKey()+", candidad: "+ e.getValue()));
    }

    private Map<String, Integer> groupByLangWithCount(List<SpotifyMusic> musicsFromSpotifyPlaylist, int threshold) {

        // todo
        // crear las playlist segun idioma y release date con sortSongs()
        // las listas tienen nombre dinamico por idioma en base a un prefijo tl_idioma
        // ponemos los lenguages encontrados en una lista

        Map<String, Integer> languageCountMap = new HashMap<>();

        for (SpotifyMusic music : musicsFromSpotifyPlaylist) {
            String language = music.language;
            languageCountMap.put(language, languageCountMap.getOrDefault(language, 0) + 1);
        }

        List<List<String>> playlistUrisByLan = new ArrayList<>();
        for (SpotifyMusic spotifyMusic : musicsFromSpotifyPlaylist){
            if (languageCountMap.get(spotifyMusic.language) <= threshold){
                playlistUrisByLan.add(List.of(spotifyMusic.spotifyMusicUri));
            }
        }

        return languageCountMap;
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
            System.out.println("full song info " + spotifyMusic);

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


//        for (SpotifyMusic spotifyMusic : musicsFromSpotifyPlaylist){
//            if (spotifyMusic.language.equals("es")){
//                playlistByEs.add(spotifyMusic.stotifyMusicUri);
//            }
//
//            if (spotifyMusic.language.equals("en")){
//                playlistByEn.add(spotifyMusic.stotifyMusicUri);
//            }
//
//            if (!spotifyMusic.language.equals("es") && !spotifyMusic.language.equals("en")){
//                playlistByOthersLanguages.add(spotifyMusic.stotifyMusicUri);
//            }
//
//            playlistByReleaseDate.add(spotifyMusic.stotifyMusicUri);
//
//        }

        for (SpotifyMusic spotifyMusic : musicsFromSpotifyPlaylist)
        {
            System.out.println("RELEASE DATE = " + spotifyMusic.releaseDate);
        }
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


