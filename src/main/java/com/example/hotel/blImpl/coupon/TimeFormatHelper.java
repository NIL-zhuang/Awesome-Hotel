package com.example.hotel.blImpl.coupon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/5/30 19:30
 * @see LocalDateTime
 * @see String
 * 用于LocalDateTime和String类的互相转换
 */
public class TimeFormatHelper {
    private final static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private final static String dump = " 14:00:00";

    public static String time2String(LocalDateTime time) {
        return df.format(time);
    }

    /**
     * @param time 必须是yyyy-mm-dd形式
     * @return 当天的开始时间
     */
    public static LocalDateTime string2Time(String time) {
        return LocalDateTime.parse(time + dump, df);
    }

    public static Date string2Date(String time) {
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
