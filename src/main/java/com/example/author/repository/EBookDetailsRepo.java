package com.example.author.repository;

import com.example.author.model.AuthorDetails;
import com.example.author.model.EBookDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface EBookDetailsRepo extends JpaRepository<EBookDetails,Long> {
    List<EBookDetails> findByAuthorDetailsAndIsFinished(AuthorDetails authorDetails, boolean isFinished);

    //  List<EBookDetails> authorAndIsFinished(Long authorId, boolean b);
}
