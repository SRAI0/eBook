package com.example.author.repository;

import com.example.admin.model.Author;
import com.example.author.model.AuthorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface AuthorDetailsRepository extends JpaRepository<AuthorDetails,Long> {

}
