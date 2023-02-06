package com.ddt.finalproject.controller;

import com.ddt.finalproject.dto.UserDto;
import com.ddt.finalproject.dto.user.UserAddRequest;
import com.ddt.finalproject.service.UserService;

import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@ApiOperation("取得所有會員資料")
	@GetMapping("/user")
	public List<UserDto> getUserDataList() {
		List<UserDto> userDtoList = userService.getUserDataList();
		return userDtoList;
	}

	
	// 取得單筆user資料(by id)
	@ApiOperation("取得單筆會員資料(userID)")
	@GetMapping("/user/{userID}")
	public UserDto getUserData(@PathVariable Long userID) {
		UserDto userDto =  userService.getUserData(userID);
		return userDto;
	}

	@ApiOperation("新增單筆會員資料")
	@PostMapping("/user")
	public void addUserData(@RequestBody UserAddRequest userAddRequest) {
		userService.addUserData(userAddRequest);
	}



}
