package klu.repository;

import klu.modal.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    // ✅ 1. Fetch all songs with a given description (i.e., songs in one playlist)
    List<Song> findByDescription(String description);

    // ✅ 2. Get all distinct descriptions (each unique description = one playlist)
    @Query("SELECT DISTINCT s.description FROM Song s WHERE s.description IS NOT NULL AND s.description <> ''")
    List<String> findDistinctDescriptions();
}
