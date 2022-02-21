
package com.cuscatlan.backendsr.lib.dto.comics;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "language",
    "text"
})
@Data
public class TextObject {

    @JsonProperty("type")
    public String type;
    @JsonProperty("language")
    public String language;
    @JsonProperty("text")
    public String text;

}
