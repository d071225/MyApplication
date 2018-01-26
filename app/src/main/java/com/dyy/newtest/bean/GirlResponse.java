package com.dyy.newtest.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DY on 2018/1/5.
 */

public class GirlResponse implements Serializable{

    private static final long serialVersionUID = 6679677787145646256L;
    private String tag1;
    private String tag2;
    private String totalNum;
    private String start_index;
    private String return_number;
    private List<DesData> data;

    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public String getTag2() {
        return tag2;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getStart_index() {
        return start_index;
    }

    public void setStart_index(String start_index) {
        this.start_index = start_index;
    }

    public String getReturn_number() {
        return return_number;
    }

    public void setReturn_number(String return_number) {
        this.return_number = return_number;
    }

    public List<DesData> getData() {
        return data;
    }

    public void setData(List<DesData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GirlResponse{" +
                "tag1='" + tag1 + '\'' +
                ", tag2='" + tag2 + '\'' +
                ", totalNum='" + totalNum + '\'' +
                ", start_index='" + start_index + '\'' +
                ", return_number='" + return_number + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     *   "id": "9406120082",
     "setId": "-1",
     "pn": 0,
     "abs": "小清新5★★★☆",
     "desc": "小清新5★★★☆",
     */
    public class DesData implements Serializable{
        private static final long serialVersionUID = 1981754498607122627L;
        private String id;
        private String abs;
        private String image_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAbs() {
            return abs;
        }

        public void setAbs(String abs) {
            this.abs = abs;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        @Override
        public String toString() {
            return "DesData{" +
                    "id='" + id + '\'' +
                    ", abs='" + abs + '\'' +
                    ", image_url='" + image_url + '\'' +
                    '}';
        }
    }
}
