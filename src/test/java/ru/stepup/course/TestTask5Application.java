package ru.stepup.course;

import org.springframework.boot.SpringApplication;

public class TestTask5Application {

	public static void main(String[] args) {
		SpringApplication.from(Task5Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
