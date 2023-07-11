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
        System.out.println("2. Borrar playlists q comienzan con tavyraiList_ *");
        playlistId_name.values().stream()
                .filter(i->i.startsWith("tavyraiList_"))
                .toList().forEach(i -> spotifyApi.deletePlaylist(i));


        List<SpotifyMusic> musicsFromSpotifyPlaylist = getSongsFromPlaylistsAndLiked(playlistId_name);


        fillInLanguageAndReleaseDates(musicsFromSpotifyPlaylist);

        fillInLanguageAndReleaseDates();

        // todo
        // encontrar idiomas que tienen mas de 20 musicas en ese idioma
        // las listas tienen nombre dinamico por idioma en base a un prefijo tl_idioma

        System.out.println("5. separar las musicas por lenguajes y ordenarlos en playslist");
        List<String> playlistByEn = new ArrayList<>();
        List<String> playlistByEs = new ArrayList<>();
        List<String> playlistByOthersLanguages = new ArrayList<>();
        List<String> playlistByReleaseDate = new ArrayList<>();
        sortSongs(musicsFromSpotifyPlaylist, playlistByEn, playlistByEs, playlistByOthersLanguages, playlistByReleaseDate);


        System.out.println("6. crear playlists con los idiomas encontrados y playlist de releaseDate");
        spotifyApi.insertMusicIntoNewPlaylist("tavyraiList_EN", "playlist en ingles", playlistByEn);
        spotifyApi.insertMusicIntoNewPlaylist("tavyraiList_ES", "playlist en espanol", playlistByEs);
        spotifyApi.insertMusicIntoNewPlaylist("tavyraiList_OTHERS_L", "playlist en otros idiomas", playlistByOthersLanguages);
        spotifyApi.insertMusicIntoNewPlaylist("tavyraiList_byReleaseDate", "playlist por ano de lanzamiento", playlistByReleaseDate);

        // - SI se encuentra una playlist con el mismo nombre eliminar su contenido
        // - crear cache con un archivo
    }

    private void fillInLanguageAndReleaseDates(List<SpotifyMusic> musicsFromSpotifyPlaylist) {
        ArrayList<String> languages = new ArrayList<>();
        System.out.println("4. buscar letras y release en shazam ** xx, 1900");
        for (SpotifyMusic spotifyMusic: musicsFromSpotifyPlaylist){
            try { Thread.sleep(1000L); } catch (Exception e) {}
            // 3. TODO hacer que getmetadata no retorne null nunca
            ShazamApi.ShazamMetadata songMetaData = shazamApi.getMetadata(spotifyMusic.title +" " +spotifyMusic.artistNames);
            if (songMetaData == null) {
                spotifyMusic.lyrics = "notLyricsFound";
                spotifyMusic.language = "noLang";
                spotifyMusic.releaseDate = 123;
                languages.add("noLang");
                continue;
            }
            String lan = textAnalysisApi.detectLanguage(songMetaData.lyrics);
            spotifyMusic.language = lan;
            spotifyMusic.releaseDate = songMetaData.getReleaseDate();
            spotifyMusic.lyrics = songMetaData.lyrics;
            System.out.println("full song info " + spotifyMusic);
            for (String lang : languages){
                if (!lan.contains(lang));
                languages.add(lan);
            }
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

    private static void sortSongs(List<SpotifyMusic> musicsFromSpotifyPlaylist, List<String> playlistByEn, List<String> playlistByEs, List<String> playlistByOthersLanguages, List<String> playlistByReleaseDate) {
        {// 3 formas de comparar
            // sort the List of SpotifyMusic Objects by date release using comparator with list instance function to sort
            // so in this way we not use the compareTo() method, that will be a longer code
            musicsFromSpotifyPlaylist.sort(new Comparator<SpotifyMusic>() {
                public int compare(SpotifyMusic o1, SpotifyMusic o2) {
                    return o2.releaseDate - o1.releaseDate;
                }
            });

            // using lambda to sort the list of SpotifyMusic Objects by date
            // musicsFromSpotifyPlaylist.sort((o1, o2) ->o1.releaseDate.compareTo(o2.releaseDate));

            // usin a super short lambda comparator
//             musicsFromSpotifyPlaylist.sort(Comparator.comparing(o -> o.releaseDate));
        }

        for (SpotifyMusic spotifyMusic : musicsFromSpotifyPlaylist){
            if (spotifyMusic.language.equals("es")){
                playlistByEs.add(spotifyMusic.stotifyMusicUri);
            }

            if (spotifyMusic.language.equals("en")){
                playlistByEn.add(spotifyMusic.stotifyMusicUri);
            }

            if (!spotifyMusic.language.equals("es") && !spotifyMusic.language.equals("en")){
                playlistByOthersLanguages.add(spotifyMusic.stotifyMusicUri);
            }

            playlistByReleaseDate.add(spotifyMusic.stotifyMusicUri);

        }

        for (SpotifyMusic spotifyMusic : musicsFromSpotifyPlaylist)
        {
            System.out.println("RELEASE DATE = " + spotifyMusic.releaseDate);
        }
    }

    private static class SpotifyMusic{
        String stotifyMusicUri;
        String title;
        String artistNames;
        String lyrics;
        int releaseDate;
        String language;

        public SpotifyMusic(Track track) {
            this.stotifyMusicUri = "spotify:track:"+track.id;
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
                    "stotifyMusicUri='" + stotifyMusicUri + '\'' +
                    ", title='" + title + '\'' +
                    ", artistNames='" + artistNames + '\'' +
                    ", lyrics='" + getShortLyrics() + '\'' +
//                    ", releaseDate=" + releaseDate +
                    ", language='" + language + '\'' +
                    '}';
        }
    }

}


