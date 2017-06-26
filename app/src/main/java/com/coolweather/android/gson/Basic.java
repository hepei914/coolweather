package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hepei on 2017-06-23.
 */

public class Basic {

    /*json中的一些字段不适合直接作为java字段来命名，这里使用@SerializedName注解的方式来让json字段和java字段建立映射关系*/

    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update{

        @SerializedName("loc")
        public String updateTime;

    }
}
