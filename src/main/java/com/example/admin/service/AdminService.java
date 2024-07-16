package com.example.admin.service;

import com.example.admin.model.Author;
import com.example.admin.model.EBook;
import com.example.admin.repository.AuthorRepo;
import com.example.admin.repository.EBookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AuthorRepo authorRepo;
    @Autowired
    private EBookRepo eBookRepo;

    public List<Author> getAuthor() {
        return authorRepo.findAll();
    }

    public Author getAuthorByID(Long id) {
        return authorRepo.findById(id).orElse(null);
    }

    public boolean requestByAuthor(Long authorId) {
        Optional<Author> authorCheck = authorRepo.findById(authorId);
        try {
            if (authorCheck.isPresent()) {
                Author author = authorCheck.get();
                if (!author.isRequestAccepted()) {
                    author.setRequestAccepted(true);
                    authorRepo.save(author);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public Author saveAuthor(Author author){
        return authorRepo.save(author);
    }


    public  void deleteAuthor(Long id){
         authorRepo.deleteById(id);
    }

    public List<EBook> getFinishedBooks(){
        return eBookRepo.findByIsFinished(true);
    }

    public EBook createBook(String title , List<Long> bookIds){

        return null; //eBookRepo.save(ebook);
    }

    public EBook createBook(EBook ebook) {
        return eBookRepo.save(ebook);
    }
}
