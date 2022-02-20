
package com.cuscatlan.backendsr.lib.dto.characters;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "description",
    "modified",
    "thumbnail",
    "resourceURI",
    "comics",
    "series",
    "stories",
    "events",
    "urls"
})
@lombok.Data
public class Result {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("description")
    public String description;
    @JsonProperty("modified")
    public String modified;
    @JsonProperty("thumbnail")
    public Thumbnail thumbnail;
    @JsonProperty("resourceURI")
    public String resourceURI;
    @JsonProperty("comics")
    public Comics comics;
    @JsonProperty("series")
    public Series series;
    @JsonProperty("stories")
    public Stories stories;
    @JsonProperty("events")
    public Events events;
    @JsonProperty("urls")
    public List<Url> urls = null;

}
