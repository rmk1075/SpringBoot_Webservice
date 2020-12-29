package com.spring.webservice.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test_mainPage() {
        String body = this.restTemplate.getForObject("/", String.class);

        assertTrue(body.contains("스프링부트로 시작하는 웹 서비스"));
    }
}