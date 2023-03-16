package com.poc.spring.cloud.contract.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//com.poc.spring.cloud.contract(Producer service groupId) && producer-contract-poc(Producer service artifactsId)
@AutoConfigureStubRunner(ids = {"com.poc.spring.cloud.contract:producer-contract-poc:+:stubs:8080"},
      stubsMode = StubRunnerProperties.StubsMode.LOCAL)
@AutoConfigureMockMvc
class ConsumerControllerTest {
   @Autowired
   private MockMvc mockMvc;
   @Test
   void shouldReturnStudentObject() throws Exception {
      this.mockMvc.perform(
                  get("/consumer/student-details/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("ABI")));
   }

   @Test
   void shouldReturnNotFoundValue_when_Invalid_StudentId_Passed() throws Exception {
      this.mockMvc.perform(
                  get("/consumer/student-details/100"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("NOT_FOUND")));
   }


}
