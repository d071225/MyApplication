package com.dyy.newtest.test;

import com.dyy.newtest.utils.AES;
import com.dyy.newtest.utils.AESUtils;

import java.util.Random;
import java.util.UUID;

/**
 * Created by DY on 2018/1/26.
 */

public class Demo {
    public static void main(String[] args) {
        Random random=new Random();
        Random random2=new Random();
        Random random3=new Random();

//        System.out.println("rd1="+rd1);
//        System.out.println("rd2="+rd2);
//        System.out.println("rd3="+rd3);
//        for (int i=0;i<50;i++){
//            int rd1 = random.nextInt(255);
//            int rd2 = random2.nextInt(255);
//            int rd3 = random3.nextInt(255);
//            System.out.println("rd1="+rd1);
//            System.out.println("rd2="+rd2);
//            System.out.println("rd3="+rd3);
//        }
//        Date dayBegin = DateUtils.getDayBegin();
//        System.out.println(dayBegin.toString());
//        System.out.println(DateUtils.getBeginDayOfYesterday().toString());
//        System.out.println(DateUtils.dateToTimeStamp(dayBegin));
//        System.out.println(DateUtils.dateToTimeStamp(DateUtils.getBeginDayOfYesterday()));
//        Date date=new Date();
//        System.out.println(date.toString());
//        String res;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date date1 = simpleDateFormat.parse(dayBegin.toString());
//            Date date1=new Date();
//            long ts = date1.getTime();
//            res = String.valueOf(ts);
//            System.out.println(res);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//        System.out.println(sdf.format(new java.util.Date()));
//        System.out.println(DateUtils.getDateToString(ts,"yyyy-MM-dd HH:mm:ss"));
//        System.out.println(DateUtils.getStrTime(res));
//        System.out.println(DateUtils.getStringToDate("2018-02-01 00:00:00.0"));
//        Date dayBegin = DateUtils.getDayBegin();
//        String yyyymmdd = TimeUtils.date2String(dayBegin, new SimpleDateFormat("yyyyMMdd"));
//        System.out.println("dayBegin:"+dayBegin);
//        System.out.println("yyyymmdd:"+yyyymmdd);
//        Date yday = DateUtils.getBeginDayOfYesterday();
//        String yesterday = TimeUtils.date2String(yday, new SimpleDateFormat("yyyyMMdd"));
//        System.out.println("yday:"+yday);
//        System.out.println("yesterday:"+yesterday);
//        Date byday = DateUtils.getBeginDayOfBeforeYesterday();
//        String byesterday = TimeUtils.date2String(byday, new SimpleDateFormat("yyyyMMdd"));
//        System.out.println("byday:"+byday);
//        System.out.println("byesterday:"+byesterday);


//        String ipAddress = NetworkUtils.getIPAddress(true);
//        String ipAddress1 = NetworkUtils.getIPAddress(false);
//        System.out.println(ipAddress+";"+ipAddress1);
//        String domainAddress = NetworkUtils.getDomainAddress("http://www.baidu.com");
//        System.out.println(domainAddress);

        String token = UUID.randomUUID().toString();
        System.out.println(token);

        //将websocket urls转化为http urls
        String url="ws://localhost:8080/Chat/websocket";
        boolean httpFlag = url.regionMatches(true, 0, "ws:", 0, 3);
        boolean httpsFlag = url.regionMatches(true, 0, "wss:", 0, 4);
        System.out.println(httpFlag+";"+httpsFlag);
        if (httpFlag) {
            url = "http:" + url.substring(3);
            System.out.println("http--"+url);
        } else if (httpsFlag) {
            url = "https:" + url.substring(4);
            System.out.println("https--"+url);
        }

        String key="91C30FB7B5193593B19BF776BED2517D";
        String keyContent="E30E703F2F90DD7C99D3357896DEA59A";
        String decrypt = AES.decrypt(keyContent, AES.hex2byte(key));
        System.out.println(decrypt);

        String seed = "123456789";
        String KEY1 = AES.generateKeyString(seed);
        String KEY2 = AES.generateKeyString(seed);
        String KEY3 = AES.generateKeyString(seed);
        System.out.println(KEY1);
        System.out.println(KEY2);
        System.out.println(KEY3);

        try {
            byte[] rawKey = AESUtils.getRawKey(seed.getBytes());
            System.out.println(AES.byte2hex(rawKey));
            byte[] rawKey2 = AESUtils.getRawKey(seed.getBytes());
            System.out.println(AES.byte2hex(rawKey2));
            byte[] rawKey3 = AESUtils.getRawKey(seed.getBytes());
            System.out.println(AES.byte2hex(rawKey3));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
