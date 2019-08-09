
package com.vilma.filestore.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.multipart.MultipartFile;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void testPostFileObj() throws Exception {
        MultipartFile simpleFile = new MockMultipartFile("fileThatDoesNotExists..txt", "fileThatDoesNotExists..txt",
                "text/plain", "This is a dummy file content".getBytes(StandardCharsets.UTF_8));

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/file").file("file",simpleFile.getBytes()))
                            .andDo(MockMvcResultHandlers.print())
                            .andExpect(status().isOk());
                    
        
    }
}