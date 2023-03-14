package com.example.Student_Library_Management_System.Services;

import com.example.Student_Library_Management_System.DTO.IssueBookRequestDto;

import com.example.Student_Library_Management_System.Enums.CardStatus;
import com.example.Student_Library_Management_System.Enums.TransactionStatus;
import com.example.Student_Library_Management_System.Models.Book;
import com.example.Student_Library_Management_System.Models.Card;
import com.example.Student_Library_Management_System.Models.Transactions;
import com.example.Student_Library_Management_System.Repositories.BookRepository;
import com.example.Student_Library_Management_System.Repositories.CardRepository;
import com.example.Student_Library_Management_System.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CardRepository cardRepository;

    public String issueBook(IssueBookRequestDto issueBookRequestDto)throws Exception{

        int bookId = issueBookRequestDto.getBookId();
        int cardId = issueBookRequestDto.getCardId();

        // Get the Book Entity and Card Entity
        // we are this bcz we want to set the Transaction attributes

        Book book = bookRepository.findById(bookId).get();

        Card card = cardRepository.findById(cardId).get();

        // Final goal is to make a transaction Entity,set it's attributes
        // And save it
        Transactions transaction=new Transactions();


        // Setting the attributes
        transaction.setBook(book);
        transaction.setCard(card);
        transaction.setIssueOperation(true);
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setTransactionStatus(TransactionStatus.PENDING);

        // Attribute left is success/Failure
        // Check
        if(book == null || book.isIssued()==true){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new Exception("Book is not available");
        }

        if(card == null || (card.getCardStatus()!= CardStatus.ACTIVATED)){

            transactionRepository.save(transaction);
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            throw new Exception("Card is not available");

        }

        //  We have reached a success case now.

        transaction.setTransactionStatus(TransactionStatus.SUCCESS);


        // Set the attributes of Book
        book.setIssued(true);
        List<Transactions> listOfTransactionForBook = book.getListOfTransactions();
        listOfTransactionForBook.add(transaction);


        // I need to make changes in the card
        // Book and the Card
        List<Book> issuedBooksForCard = card.getBooksIssued();
        issuedBooksForCard.add(book);
        card.setBooksIssued(issuedBooksForCard);

        // Card and the Transaction : bidirectional (parent class
        List<Transactions> transactionListForCard = card.getTransactionsList();
        transactionListForCard.add(transaction);
        card.setTransactionsList(transactionListForCard);



        // save Parent
        cardRepository.save(card);
        // Automatically by cascading : book and transactions will be saved by saving the parent

        return "Book issued successfully";

    }
    public String getTransactions(int bookId,int cardId){
        List<Transactions> transactionsList = transactionRepository.getTransactionsforBookAndCard(bookId,cardId);

        String transactionId = transactionsList.get(0).getTransactionId();

        return transactionId;

    }
}
