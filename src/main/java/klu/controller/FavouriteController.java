package klu.controller;

import klu.modal.Favourite;
import klu.modal.User;
import klu.modal.Song;
import klu.repository.FavouriteRepository;
import klu.repository.UserRepository;
import klu.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favourites")
@CrossOrigin(origins = "*")
public class FavouriteController {

    @Autowired
    private FavouriteRepository favouriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SongRepository songRepository;

    // ✅ Add Favourite
    @PostMapping("/add")
    public ResponseEntity<?> addFavourite(@RequestParam String email, @RequestParam Long songId) {
        try {
            User user = userRepository.findById(email)
                    .orElseThrow(() -> new RuntimeException("User not found: " + email));

            Song song = songRepository.findById(songId)
                    .orElseThrow(() -> new RuntimeException("Song not found: " + songId));

            // ✅ Check if it already exists before saving
            if (favouriteRepository.existsByUserAndSong(user, song)) {
                return ResponseEntity.ok("Already added to favourites ❤️");
            }

            Favourite fav = new Favourite(user, song);
            favouriteRepository.save(fav);
            return ResponseEntity.ok("Favourite added successfully ✅");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding favourite: " + e.getMessage());
        }
    }



    // ✅ Remove Favourite
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFavourite(@RequestParam String email, @RequestParam Long songId) {
        try {
            // ✅ Fetch the user
            User user = userRepository.findById(email)
                    .orElseThrow(() -> new RuntimeException("User not found: " + email));

            // ✅ Fetch the song
            Song song = songRepository.findById(songId)
                    .orElseThrow(() -> new RuntimeException("Song not found: " + songId));

            // ✅ Find all favourites that match
            List<Favourite> favourites = favouriteRepository.findByUserAndSong(user, song);

            if (favourites.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Favourite not found for songId: " + songId);
            }

            // ✅ Delete them safely
            favouriteRepository.deleteAll(favourites);

            return ResponseEntity.ok("Removed from favourites successfully 💔");
        } catch (Exception e) {
            e.printStackTrace(); // shows full error in console
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error removing favourite: " + e.getMessage());
        }
    }


    // ✅ Get All Favourites for User (returns Favourite objects)
    @GetMapping("/{email}")
    public ResponseEntity<?> getUserFavourites(@PathVariable String email) {
        try {
            User user = userRepository.findById(email)
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

            List<Favourite> favourites = favouriteRepository.findByUser(user);
            return ResponseEntity.ok(favourites);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching favourites: " + e.getMessage());
        }
    }

    // ✅ (Optional) Directly return Song objects instead of Favourite
    @GetMapping("/{email}/songs")
    public ResponseEntity<?> getUserFavouriteSongs(@PathVariable String email) {
        try {
            User user = userRepository.findById(email)
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

            List<Favourite> favourites = favouriteRepository.findByUser(user);
            List<Song> songs = favourites.stream().map(Favourite::getSong).toList();

            return ResponseEntity.ok(songs);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching user favourite songs: " + e.getMessage());
        }
    }
}
