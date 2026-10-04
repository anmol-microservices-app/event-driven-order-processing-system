package emailapi.EmailService.controller;

import emailapi.EmailService.entity.EmailEntity;
import emailapi.EmailService.service.EmailSrevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emailservice")
public class EmailController {

	@Autowired
	private EmailSrevice emailservice;

	@GetMapping("/email")
	public String Welcome() {
		return "welocome!!";
	}

	@PostMapping("/sendemail")
	public ResponseEntity<?> sendEmail(@RequestBody EmailEntity request) {
		System.out.println(request);
		boolean res = this.emailservice.sendEmail(request.getTo(), request.getSubject(), request.getMessage());
		if (res) {
			return ResponseEntity.ok("Email sent...!!");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email not sent..!");
		}
	}

}
