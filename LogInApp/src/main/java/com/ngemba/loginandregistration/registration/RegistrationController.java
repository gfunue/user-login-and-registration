package com.ngemba.loginandregistration.registration;

import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController{
	
	private RegistrationService registrationService;
	
	 @PostMapping
	public String registration(@RequestBody RegistrationRequest request) {
		return registrationService.register(request);
	}
	
//	 @GetMapping(path = "confirm")
//	    public String confirm(@RequestParam("token") String token) {
//	        return registrationService.confirmToken(token);
//	    }

}
