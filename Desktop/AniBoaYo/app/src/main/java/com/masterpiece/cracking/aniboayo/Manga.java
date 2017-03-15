package com.masterpiece.cracking.aniboayo;

/**
 * Created by Bitch on 2017-02-24.
 */

public class Manga {
    private int id;
    private String name;
    private String imgUrl;

    private String manga_url;

    public Manga(int id, String Name,String manga_url, String ImgUrl) {
        this.id = id;
        this.name = Name;
        this.imgUrl = ImgUrl;
        this.manga_url = manga_url;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManga_url() {
        return manga_url;
    }

    public void setManga_url(String manga_url) {
        this.manga_url = manga_url;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o==null)
            return false;
        if(getClass()!=o.getClass())
            return false;
        Manga other  = (Manga) o;
        if(id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Manga [id="+id+", name="+name+", manga_Url="+manga_url+"]";
    }
}
