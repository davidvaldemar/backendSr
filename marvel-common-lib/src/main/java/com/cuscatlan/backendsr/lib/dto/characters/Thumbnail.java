
package com.cuscatlan.backendsr.lib.dto.characters;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "path",
    "extension"
})
public class Thumbnail {

    @JsonProperty("path")
    public String path;
    @JsonProperty("extension")
    public String extension;

}
