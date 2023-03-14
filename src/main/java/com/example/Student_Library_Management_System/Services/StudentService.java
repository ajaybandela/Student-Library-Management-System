package com.example.Student_Library_Management_System.Services;

import com.example.Student_Library_Management_System.DTO.StudentUpdateMobRequestDto;
import com.example.Student_Library_Management_System.Enums.CardStatus;
import com.example.Student_Library_Management_System.Models.Card;
import com.example.Student_Library_Management_System.Models.Student;
import com.example.Student_Library_Management_System.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {


    @Autowired
    StudentRepository studentRepository;


    public String createStudent(Student student){


        // Card should be generated when createStudent function is called

        Card card=new Card();
        card.setCardStatus(CardStatus.ACTIVATED);
        card.setStudentVariableName(student);  // Foreign key attributed

        student.setCard(card);

        // If there was a unidirectional Mapping : We have to save both of them

        // But we are super advance and are use bidirectional  : Child will automatically be saved

        studentRepository.save(student);
        // By cascading affect , Child will automatically be saved


        return "Student and Card added";
    }

    public String findNameByEmail(String email){
        Student student=studentRepository.findByEmail(email);
        return student.getName();
    }

    public String updateMobNo(StudentUpdateMobRequestDto studentReq){

        // Convert Dto to Entity : saved better



        // First we will try to fetch Original Data
        Student originalStudent=studentRepository.findById(studentReq.getId()).get();

        // we will keep Other Properties as it is : and only change the required parameter

        originalStudent.setMobNo(studentReq.getMobNo());

        studentRepository.save(originalStudent);

        return "student has been updated successfully. ";

    }
}
