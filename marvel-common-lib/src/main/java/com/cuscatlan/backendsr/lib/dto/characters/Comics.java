
package com.cuscatlan.backendsr.lib.dto.characters;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "available",
    "collectionURI",
    "items",
    "returned"
})
@lombok.Data
public class Comics {

    @JsonProperty("available")
    public Integer available;
    @JsonProperty("collectionURI")
    public String collectionURI;
    @JsonProperty("items")
    public List<Item> items = null;
    @JsonProperty("returned")
    public Integer returned;

}
