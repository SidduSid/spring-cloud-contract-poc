package com.poc.spring.cloud.contract.repository;

import com.poc.spring.cloud.contract.model.Student;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentRepository {

   private static final  String FOUND = "FOUND";
   static List<Student> studentList = new ArrayList<>();
   static {
      studentList.add(new Student(1, "ABI", 27, FOUND));
      studentList.add(new Student(2, "AJAY", 28, FOUND));
      studentList.add(new Student(3, "AKASH", 22, FOUND));
      studentList.add(new Student(4, "ANU", 55, FOUND));
      studentList.add(new Student(5, "APPU", 19, FOUND));
   }

   public Student getStudentDetails(int id){
     return studentList.stream()
            .filter(stud -> stud.getId() == id)
            .findFirst().orElse(new Student(0, "", 0, "NOT_FOUND"));
   }

}
