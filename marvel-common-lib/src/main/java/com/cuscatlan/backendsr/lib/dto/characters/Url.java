
package com.cuscatlan.backendsr.lib.dto.characters;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "url"
})
public class Url {

    @JsonProperty("type")
    public String type;
    @JsonProperty("url")
    public String url;

}
