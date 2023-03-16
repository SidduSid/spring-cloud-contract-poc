package com.poc.spring.cloud.contract.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class StudentController {

 private  final RestTemplate restTemplate;

   public StudentController() {
      this.restTemplate = new RestTemplateBuilder().build();
   }
  @Value("${producer.url}")
  private String producerUrl;
  @GetMapping(path = "/consumer/student-details/{id}", produces = "application/json")
  public String getStudentDetails(@PathVariable(value = "id") int id){
    return   restTemplate.getForObject(
        producerUrl + "/producer/student-details/{id}", String.class, id);
  }
}
