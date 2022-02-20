package com.cuscatlan.backendsr.lib.util.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result {
	@ApiModelProperty(notes = "Codigo Status HTTP resultado de la transacción")
	private int code;
	@ApiModelProperty(notes = "Descripción de resultado de transacción")
	private String descripcion;
	@ApiModelProperty(notes = "Id de transacción")
	private String uuid;	

}
