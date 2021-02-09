package com.freshvotes.service;


import static org.hamcrest.CoreMatchers.not;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UserDetailServiceTest {

	@Test
	public void generate_encrypted_password() {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "adiel123";
		String encodedPassword = encoder.encode(rawPassword);
		System.out.println(encodedPassword);
		assertThat(rawPassword,not( encodedPassword));
	}

}
