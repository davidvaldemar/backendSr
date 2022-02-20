package com.cuscatlan.backendsr.lib.dto.characters.api;

import com.cuscatlan.backendsr.lib.dto.characters.Comics;
import com.cuscatlan.backendsr.lib.util.dto.Result;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ComicsListByCharacterResponse {
	private Result result;
	private Comics data;
}
