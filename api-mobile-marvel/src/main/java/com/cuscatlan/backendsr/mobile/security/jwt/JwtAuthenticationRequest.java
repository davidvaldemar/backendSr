package com.cuscatlan.backendsr.mobile.security.jwt;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;


public class  JwtAuthenticationRequest implements Serializable {

    private static final long serialVersionUID = -8445943548965154778L;

    @ApiModelProperty(notes = "Usuario registrado", example= "usuario")
    private String username;
    
    @ApiModelProperty(notes = "Clave de usuario registrado", example= "mycl4v3")
    private String password;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
