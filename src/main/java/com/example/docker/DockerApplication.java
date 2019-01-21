package com.example.docker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

@SpringBootApplication
@Controller
public class DockerApplication {
	@Autowired
	private JavaMailSender sender;

	public static void main(String[] args) {
		SpringApplication.run(DockerApplication.class, args);
	}

	@GetMapping
	public String getStartPage() {
		return "index";
	}

	@PostMapping("/emails")
	public ResponseEntity<?> sendEmail(@RequestBody @Valid MailDto email) throws Exception{
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(email.getFrom());
		helper.setTo(email.getTo());
		helper.setText(email.getMessage());
		helper.setSubject(email.getSubject());
		sender.send(message);
		return ResponseEntity.ok().build();
	}

}

