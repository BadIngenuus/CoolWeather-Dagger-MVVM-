package com.android.weatherjecpack.model.data.place;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.LitePalSupport;

public class Province extends LitePalSupport {

    @SerializedName("name")
    String provinceName;

    @SerializedName("id")
    String provinceId;

    public String getProvinceName() {
        return provinceName;
    }

    public String getProvinceId() {
        return provinceId;
    }
}
