package com.example.song.sysuinfoserviceplatform.admin;

import android.util.Log;

import java.io.Serializable;
import java.util.List;

/**
 * Created by song on 2018/5/20.
 */

public class ListDataModel implements Serializable {
    private List<ListData> data;

    public List<ListData> getData() {
        return data;
    }

    public void setData(List<ListData> data) {
        this.data = data;
    }
}
