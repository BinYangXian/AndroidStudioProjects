package com.jikexueyuan.baiduwaimailayout.models;

/**
 * Created by fangc on 2016/2/29.
 */
public class ShopMessage {
    private String name;
    private String distance;
    private String extraMessages;
    private String soldNumber;
    private int shopImageId;

    private int halfStar;

    public ShopMessage(String name, String distance, String extraMessages, String soldNumber, int shopImageId, int halfStar) {
        this.name = name;
        this.distance = distance;
        this.extraMessages = extraMessages;
        this.soldNumber = soldNumber;
        this.shopImageId = shopImageId;
        this.halfStar = halfStar;
    }

    public String getName() {
        return name;
    }

    public String getDistance() {
        return distance;
    }

    public String getExtraMessages() {
        return extraMessages;
    }

    public String getSoldNumber() {
        return soldNumber;
    }

    public int getShopImageId() {
        return shopImageId;
    }

    public int getHalfStar() {
        return halfStar;
    }
}
