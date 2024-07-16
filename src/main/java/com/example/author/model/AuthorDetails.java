package com.example.author.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class AuthorDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    private String password;
    @JsonManagedReference
    @OneToMany(mappedBy = "authorDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EBookDetails> eBookDetailsList;


}
