package com.schedule.tool;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2017/7/22.
 */
public class RandomId {
    public String randomNumForId(){
        int length = 6;
        String[] beforeShuffle = new String[]{"d", "s", "b", "m", "u", "l", "x", "c", "k"};
        List list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        String part1=afterShuffle.substring(2, 2 + length);
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getTimeInstance();
        String s =dateFormat.format(date);
        return part1.concat(s);
    }
}
