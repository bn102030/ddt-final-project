package com.ddt.finalproject.service;

import java.util.List;

import com.ddt.finalproject.dto.UserDto;
import com.ddt.finalproject.dto.user.UserAddRequest;

public interface UserService {

	List<UserDto> getUserDataList();
	UserDto getUserData(Long userID);
	void addUserData(UserAddRequest userAddRequest);

	Long getUserIdByCarrierId(String carrierID);

}
