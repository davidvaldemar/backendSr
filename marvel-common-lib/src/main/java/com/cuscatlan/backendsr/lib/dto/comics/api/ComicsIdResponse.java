package com.cuscatlan.backendsr.lib.dto.comics.api;

import com.cuscatlan.backendsr.lib.dto.comics.MarvelComicsResponse;
import com.cuscatlan.backendsr.lib.util.dto.Result;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "result",
    "data"
})
@Data
public class ComicsIdResponse {
	private Result result;
	private MarvelComicsResponse data;
}
