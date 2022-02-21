package com.cuscatlan.backendsr.lib.dto.characters.api;

import com.cuscatlan.backendsr.lib.dto.characters.Comics;
import com.cuscatlan.backendsr.lib.util.dto.Result;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "result",
    "result"
})
@Data
public class CharacterComicsListResponse {
	private Result result;
	private Comics data;
}
