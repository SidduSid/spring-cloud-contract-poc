package com.poc.spring.cloud.contract.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @AllArgsConstructor @ToString
public class Student {

   private int id;
   private String name;
   private int age;
   private String status;
}
