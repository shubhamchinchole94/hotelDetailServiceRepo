package com.hotelConfigService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "*")
public class HotelConfigServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelConfigServiceApplication.class, args);
	}

}
