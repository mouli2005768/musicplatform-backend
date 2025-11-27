package klu.controller;

import klu.modal.Album;

import klu.modal.Song;
import klu.repository.AlbumRepository;

import klu.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/")
@CrossOrigin(
        origins = {"http://localhost:5173", "http://localhost:8080"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
public class MusicController {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private AlbumRepository albumRepository;

    private final String UPLOAD_DIR = "uploads";
    
    

    

    // ================= SONGS =================
    @PostMapping("/songs")
    public Song addSong(@RequestBody Song song) {
        if (song.getImageUrl() == null || song.getImageUrl().isEmpty()) {
            song.setImageUrl(getBaseUrl() + "/images/default.png");
        }

       

        return songRepository.save(song);
    }
    @DeleteMapping("/songs/{id}")
    public void deleteSong(@PathVariable Long id) {
        songRepository.deleteById(id);
    }

    // Upload audio + optional image
    @PostMapping("/songs/upload")
    public Song uploadSong(
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam String name,
            @RequestParam(required = false) String description
    ) throws IOException {

        // ----------------- Save audio -----------------
        String audioFileName = System.currentTimeMillis() + "_" +
                file.getOriginalFilename().replaceAll("[^a-zA-Z0-9._-]", "_");
        Path audioPath = Paths.get(UPLOAD_DIR + "/songs/" + audioFileName);
        Files.createDirectories(audioPath.getParent());
        Files.write(audioPath, file.getBytes());
        String songUrl = getBaseUrl() + "/songs/" + audioFileName;

        // ----------------- Save image -----------------
        String imageUrl;
        if (image != null && !image.isEmpty()) {
            String imageFileName = System.currentTimeMillis() + "_" +
                    image.getOriginalFilename().replaceAll("[^a-zA-Z0-9._-]", "_");
            Path imagePath = Paths.get(UPLOAD_DIR + "/images/" + imageFileName);
            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath, image.getBytes());
            imageUrl = getBaseUrl() + "/images/" + imageFileName;
        } else {
            // Fallback default image
            imageUrl = getBaseUrl() + "/images/default.png";
        }

        // ----------------- Save to DB -----------------
        Song song = new Song();
        song.setName(name);
        song.setDescription(description);
        song.setSongUrl(songUrl);
        song.setImageUrl(imageUrl);

        return songRepository.save(song);
    }

    @GetMapping("/songs")
    public List<Song> getSongs() {
        List<Song> songs = songRepository.findAll();
        for (Song s : songs) {
            if (s.getImageUrl() == null || s.getImageUrl().isEmpty()) {
                s.setImageUrl(getBaseUrl() + "/images/default.png");
            }
        }
        return songs;
    }

    // ----------------- Helper -----------------
    private String getBaseUrl() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    }
}
