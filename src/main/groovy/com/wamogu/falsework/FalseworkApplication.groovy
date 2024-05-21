package com.wamogu.falsework

import com.feiniaojin.gracefulresponse.EnableGracefulResponse
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@EnableGracefulResponse
@SpringBootApplication
class FalseworkApplication {

	static void main(String[] args) {
		SpringApplication.run(FalseworkApplication, args)
	}

}
