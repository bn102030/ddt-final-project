package com.ddt.finalproject.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DetailResponseHandler {
	public static ResponseEntity<Object> responseBuiler(String receipt, HttpStatus returnCode, Object responseObject){
		Map<String, Object> response = new HashMap<>();
		response.put("receipt", receipt);
		response.put("returnCode", returnCode);
		response.put("orderDetail", responseObject);
		
		return new ResponseEntity<> (response, returnCode);
	}
}
