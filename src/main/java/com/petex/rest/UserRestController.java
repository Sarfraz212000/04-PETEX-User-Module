package com.petex.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petex.binding.LoginForm;
import com.petex.binding.NewConfirmPwdForm;
import com.petex.binding.OtpForm;
import com.petex.binding.UserSingUpForm;
import com.petex.entity.UserEntity;
import com.petex.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

	@Autowired
	private UserService services;

	@PostMapping("/signup")
	public ResponseEntity<String> saveUser(@RequestBody UserSingUpForm form) {
		String singUpUser = services.singUpUser(form);
		return new ResponseEntity<>(singUpUser, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody LoginForm form) {
		String login = services.login(form);
		if (login.equals("login success")) {
			return new ResponseEntity<>(login, HttpStatus.OK);
		}
		return new ResponseEntity<>(login, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/forget/{email}")
	public ResponseEntity<String> GerateUserOtp(@PathVariable String email) {
		Boolean status = services.forgetPwd(email);
		if (status) {
			return new ResponseEntity<String>("An otp has been sent to your email", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<String>("Email not found,Give Exist Email", HttpStatus.NOT_FOUND);
	}

	@PostMapping("/validotp")
	public ResponseEntity<String> validOtp(@RequestBody OtpForm form) {
		String validOtp = services.validOtp(form);
		return new ResponseEntity<String>(validOtp, HttpStatus.OK);
	}

	@PostMapping("/confirmpwd")
	public ResponseEntity<String> confirmPassword(@RequestBody NewConfirmPwdForm form) {
		String confirmPassword = services.confirmPassword(form);
		return new ResponseEntity<String>(confirmPassword, HttpStatus.OK);

	}

	@PutMapping("/update/{userId}")
	public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody UserEntity entity) {
		Boolean status = services.updateUser(userId, entity);
		if (status) {
			return new ResponseEntity<String>("Update successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("update not succesfull", HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
		Boolean deleteUser = services.deleteUser(userId);
		if (deleteUser) {
			return new ResponseEntity<String>("delete successfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("delete not happend successfully", HttpStatus.NOT_FOUND);
	}

}
