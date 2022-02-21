package com.cuscatlan.backendsr.lib.dto.characters.api;

import java.util.List;

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
public class CharacterAllImageDescriptionResponse {
 private Result result;
 private List<ImageDescription> data;

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
