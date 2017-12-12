package com.album.photos.photos;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by basis_000 on 12/10/2017.
 */

public class Photo implements Serializable{
    String caption;
    String path;
    //LocalDateTime capture;
    ArrayList<Tag> tags = new ArrayList<Tag>();

    //	public Photo(){
//		capture = LocalDateTime.now();
//	}
    public Photo(String path)
    {
        //capture = getDate(path);
        this.path = path;
    }
    public Photo(String path, String caption){
        //capture = getDate(path);
        this.caption = caption;
        this.path = path;
    }
    public Photo(String path, String caption, ArrayList<Tag> tags){
        //capture = getDate(path);
        this.caption = caption;
        this.path = path;
        this.tags = tags;
    }
    public Photo copy()
    {
        ArrayList<Tag> newTags = new ArrayList<Tag>();
        for(Tag t:this.tags){
            newTags.add(t);
        }
        return new Photo(this.getPath(),this.getCaption(),newTags);
    }
//    private LocalDateTime getDate(String fpath)
//    {
//        File f = new File(fpath.substring(5));
//        java.text.SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String formated = formatter.format(f.lastModified());
//
//        DateTimeFormatter dformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//        LocalDateTime ans = LocalDateTime.parse(formated);
//        return ans;
//
//    }

//    public boolean matches(LocalDate startDate, LocalDate endDate, ObservableList<Tag> searchTags){
//        Iterator<Tag> tI = searchTags.iterator();
//        while(tI.hasNext()){
//            Tag t = tI.next();
//            if(!tags.contains(t))
//                return false;
//        }
//        if(startDate == null && endDate == null)
//            return true;
//        if(capture.toLocalDate().compareTo(startDate)>=0 && capture.toLocalDate().compareTo(endDate)<=0)
//            return true;
//        return false;
//    }
    public boolean matches(ArrayList<Tag> searchTags){
        Iterator<Tag> searchIterator = searchTags.iterator();
        while(searchIterator.hasNext()){
            Tag searchTag = searchIterator.next();
            Iterator<Tag> matchIterator = this.tags.iterator();
            while(matchIterator.hasNext()){
                Tag matchTag = matchIterator.next();
                if(!(matchTag.type.equals(searchTag.type)&&matchTag.value.startsWith(searchTag.value)))
                    return false;
            }
        }
        return true;
    }

    public String toString(){
        return this.path;
    }
    public String getCaption(){
        return this.caption==null ? "" : this.caption;
    }
    public void setCaption(String s)
    {
        this.caption = s;
    }
    public String getPath(){
        return this.path;
    }
    public ArrayList<Tag> getTags()
    {
        return this.tags;
    }
    public String tagString(){
        String s = "";
        if(this.tags.isEmpty()){
            return s;
        }
        for(Tag t : this.tags)
        {
            s+=t.type + ":" + t.value + ", ";
        }
        return s.substring(0, s.lastIndexOf(","));
    }
//    public String formatedDate()
//    {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//        return this.capture.format(formatter);
//    }
}
