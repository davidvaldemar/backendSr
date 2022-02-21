package com.cuscatlan.backendsr.lib.dto.creators.api;

import com.cuscatlan.backendsr.lib.dto.creators.MarvelCreatorsResponse;
import com.cuscatlan.backendsr.lib.util.dto.Result;

import lombok.Data;

@Data
public class CreatorComicsListResponse {

	private Result result;
	
	private MarvelCreatorsResponse data;
}
