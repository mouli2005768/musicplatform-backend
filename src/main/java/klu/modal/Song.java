package klu.modal;

import jakarta.persistence.*;

@Entity
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String songUrl;  // 🎵 audio link (e.g., MP3, YouTube, Spotify)
    private String imageUrl; // 🎨 song thumbnail/cover

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;
    
   
    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSongUrl() { return songUrl; }
    public void setSongUrl(String songUrl) { this.songUrl = songUrl; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Album getAlbum() { return album; }
    public void setAlbum(Album album) { this.album = album; }
}
