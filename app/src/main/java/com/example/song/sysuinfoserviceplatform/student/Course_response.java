package com.example.song.sysuinfoserviceplatform.student;

import android.os.Parcel;
import android.os.Parcelable;

class myTable implements Parcelable{
    String monday;
    String tuesday;
    String wednesday;
    String thursday;
    String friday;
    String saturday;
    String sunday;
    int section;
    int weekly;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.monday);
        dest.writeString(this.tuesday);
        dest.writeString(this.wednesday);
        dest.writeString(this.thursday);
        dest.writeString(this.friday);
        dest.writeString(this.saturday);
        dest.writeString(this.sunday);
        dest.writeInt(this.section);
        dest.writeInt(this.weekly);
    }

    public myTable() {
    }

    protected myTable(Parcel in) {
        this.monday = in.readString();
        this.tuesday = in.readString();
        this.wednesday = in.readString();
        this.thursday = in.readString();
        this.friday = in.readString();
        this.saturday = in.readString();
        this.sunday = in.readString();
        this.section = in.readInt();
        this.weekly = in.readInt();
    }

    public static final Creator<myTable> CREATOR = new Creator<myTable>() {
        @Override
        public myTable createFromParcel(Parcel source) {
            return new myTable(source);
        }

        @Override
        public myTable[] newArray(int size) {
            return new myTable[size];
        }
    };
}

public class Course_response {
    public int code;
    public myTable[] data;

}
