package com.rale.bonkerlist.spotifyapi.dto;

import java.util.ArrayList;

public class RootFromPlaylist {
    public String href;
    public ArrayList<ItemFromPlaylist> items;
    public int limit;
    public Object next;
    public int offset;
    public Object previous;
    public int total;
}
