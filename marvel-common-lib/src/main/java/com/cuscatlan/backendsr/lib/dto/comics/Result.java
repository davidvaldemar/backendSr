
package com.cuscatlan.backendsr.lib.dto.comics;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "digitalId",
    "title",
    "issueNumber",
    "variantDescription",
    "description",
    "modified",
    "isbn",
    "upc",
    "diamondCode",
    "ean",
    "issn",
    "format",
    "pageCount",
    "textObjects",
    "resourceURI",
    "urls",
    "series",
    "variants",
    "collections",
    "collectedIssues",
    "dates",
    "prices",
    "thumbnail",
    "images",
    "creators",
    "characters",
    "stories",
    "events"
})
@Data
public class Result {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("digitalId")
    public Integer digitalId;
    @JsonProperty("title")
    public String title;
    @JsonProperty("issueNumber")
    public Integer issueNumber;
    @JsonProperty("variantDescription")
    public String variantDescription;
    @JsonProperty("description")
    public String description;
    @JsonProperty("modified")
    public String modified;
    @JsonProperty("isbn")
    public String isbn;
    @JsonProperty("upc")
    public String upc;
    @JsonProperty("diamondCode")
    public String diamondCode;
    @JsonProperty("ean")
    public String ean;
    @JsonProperty("issn")
    public String issn;
    @JsonProperty("format")
    public String format;
    @JsonProperty("pageCount")
    public Integer pageCount;
    @JsonProperty("textObjects")
    public List<TextObject> textObjects = null;
    @JsonProperty("resourceURI")
    public String resourceURI;
    @JsonProperty("urls")
    public List<Url> urls = null;
    @JsonProperty("series")
    public Series series;
    @JsonProperty("variants")
    public List<Variant> variants = null;
    @JsonProperty("collections")
    public List<Object> collections = null;
    @JsonProperty("collectedIssues")
    public List<Object> collectedIssues = null;
    @JsonProperty("dates")
    public List<Date> dates = null;
    @JsonProperty("prices")
    public List<Price> prices = null;
    @JsonProperty("thumbnail")
    public Thumbnail thumbnail;
    @JsonProperty("images")
    public List<Image> images = null;
    @JsonProperty("creators")
    public Creators creators;
    @JsonProperty("characters")
    public Characters characters;
    @JsonProperty("stories")
    public Stories stories;
    @JsonProperty("events")
    public Events events;

}
