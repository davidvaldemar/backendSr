package com.cuscatlan.backendsr.lib.dto.characters.api;

import com.cuscatlan.backendsr.lib.dto.characters.MarvelCharactersResponse;
import com.cuscatlan.backendsr.lib.util.dto.Result;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "result",
    "result"
})
@Data
public class CharacterByNameResponse {
 private Result result;
 private MarvelCharactersResponse data;
}
