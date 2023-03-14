package com.example.Student_Library_Management_System.Services;

import com.example.Student_Library_Management_System.DTO.BookRequestDto;
import com.example.Student_Library_Management_System.Models.Author;
import com.example.Student_Library_Management_System.Models.Book;
import com.example.Student_Library_Management_System.Repositories.AuthorRepository;
import com.example.Student_Library_Management_System.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    AuthorRepository authorRepository;


    public String addBook(BookRequestDto bookRequestDto){

        // I want to get AuthorEntity ??

        int authorId=bookRequestDto.getAuthorId();

        // Now i will be fetching the authorEntity

        Author author=authorRepository.findById(authorId).get();


        // We have created this entity so that we can save it in DB
        Book book=new Book();

        // Basic attributes are being from Dto to entity layer
        book.setGenre(bookRequestDto.getGenre());
        book.setIssued(false);
        book.setName(bookRequestDto.getName());
        book.setPages(bookRequestDto.getPages());


        book.setAuthor(author);

        // We need to update the listOfBooks written in the parent class

        List<Book> currentBooksWritten = author.getBooksWritten();
        currentBooksWritten.add(book);
        author.setBooksWritten(currentBooksWritten);


        // The book is to be saved but author is also be saved.
        //Because the author entity has been updated...we need to reSave/update

        authorRepository.save(author);

        // .save function will work both save and update function


        // BookRepo.save is not required:bcz it will be autocalled by cascading effect

        return "Book Added Successfully";

    }
}
