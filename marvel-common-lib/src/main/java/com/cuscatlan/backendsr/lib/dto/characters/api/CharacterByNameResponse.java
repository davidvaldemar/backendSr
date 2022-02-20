package com.cuscatlan.backendsr.lib.dto.characters.api;

import com.cuscatlan.backendsr.lib.dto.characters.MarvelCharactersResponse;
import com.cuscatlan.backendsr.lib.util.dto.Result;

import lombok.Data;

@Data
public class CharacterByNameResponse {
 private Result result;
 private MarvelCharactersResponse data;
}
