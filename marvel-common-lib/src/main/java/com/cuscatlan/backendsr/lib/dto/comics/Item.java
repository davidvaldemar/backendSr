
package com.cuscatlan.backendsr.lib.dto.comics;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "resourceURI",
    "name",
    "role"
})
@Data
public class Item {

    @JsonProperty("resourceURI")
    public String resourceURI;
    @JsonProperty("name")
    public String name;
    @JsonProperty("role")
    public String role;

}
