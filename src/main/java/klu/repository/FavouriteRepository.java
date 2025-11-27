package klu.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import klu.modal.Favourite;
import klu.modal.User;
import klu.modal.Song;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    List<Favourite> findByUser(User user);
    boolean existsByUserAndSong(User user, Song song);
    List<Favourite> findByUserAndSong(User user, Song song);
    void deleteByUserAndSong(User user, Song song);
}
