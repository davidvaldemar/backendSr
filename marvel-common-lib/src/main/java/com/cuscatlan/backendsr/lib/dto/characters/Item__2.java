
package com.cuscatlan.backendsr.lib.dto.characters;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "resourceURI",
    "name",
    "type"
})
public class Item__2 {

    @JsonProperty("resourceURI")
    public String resourceURI;
    @JsonProperty("name")
    public String name;
    @JsonProperty("type")
    public String type;

}
