package com.ddt.finalproject.repository;

import com.ddt.finalproject.entity.UserEntity;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
     Boolean existsByCarrierID(String carrierID);

     UserEntity findByCarrierID(String carrierID);
}


