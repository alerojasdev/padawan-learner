package com.rale.bonkerlist.rest;
import com.rale.bonkerlist.spotifyapi.OrganizeAndSynchronize;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SpotifyService {
    @Autowired
    OrganizeAndSynchronize oas;
    @GetMapping(value = "/org-sinc")
    public String getUserMusics() {
        oas.orgAndSync();
//        te.execute( () -> oas.orgAndSync());
        return "Done";
    }
    @PostMapping(value = "/upload")
    public List<String> handleFileUpload(@RequestParam("files") MultipartFile[] files) {
        System.out.println("Get in handleFileUpload()....................................");
        List<String> metadataList = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String metadata = extractMetadata(file);
                metadataList.add(metadata);
            }
        }

        return metadataList;
    }
    private String extractMetadata(MultipartFile file) {
        try {
            // Convert the MultipartFile to a regular File
            File tempFile = File.createTempFile("temp", null);
            file.transferTo(tempFile);

            // Load the audio file using JAudioTagger
            AudioFile audioFile = AudioFileIO.read(tempFile);

            // Get the metadata fields from the audio file
            String title = audioFile.getTag().getFirst(FieldKey.TITLE);
            String artist = audioFile.getTag().getFirst(FieldKey.ARTIST);

            // Cleanup - delete the temporary file
            tempFile.delete();

            // Return the formatted metadata
            return "Metadata: Title - " + title + ", Artist - " + artist + ", Duration - ";
        } catch (IOException | org.jaudiotagger.tag.TagException | org.jaudiotagger.audio.exceptions.CannotReadException |
                 org.jaudiotagger.audio.exceptions.ReadOnlyFileException |
                 org.jaudiotagger.audio.exceptions.InvalidAudioFrameException e) {
            // Handle any exceptions that might occur during metadata extraction
            e.printStackTrace();
            return "Error extracting metadata";
        }
    }
}