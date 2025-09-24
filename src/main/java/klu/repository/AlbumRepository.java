package klu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import klu.modal.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
