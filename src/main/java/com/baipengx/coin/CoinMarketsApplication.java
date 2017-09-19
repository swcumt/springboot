package com.baipengx.coin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.baipengx.coin.dao")
@SpringBootApplication
@EnableScheduling
@ServletComponentScan
public class CoinMarketsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoinMarketsApplication.class, args);
	}
}
