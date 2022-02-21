
package com.cuscatlan.backendsr.lib.dto.creators;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "firstName",
    "middleName",
    "lastName",
    "suffix",
    "fullName",
    "modified",
    "thumbnail",
    "resourceURI",
    "comics",
    "series",
    "stories",
    "events",
    "urls"
})
@Data
public class Result {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("firstName")
    public String firstName;
    @JsonProperty("middleName")
    public String middleName;
    @JsonProperty("lastName")
    public String lastName;
    @JsonProperty("suffix")
    public String suffix;
    @JsonProperty("fullName")
    public String fullName;
    @JsonProperty("modified")
    public String modified;
    @JsonProperty("thumbnail")
    public Thumbnail thumbnail;
    @JsonProperty("resourceURI")
    public String resourceURI;
    @JsonProperty("comics")
    public Comics comics;
    /*
    @JsonProperty("series")
    public Series series;
    @JsonProperty("stories")
    public Stories stories;
    @JsonProperty("events")
    public Events events;
    @JsonProperty("urls")
    public List<Url> urls = null;
*/
}
