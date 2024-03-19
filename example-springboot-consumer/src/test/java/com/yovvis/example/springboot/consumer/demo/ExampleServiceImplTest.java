package com.yovvis.example.springboot.consumer.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExampleServiceImplTest {
    @Resource
    private ExampleServiceImpl exampleService;

    @Test
    void testUser() {
        exampleService.testUser();
    }
}