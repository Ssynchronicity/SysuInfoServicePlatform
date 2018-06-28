package com.example.song.sysuinfoserviceplatform.admin;

import java.io.Serializable;

/**
 * Created by song on 2018/6/1.
 */

public class ListAndCard implements Serializable{
    private ListDataModel listDataModel;
    private CardDataModel cardDataModel;

    public ListAndCard(ListDataModel ldm,CardDataModel cdm) {
        setCardDataModel(cdm);
        setListDataModel(ldm);
    }

    public CardDataModel getCardDataModel() {
        return cardDataModel;
    }

    public ListDataModel getListDataModel() {
        return listDataModel;
    }

    public void setCardDataModel(CardDataModel cardDataModel) {
        this.cardDataModel = cardDataModel;
    }

    public void setListDataModel(ListDataModel listDataModel) {
        this.listDataModel = listDataModel;
    }
}
