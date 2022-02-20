
package com.cuscatlan.backendsr.lib.dto.characters;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "code",
    "descripcion",
    "uuid"
})
@Data
public class TransactionResult {

    @JsonProperty("code")
    public Integer code;
    @JsonProperty("descripcion")
    public String descripcion;
    @JsonProperty("uuid")
    public String uuid;

}
