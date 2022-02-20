
package com.cuscatlan.backendsr.lib.dto.characters;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "resourceURI",
    "name"
})
public class Item__1 {

    @JsonProperty("resourceURI")
    public String resourceURI;
    @JsonProperty("name")
    public String name;

}
