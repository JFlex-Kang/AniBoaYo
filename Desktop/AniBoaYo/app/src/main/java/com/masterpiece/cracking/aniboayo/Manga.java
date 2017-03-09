package com.masterpiece.cracking.aniboayo;

/**
 * Created by Bitch on 2017-02-24.
 */

public class Manga {
    private String name;
    private String imgUrl;

    public Manga(String Name, String ImgUrl) {
        this.name = Name;
        this.imgUrl = ImgUrl;
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

}
