package com.petex.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER_TAB")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String petType;
	private String petname;
	private String species;
	private Integer age;
	private String gender;
	private Double weight;
    private Double height;
    private String color;
    private String disease;
	private String vaccinated;
	private String fullname;
	private String email;
	private String pwd;
	private String confirmPwd;
	private Long phno;
	private String city;
	private String otp;

}
