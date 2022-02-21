
package com.cuscatlan.backendsr.lib.dto.comics;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "url"
})
@Data
public class Url {

    @JsonProperty("type")
    public String type;
    @JsonProperty("url")
    public String url;

}
