package com.shisj.study.studyspringboothello;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class StudySpringbootHelloApplication {

	public static void main(String[] args) {

		SpringApplication.run(StudySpringbootHelloApplication.class, args);
	}

	@Bean
	public Person person(){
		Person ret = new Person();
		ret.setAge(25);
		ret.setName("Shengjie Shi");
		return ret;
	}
}
