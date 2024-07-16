package com.example.admin.repository;

import com.example.admin.model.EBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface EBookRepo extends JpaRepository<EBook,Long> {
    List<EBook> findByIsFinished(boolean isFinished);
}
