package com.rale.tavyraiList.arlyricsfinder;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.*;

public class ShazamLyricsProvider implements LyricsProvider {
    private static final String SEARCH_URL_ONE = "https://www.shazam.com/services/search/v4/en-US/PY/web/search?term=";
    private static final String SEARCH_URL_TWO = "&numResults=3&offset=0&types=artists,songs&limit=3";
    private static final String LYRICS_URL_ONE = "https://www.shazam.com/discovery/v5/en-US/PY/web/-/track/";
    private static final String LYRICS_URL_TWO = "?shazamapiversion=v3&video=v3";

    @Override
    public String fetchLyrics(String artist, String songName) {
        return fetchLyrics(songName+" "+artist);
    }
    public String fetchLyrics(String songName) {

        //          Search the music name
        HttpRequest songLyricsUrlRequest = HttpRequest.get(SEARCH_URL_ONE + songName.replaceAll(" ", "%20") + SEARCH_URL_TWO);
        //          Obtain URL of the lyrics
        String response = songLyricsUrlRequest.body();
        //          Parse the JSON string into a JsonObject
        JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);
        //          Get the music Key
        int musicKey = jsonObject
                .getAsJsonObject("tracks")
                .getAsJsonArray("hits")
                .get(0)
                .getAsJsonObject()
                .getAsJsonObject("track")
                .get("key")
                .getAsInt();

        HttpRequest getLyricsContainer = HttpRequest.get(LYRICS_URL_ONE + musicKey + LYRICS_URL_TWO);

        String json = getLyricsContainer.body();

        JsonParser parser = new JsonParser();
        //          Parse the JSON
        JsonElement element = parser.parse(json);
        //          Access the lyrics text
        JsonObject jsonObject2 = element.getAsJsonObject();
        JsonArray sections = jsonObject2.getAsJsonArray("sections");
        JsonObject lyricsSection = sections.get(1).getAsJsonObject();
        JsonArray lyricsText = lyricsSection.getAsJsonArray("text");
        StringBuilder abc = new StringBuilder();
        for (JsonElement text : lyricsText) {
            abc.append(text);
        }
        return abc.toString();
    }
}
