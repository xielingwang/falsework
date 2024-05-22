package com.wamogu.falsework;

import com.feiniaojin.gracefulresponse.EnableGracefulResponse;
import com.tangzc.autotable.springboot.EnableAutoTable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableGracefulResponse
@EnableAutoTable
@SpringBootApplication
public class FSMgrApplication {

	public static void main(String[] args) {
		SpringApplication.run(FSMgrApplication.class, args);
	}

}
