package com.album.photos.photos;

/**
 * Created by basis_000 on 12/10/2017.
 */

public class Tag {
    String type;
    String value;
    public Tag(String type, String value){
        this.type = type;
        this.value = value;
    }

    public boolean equals(Object o){
        if(!(o instanceof Tag))
            return false;
        Tag t = (Tag)o;
        if(t.type.equals(type) && t.value.equals(value))
            return true;
        return false;
    }

    public String toString(){
        return "Type: " + type + ", Value: " + value;
    }
}
