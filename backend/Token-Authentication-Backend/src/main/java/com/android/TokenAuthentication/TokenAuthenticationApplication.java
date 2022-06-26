package com.android.TokenAuthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

@Controller
@SpringBootApplication
public class TokenAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(TokenAuthenticationApplication.class, args);
	}
}
