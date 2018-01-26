package com.dyy.newtest.bean;

import java.util.List;

/**
 * Created by DY on 2018/1/16.
 */

public class Province {
    private String proName;
    private List<City> city;

    public Province(String proName, List<City> city) {
        this.proName = proName;
        this.city = city;
    }

    public static class City{
        private String cityName;

        public City(String cityName) {
            this.cityName = cityName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }
}
