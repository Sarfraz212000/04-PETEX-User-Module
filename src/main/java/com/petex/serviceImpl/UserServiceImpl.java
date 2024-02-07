package com.petex.serviceImpl;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petex.binding.LoginForm;
import com.petex.binding.NewConfirmPwdForm;
import com.petex.binding.OtpForm;
import com.petex.binding.UserSingUpForm;
import com.petex.entity.UserEntity;
import com.petex.repository.UserRepository;
import com.petex.service.UserService;
import com.petex.utils.EmailUtils;
import com.petex.utils.PasswordUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repo;

	@Autowired
	private EmailUtils emailUtils;

	public String singUpUser(UserSingUpForm form) {
		if (form.getPwd().equals(form.getConfirmPwd())) {
			if (!repo.existsByEmail(form.getEmail())) {
				UserEntity entity = new UserEntity();
				BeanUtils.copyProperties(form, entity);
				repo.save(entity);
				return "Signup successful";
			} else {
				return "Email already exists. Please use a different email.";
			}
		}
		return "Invalid password and confirmation password";
	}

	@Override
	public String login(LoginForm form) {
		UserEntity findByEmailAndPaw = repo.findByEmailAndPwd(form.getEmail(), form.getPwd());
		if (findByEmailAndPaw != null) {
			return "login success";

		}
		return "check pwd and email";
	}

	@Override
	public Boolean forgetPwd(String email) {
		UserEntity entity = repo.findByEmail(email);
		if (entity == null) {
			return false;
		}
		String toEmail = entity.getEmail();
		String otp = PasswordUtil.genrateRandomPassword();
		entity.setOtp(otp);
		repo.save(entity);
		String subject = "New Optp";
		StringBuffer body = new StringBuffer("");
		body.append("take this temprory otp::" + otp);
		emailUtils.sendEmail(toEmail, subject, body.toString());
		return true;
	}

	@Override
	public String validOtp(OtpForm form) {
		UserEntity findByEmailAndOtp = repo.findByEmailAndOtp(form.getEmail(), form.getOtp());
		if (findByEmailAndOtp != null) {
			return "otp confirmed";
		}
		return "otp and email mismatch";
	}

	@Override
	public String confirmPassword(NewConfirmPwdForm form) {
		UserEntity entity = repo.findByEmail(form.getEmail());
		if (entity != null) {
			if (form.getPwd().equals(form.getConfirmPwd())) {

				entity.setPwd(form.getPwd());
				entity.setConfirmPwd(form.getConfirmPwd());
				repo.save(entity);
				return "Your password has been successfully updated";
			} else {
				return "Password and confirmation password do not match";
			}
		}
		return "Failed to update password. Please check your current password and try again.";
	}

	@Override
	public Boolean updateUser(Long userId, UserEntity entity) {
		Optional<UserEntity> optionalEntity = repo.findById(userId);
		if (optionalEntity.isPresent()) {
			UserEntity userEntity = optionalEntity.get();
			BeanUtils.copyProperties(entity, userEntity);
			userEntity.setUserId(userId);
			repo.save(userEntity);
			return true;
		}
	
		return false;
	}

	@Override
	public Boolean deleteUser(Long userId) {
		repo.deleteById(userId);
		return true;
	}

}
