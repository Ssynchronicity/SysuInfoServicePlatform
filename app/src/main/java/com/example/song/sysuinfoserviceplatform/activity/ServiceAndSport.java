package com.example.song.sysuinfoserviceplatform.activity;

import java.io.Serializable;

public class ServiceAndSport implements Serializable {
    private ActivityDataModel serviceModel, sportModel;
    public ServiceAndSport(ActivityDataModel ldm, ActivityDataModel cdm) {
        setServiceModel(ldm);
        setSportModel(cdm);
    }

    public ActivityDataModel getServiceModel() {
        return serviceModel;
    }

    public ActivityDataModel getSportModel() {
        return sportModel;
    }

    public void setServiceModel(ActivityDataModel serviceModel) {
        this.serviceModel = serviceModel;
    }

    public void setSportModel(ActivityDataModel sportModel) {
        this.sportModel = sportModel;
    }
}
