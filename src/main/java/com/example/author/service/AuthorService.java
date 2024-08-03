package com.example.author.service;

import com.example.admin.model.Author;
import com.example.author.model.AuthorDetails;
import com.example.author.model.EBookDetails;
import com.example.author.repository.AuthorDetailsRepository;
import com.example.author.repository.EBookDetailsRepo;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    private AuthorDetailsRepository authorDetailsRepository;
    @Autowired
    private EBookDetailsRepo eBookDetailsRepo;

    public List<AuthorDetails> getAuthor(){
        return authorDetailsRepository.findAll();
    }

    public AuthorDetails getAuthorByID(Long authorId){
        return authorDetailsRepository.findById(authorId).orElse(null);
    }

    public AuthorDetails saveAuthor(AuthorDetails authorDetails){
        return authorDetailsRepository.save(authorDetails);
    }
    public  void deleteAuthor(Long id){
        authorDetailsRepository.deleteById(id);
    }

    public EBookDetails updateEBookContext(Long eBookId ,String context ) throws Exception
    {
        EBookDetails eBookDetails = eBookDetailsRepo.findById(eBookId).orElse(null);
        if(eBookDetails == null)
        {
            throw new Exception("EBook not found");
        }
        eBookDetails.setContext(context);
        return eBookDetailsRepo.save(eBookDetails);


    }
    public EBookDetails createOrResume(Long authorId, EBookDetails eBookDetails) throws Exception
    {
        AuthorDetails authorDetails = getAuthorByID(authorId);
        if(authorDetails == null){
            throw new Exception("Author not found");
        }
        List<EBookDetails> incompleteBooks = eBookDetailsRepo.findByAuthorDetailsAndIsFinished(authorDetails,false);
        if(incompleteBooks.size() >=3){
            throw new Exception("One Author cannot keep more than 3 incomplete Books," +
                    "Please complete 1 book atleast to start new one");
        }

         Optional<EBookDetails> eBookNames =  incompleteBooks.stream().filter(b->b.getTitle().equalsIgnoreCase(eBookDetails.getTitle())).findFirst();

        //RESUME
        if (eBookNames.isPresent())
        {
            EBookDetails eBook = eBookNames.get();
            eBook.setContent(eBookDetails.getContent());  //Resume
            return eBookDetailsRepo.save(eBook);

        }
        else {
            //CREATE
            eBookDetails.setAuthorDetails(authorDetails);
            //System.out.println(eBookDetails);
            return eBookDetailsRepo.save(eBookDetails);

        }
    }
    public EBookDetails pauseBook(Long eBookId) throws Exception{

        EBookDetails eBookDetails = eBookDetailsRepo.findById(eBookId).orElse(null);
        if(eBookDetails == null)
        {
            throw new Exception("EBook not found");
        }
        eBookDetails.setPaused(true);
       return eBookDetailsRepo.save(eBookDetails);

    }

    public EBookDetails resume(Long eBookDetailsID) throws Exception{
        EBookDetails eBookDetails = eBookDetailsRepo.findById(eBookDetailsID).orElse(null);
        if(eBookDetails == null)
        {
            throw new Exception("EBook not found");
        }
        eBookDetails.setPaused(false);
        return eBookDetailsRepo.save(eBookDetails);
    }

    public File createPdf(Long eBookDetailsID, String format) throws Exception{
        EBookDetails eBookDetails = eBookDetailsRepo.findById(eBookDetailsID).orElse(null);
        if(eBookDetails == null)
        {
            throw new Exception("EBook not found");
        }
        if(format.equalsIgnoreCase("pdf")){
            return convertToPDF(eBookDetails);
        }
        else
        {
            throw new Exception("Unsupported Format");
        }
    }

    private File convertToPDF(EBookDetails eBookDetails) throws IOException, DocumentException {
        File file = new File(eBookDetails.getTitle() +".pdf");
        Document document =new Document();
   //   try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {

          PdfWriter writer =  PdfWriter.getInstance(document,new FileOutputStream(file));
         // PdfDocument doc = new PdfDocument(writer);
        document.open();

        document.add(new Paragraph("Title:"+ eBookDetails.getTitle()));
        document.add(new Paragraph("Content:"+ eBookDetails.getContent()));

        document.close();
        writer.close();
        return file;
    }
}





