package com.example.song.sysuinfoserviceplatform.admin;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by song on 2018/5/20.
 */

public class CardDataModel implements Serializable{
    private List<CardData> data;

    public List<CardData> getData() {
        return data;
    }

    public void setData(List<CardData> data) {
        this.data = data;
    }
}
