package com.android.weatherjecpack.model.data.weather;

import com.google.gson.annotations.SerializedName;

public class Basic {
    @SerializedName("city")
    public String cityName = "";
    @SerializedName("id")
    public String weatherId = "";
    public Update update;

    public class Update {
        @SerializedName("loc")
        public String updateTime = "";

        public String slipTime(){
            return updateTime.split(" ")[1];
        }

    }
}
