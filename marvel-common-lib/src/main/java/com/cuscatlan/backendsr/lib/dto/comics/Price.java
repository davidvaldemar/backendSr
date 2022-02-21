
package com.cuscatlan.backendsr.lib.dto.comics;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "price"
})
@Data
public class Price {

    @JsonProperty("type")
    public String type;
    @JsonProperty("price")
    public Double price;

}
