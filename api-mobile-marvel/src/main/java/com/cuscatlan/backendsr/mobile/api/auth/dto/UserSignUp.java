package com.cuscatlan.backendsr.mobile.api.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserSignUp {

	 @ApiModelProperty(notes = "Nombre de usuario a crear", example= "usuarioAdmin")
	private String username;
	 @ApiModelProperty(notes = "Clave de usuario a crear", example= "cuscatlan2022$")
	private String password;
}
