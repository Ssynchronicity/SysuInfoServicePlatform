package com.example.song.sysuinfoserviceplatform.student;

import android.os.Parcel;
import android.os.Parcelable;

class Detail implements Parcelable{
    String scoFinalScore;
    double scoCredit;
    String teachClassRank;
    String scoTeacherName;
    double scoPoint;
    String scoCourseCategoryName;
    String scoCourseName;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.scoFinalScore);
        dest.writeDouble(this.scoCredit);
        dest.writeString(this.teachClassRank);
        dest.writeString(this.scoTeacherName);
        dest.writeDouble(this.scoPoint);
        dest.writeString(this.scoCourseCategoryName);
        dest.writeString(this.scoCourseName);
    }

    public Detail() {
    }

    protected Detail(Parcel in) {
        this.scoFinalScore = in.readString();
        this.scoCredit = in.readDouble();
        this.teachClassRank = in.readString();
        this.scoTeacherName = in.readString();
        this.scoPoint = in.readDouble();
        this.scoCourseCategoryName = in.readString();
        this.scoCourseName = in.readString();
    }

    public static final Creator<Detail> CREATOR = new Creator<Detail>() {
        @Override
        public Detail createFromParcel(Parcel source) {
            return new Detail(source);
        }

        @Override
        public Detail[] newArray(int size) {
            return new Detail[size];
        }
    };
}

public class Score_response {
    private String totalPaiming;
    private double totalvegPoint;
    private int thisYearPaiming;
    private double thisYearvegPoint;
    public Detail[] detail;

}
