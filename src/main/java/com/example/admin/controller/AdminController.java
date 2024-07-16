package com.example.admin.controller;

import com.example.admin.model.Author;
import com.example.admin.model.EBook;
import com.example.admin.service.AdminService;
import org.hibernate.annotations.processing.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping()
    public List<Author> getAuthor(){
        return adminService.getAuthor();
    }

    @GetMapping("/{id}")
    public  ResponseEntity<?> getAuthorByID(@PathVariable Long id){

        Author authorById = adminService.getAuthorByID(id);
        return new ResponseEntity<>(authorById, HttpStatus.CREATED);

    }

    @PostMapping("/acceptsRequest/{authorId}")
    public ResponseEntity<?> requestByAuthor(@PathVariable @Pattern Long authorId) {

        boolean requestAccepted = adminService.requestByAuthor(authorId);

        if(requestAccepted){
            return ResponseEntity.ok("Author request accepts successfully");
        }
        else{
            return ResponseEntity.badRequest().
                    body("Author may not exists or request already accepted");

            }
        }

    @PostMapping("/saveAuthor")
    public Author saveAuthor( @RequestBody Author author){
        return adminService.saveAuthor(author);
    }

    @DeleteMapping("/{id}")
    public  void deleteAuthor(Long id){
        adminService.deleteAuthor(id);
    }

    @GetMapping("/finished")
    public ResponseEntity<List<EBook> >getFinishedBooks(){
        List<EBook> finished = adminService.getFinishedBooks();
        return
                ResponseEntity.ok(finished);
    }
//will implement in author section
    @PostMapping("/createBook")
    public EBook createBook(EBook ebook){
        return adminService.createBook(ebook);
    }

    @GetMapping("/check")
    public String apiHealth(){
        return "Works fine";
    }

}
