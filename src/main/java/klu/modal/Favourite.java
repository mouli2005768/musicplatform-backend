package klu.modal;

import jakarta.persistence.*;

@Entity
@Table(name = "favourite")
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Each favourite belongs to one user
    @ManyToOne
    @JoinColumn(name = "user_emailid", referencedColumnName = "emailid")
    private User user;

    // ✅ Each favourite points to one song
    @ManyToOne
    @JoinColumn(name = "song_id", referencedColumnName = "id")
    private Song song;

    public Favourite() {}

    public Favourite(User user, Song song) {
        this.user = user;
        this.song = song;
    }

    // --- Getters & Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}
