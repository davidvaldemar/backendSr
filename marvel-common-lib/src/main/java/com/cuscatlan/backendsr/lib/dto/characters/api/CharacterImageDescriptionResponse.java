package com.cuscatlan.backendsr.lib.dto.characters.api;

import com.cuscatlan.backendsr.lib.dto.characters.Thumbnail;
import com.cuscatlan.backendsr.lib.util.dto.Result;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "result",
    "data"
})
@Data
public class CharacterImageDescriptionResponse {
 private Result result;
 private ImageDescription data;

 @JsonInclude(JsonInclude.Include.NON_NULL)
 @JsonPropertyOrder({
	 "name",
     "description",
     "thumbnail"
 })
 @Data
 @Builder
public static class ImageDescription{
	private String name;
	private String description;
	private Thumbnail thumbnail;
}
}
