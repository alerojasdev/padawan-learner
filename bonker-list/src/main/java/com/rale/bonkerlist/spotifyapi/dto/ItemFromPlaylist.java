package com.rale.bonkerlist.spotifyapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.ArrayList;

@ToString
public class ItemFromPlaylist {
    public Boolean collaborative;
    public String description;
    public ExternalUrls external_urls;
    public String href;
    public String id;
    public ArrayList<Image> images;
    public String name;
    public Owner owner;
    public Object primary_color;
    @JsonProperty("public")
    public boolean mypublic;
    public String snapshot_id;
    public Tracks tracks;
    public String type;
    public String uri;
}
