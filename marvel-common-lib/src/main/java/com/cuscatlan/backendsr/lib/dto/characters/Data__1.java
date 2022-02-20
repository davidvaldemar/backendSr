
package com.cuscatlan.backendsr.lib.dto.characters;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "offset",
    "limit",
    "total",
    "count",
    "results"
})
@lombok.Data
public class Data__1 {

    @JsonProperty("offset")
    public Integer offset;
    @JsonProperty("limit")
    public Integer limit;
    @JsonProperty("total")
    public Integer total;
    @JsonProperty("count")
    public Integer count;
    @JsonProperty("results")
    public List<Result> results = null;

}
