package com.masterpiece.cracking.aniboayo;

/**
 * Created by User on 2017-02-17.
 */

public class CardItem {
    private String imageUrl;
    private String name;

    public CardItem(String image, String name) {
        this.imageUrl = image;
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setImageUrl(String ImageUrl) {
        this.imageUrl = ImageUrl;
    }

    public void setName(String Name) {
        this.name = Name;
    }
}
