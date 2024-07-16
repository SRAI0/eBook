package com.example.author.controller;

import com.example.admin.model.Author;
import com.example.admin.service.AdminService;
import com.example.author.model.AuthorDetails;
import com.example.author.model.EBookDetails;
import com.example.author.service.AuthorService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@RestController
@RequestMapping("/authorData")
public class AuthorController {
    @Autowired
    private AuthorService authorService;
    @GetMapping()
    public List<AuthorDetails> getAuthor(){
        return authorService.getAuthor();
    }

    @GetMapping("/{id}")
    public AuthorDetails getAuthorByID(@PathVariable Long id){
        return authorService.getAuthorByID(id);
    }
    @PostMapping("/saveAuthor")
    public AuthorDetails saveAuthor( @RequestBody AuthorDetails AuthorDetails){
        return authorService.saveAuthor(AuthorDetails);
    }

    @DeleteMapping("/{id}")
    public  void deleteAuthor(Long id){
        authorService.deleteAuthor(id);
    }
    @PutMapping("/ebooks/{ebookId}/context")
    public ResponseEntity<EBookDetails> updateEBookContext(@PathVariable Long id , @RequestBody String context)  {
        try {
            EBookDetails updatedBook = authorService.updateEBookContext(id, context);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/e")
    public ResponseEntity<?> createOrResume(@RequestParam Long authorId,@RequestBody EBookDetails eBookDetails)
    {
    try{
        EBookDetails createOrResume = authorService.createOrResume(authorId,eBookDetails);
       return new ResponseEntity<>(createOrResume, HttpStatus.OK);
       // return ResponseEntity.ok("Author request accepts successfully");
    }
    catch (Exception e)
    {
        e.printStackTrace();
        return  new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
    }

    @PutMapping("EBookDetails/{ebookId}/pause")
    public ResponseEntity<EBookDetails> pauseBook(@PathVariable Long eBookId) {
        try{
            EBookDetails pause = authorService.pauseBook(eBookId);
            return new ResponseEntity<>(pause, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return  new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("eBookDetails/{ebookId}/resume")
    public ResponseEntity<EBookDetails> resumeBook(@PathVariable Long eBookId) {
        try{
            EBookDetails resume = authorService.pauseBook(eBookId);
            return new ResponseEntity<>(resume, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return  new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }
@GetMapping("/eBookDetails/{eBookDetailsID}/format ")
    public ResponseEntity<Resource> createPdf(@PathVariable Long eBookDetailsID, @RequestParam String format) {
        try{
            File export = authorService.createPdf(eBookDetailsID,format);
            InputStreamResource inputStreamResource =new InputStreamResource(new FileInputStream(export));

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename ="+export.getName());
            return ResponseEntity.ok().headers(headers).contentLength(export.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM).body((Resource) inputStreamResource);
        }
        catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }
    }


}


