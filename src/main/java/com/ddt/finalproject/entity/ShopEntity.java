package com.ddt.finalproject.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "shop")
public class ShopEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shop_id")
	private Long shopId;

	@Column(name = "shop_name")
	private String shopName;

	@Column(name = "tax")
	private String tax;

	@Column(name = "shop_address")
	private String address;
}