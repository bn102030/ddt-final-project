package com.ddt.finalproject.service.impl;

import com.ddt.finalproject.dto.UserDto;
import com.ddt.finalproject.dto.user.UserAddRequest;
import com.ddt.finalproject.entity.UserEntity;
import com.ddt.finalproject.repository.UserRepo;
import com.ddt.finalproject.service.UserService;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

	private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);


	@Autowired
	UserRepo userRepo;

	@Override
	public List<UserDto> getUserDataList() {
		List<UserEntity> userEntities = userRepo.findAll();
		List<UserDto> result = new ArrayList<>();
		for(UserEntity userEntity : userEntities) {
			UserDto userDto = new UserDto();
			userDto.setUserID(userEntity.getUserID());
			userDto.setUserName(userEntity.getUserName());
			userDto.setAccount(userEntity.getAccount());
			userDto.setCarrierID(userEntity.getCarrierID());
			result.add(userDto);
		}
		return result;
	}

	@Override
	public UserDto getUserData(Long userID) {
		Optional<UserEntity> userOpt =  userRepo.findById(userID);
		UserDto userDto = new UserDto();
		if(userOpt.isPresent()) {
			UserEntity userEntity = userOpt.get();
			userDto.setUserID(userEntity.getUserID());
			userDto.setUserName(userEntity.getUserName());
			userDto.setAccount(userEntity.getAccount());
			userDto.setCarrierID(userEntity.getCarrierID());
		}
		return userDto;
	}


//	User u = new User();
//u.setOpenId("xxx");
//u.setState(1);
//	Example<User> example = Example.of(u);
//	Optional<User> user = postRepository.findOne(example);

	@Override
	public void addUserData(UserAddRequest userAddRequest){

		Boolean UserCarrierID = userRepo.existsByCarrierID(userAddRequest.getCarrierID());
		if(UserCarrierID){
			log.warn("該載具 {} 已經被使用", userAddRequest.getCarrierID());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"該載具"+userAddRequest.getCarrierID()+"已經被使用");
		}else {
			log.info("該載具 {} 尚未被註冊，可以被新增", userAddRequest.getCarrierID());
			UserEntity userEntity = new UserEntity();
			userEntity.setUserName(userAddRequest.getUserName());
			userEntity.setAccount(userAddRequest.getAccount());
			userEntity.setCarrierID(userAddRequest.getCarrierID());
			UserEntity newUserEntity = userRepo.save(userEntity);
			log.info("該載具 {} 成功新增", userAddRequest.getCarrierID());
			throw new ResponseStatusException(HttpStatus.OK,"該會員註冊成功"+newUserEntity.getUserName());
		}




	}

	@Override
	public Long getUserIdByCarrierId(String carrierID) {
		UserEntity userEntity = userRepo.findByCarrierID(carrierID);
		return userEntity.getUserID();

	}


}
