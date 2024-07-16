package com.example.author.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class EBookDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true ,nullable = false)
    private Long id;
    private String title;
    private String content;
    private boolean isPaused;
    private boolean isFinished;

    private String context;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name ="author_id", nullable = false)
    private AuthorDetails authorDetails;

}
