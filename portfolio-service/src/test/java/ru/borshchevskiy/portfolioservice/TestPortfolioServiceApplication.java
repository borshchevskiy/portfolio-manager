package ru.borshchevskiy.portfolioservice;

import org.springframework.boot.SpringApplication;

public class TestPortfolioServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(PortfolioServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
