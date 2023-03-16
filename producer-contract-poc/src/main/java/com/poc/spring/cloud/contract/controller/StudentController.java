package com.poc.spring.cloud.contract.controller;

import com.poc.spring.cloud.contract.model.Student;
import com.poc.spring.cloud.contract.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudentController {

   private final StudentRepository studentRepository;

   @GetMapping("/producer/student-details/{id}")
   public ResponseEntity<Student> getStudentDetails(@PathVariable Integer id) {
      Student student = studentRepository.getStudentDetails(id);
      return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(student);
   }
}
