
package com.cuscatlan.backendsr.lib.dto.creators;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "code",
    "status",
    "copyright",
    "attributionText",
    "attributionHTML",
    "etag",
    "data"
})
@lombok.Data
public class MarvelCreatorsResponse {

    @JsonProperty("code")
    public Integer code;
    @JsonProperty("status")
    public String status;
    @JsonProperty("copyright")
    public String copyright;
    @JsonProperty("attributionText")
    public String attributionText;
    @JsonProperty("attributionHTML")
    public String attributionHTML;
    @JsonProperty("etag")
    public String etag;
    @JsonProperty("data")
    public Data data;

}
