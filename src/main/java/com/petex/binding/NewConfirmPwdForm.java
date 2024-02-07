package com.petex.binding;

import lombok.Data;

@Data
public class NewConfirmPwdForm {

	private String pwd;
	private String confirmPwd;
	private String email;
}
