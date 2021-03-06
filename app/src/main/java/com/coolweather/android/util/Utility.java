package com.coolweather.android.util;

import android.text.TextUtils;
import android.util.Log;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;
import com.coolweather.android.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hepei on 2017-06-23.
 * function:json数据解析类
 */

public class Utility {
    private static final String TAG = "Utility";

    /*
    * 解析和处理服务器返回的省级数据
    * */
    public static boolean handlerProvinceResponse(String response){
        if (!TextUtils.isEmpty(response)){
            Log.d(TAG, "handlerProvinceResponse: ");
            Log.d(TAG, "handlerProvinceResponse: "+response);
            try{
                JSONArray allProvinces = new JSONArray(response);
                for (int i=0;i<allProvinces.length();i++){
                    Log.d(TAG, "handlerProvinceResponse: "+i);
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.setProvinceName(provinceObject.getString("name"));
                    province.save();
                }
                Log.d(TAG, "handlerProvinceResponse: finish");
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /*
    * 解析和处理服务费返回的市级数据
    * */
    public static boolean handlerCityResponse(String response,int provinceId){
        if (!TextUtils.isEmpty(response)){
            Log.d(TAG, "handlerCityResponse: ");
            try{
                JSONArray allCities = new JSONArray(response);
                for (int i=0;i<allCities.length();i++){
                    Log.d(TAG, "handlerCityResponse: "+i);
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /*
    * 处理和解析服务器返回的县级数据
    * */
    public static boolean handerCountyResponse(String response,int cityId){
        if (!TextUtils.isEmpty(response)){
            Log.d(TAG, "handerCountyResponse: ");
            try{
                JSONArray allCounties = new JSONArray(response);
                for (int i=0;i<allCounties.length();i++){
                    Log.d(TAG, "handerCountyResponse: "+i);
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setCityId(cityId);
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /*将返回的josn数据解析成Weaather实体类*/
    public static Weather handlerWeatherResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,Weather.class);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
