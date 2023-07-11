package com.rale.tavyraiList.old;

import com.rale.tavyraiList.ralelanguagedetector.TextAnalysisApi;
import com.rale.tavyraiList.ralelyricsfinder.ShazamApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GetLanguageAndRelease {
    @Autowired
    ShazamApi shazamApi;
    @Autowired
    UsersMusics usersMusics;
    @Autowired
    TextAnalysisApi textAnalysisApi;

    public String getMusicsLanguage(){

        //musics names
        List<String> musicsBunchNames = usersMusics.getMusicNameAndArtist();

        //map -> key is the id track and the value the song name. The value song name will be change for the language of the music,
        // so it will return a map -> key id track / value language of music
        Map<String, String> replaceValueForLanguage = usersMusics.getIdTrackAndTrackName();

        //map -> key is the id track and the value the song name. The value song name will be changed for the Release Date of the music,
        // so it will return a map -> key id track / value Release Date
        Map<String, String> replaceValueForReleaseDate = usersMusics.getIdTrackAndTrackName();

        //the keys to point to the specific music id in the map
        List<String> key = usersMusics.getMusicsIdsFromUser();

        for (String songName : musicsBunchNames){

            if (shazamApi.fetchLyrics(songName) == null){
                continue;
            }

            System.out.println("******************");
            //getting the song lyrics
            String lyrics = shazamApi.fetchLyrics(songName);
            System.out.println(songName);
            System.out.println();

            // getting the song language
            String lang = textAnalysisApi.detectLanguage(lyrics);
            System.out.println(lang);
            System.out.println("******************");

            //store each song language result in the map replaceValueForLanguage
            for (Map.Entry<String, String> entry : replaceValueForLanguage.entrySet()){
                if (songName.equals(entry.getValue())){
                    String getKey = entry.getKey();
                    replaceValueForLanguage.replace(getKey, songName, lang);
                }

            }

            //store each song Release Date result in the map replaceValueForReleaseDate
//            Date releaseDate = shazamApi.getMetadata(songName).releaseDate;

            for (Map.Entry<String, String> entry : replaceValueForReleaseDate.entrySet()){
                if (songName.equals(entry.getValue())){
                    String getKey = entry.getKey();
                    replaceValueForReleaseDate.replace(getKey, songName, lang);
                }
            }

        }

        return replaceValueForLanguage.toString();

    }
}
