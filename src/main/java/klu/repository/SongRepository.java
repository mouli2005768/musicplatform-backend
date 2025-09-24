package klu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import klu.modal.Song;

public interface SongRepository extends JpaRepository<Song, Long> {
}
