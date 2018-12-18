package com.cnpiec.ireader;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cnpiec.ireader.dao")
public class IReader1Application {

	public static void main(String[] args) {
		SpringApplication.run(IReader1Application.class, args);
	}
}
