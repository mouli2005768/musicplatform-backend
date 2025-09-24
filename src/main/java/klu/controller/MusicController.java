package klu.controller;

import klu.modal.Album;
import klu.modal.Song;
import klu.repository.AlbumRepository;
import klu.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class MusicController {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private AlbumRepository albumRepository;

    // ✅ Add Album
    @PostMapping("/albums")
    public Album addAlbum(@RequestBody Album album) {
        return albumRepository.save(album);
    }

    // ✅ Get Albums
    @GetMapping("/albums")
    public List<Album> getAlbums() {
        return albumRepository.findAll();
    }

    // ✅ Delete Album
    @DeleteMapping("/albums/{id}")
    public void deleteAlbum(@PathVariable Long id) {
        albumRepository.deleteById(id);
    }

    // ✅ Add Song (JSON way - keep it)
    @PostMapping("/songs")
    public Song addSong(@RequestBody Song song) {
        return songRepository.save(song);
    }

    // ✅ Delete Song
    @DeleteMapping("/songs/{id}")
    public void deleteSong(@PathVariable Long id) {
        songRepository.deleteById(id);
    }

    // ✅ Upload Song File
    @PostMapping("/songs/upload")
    public Song uploadSong(
            @RequestParam("file") MultipartFile file,
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String imageUrl
    ) throws IOException {

        // Save file inside target/classes/static/songs/
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get("target/classes/static/songs/" + fileName);
        Files.createDirectories(path.getParent()); // ensure folder exists
        Files.write(path, file.getBytes());

        // Generate public URL
        String songUrl = "http://localhost:8080/musicspring/songs/" + fileName;

        // Save song in DB
        Song song = new Song();
        song.setName(name);
        song.setDescription(description);
        song.setSongUrl(songUrl);
        song.setImageUrl(imageUrl);

        return songRepository.save(song);
    }

    // ✅ Get Songs
    @GetMapping("/songs")
    public List<Song> getSongs() {
        return songRepository.findAll();
    }
}
