package com.snowcattle.game.common.enums;

/**
 * Created by jwp on 2017/2/9.
 */
public enum DeliveryGuarantyOption
{
    /**
     * socket
     */
    RELIABLE(0),
    /**
     * udp
     */
    FAST(1);
    final int guaranty;

    DeliveryGuarantyOption(int guaranty)
    {
        this.guaranty = guaranty;
    }

    public int getGuaranty(){
        return guaranty;
    }
}