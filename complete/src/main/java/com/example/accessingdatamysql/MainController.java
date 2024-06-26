package com.example.accessingdatamysql;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.trace.Span;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

@Controller
@RequestMapping(path="/demo")
public class MainController {
	@Autowired
	private UserRepository userRepository;

	private final Random random = new Random(0);
	private final Logger logger = LoggerFactory.getLogger(MainController.class);

	@PostMapping(path="/add")
	public @ResponseBody ResponseEntity<String> addNewUser (@RequestParam String name
			, @RequestParam String email) {

		// additional email validation:
		if(!email.contains("@")) {
			logger.error("Invalid email address");
			Span.current().setAttribute(AttributeKey.booleanKey("email.validation.error"), true);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email validation error\n");
		}


		User n = new User();
		n.setName(name);
		n.setEmail(email);
		userRepository.save(n);
		logger.info("User saved");
		return ResponseEntity.status(HttpStatus.OK).body("Saved\n");
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		if (random.nextInt(10) < 3) {
			throw new RuntimeException("simulating an error");
		}
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}
}
