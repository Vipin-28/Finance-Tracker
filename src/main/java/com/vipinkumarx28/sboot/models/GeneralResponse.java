package com.vipinkumarx28.sboot.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Data
public class GeneralResponse {
	Object data;
	public GeneralResponse(Object obj) {
		super();
		this.data = data;
	}
	
}
