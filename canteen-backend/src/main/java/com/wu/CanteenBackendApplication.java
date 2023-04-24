package com.wu;

import com.wu.entity.User;
import com.wu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SpringBootApplication
public class CanteenBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CanteenBackendApplication.class, args);
    }

}
