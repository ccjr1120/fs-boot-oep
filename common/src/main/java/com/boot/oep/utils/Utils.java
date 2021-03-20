package com.boot.oep.utils;

import java.util.Locale;
import java.util.UUID;

/**
 * @author ccjr
 * @date 2021/3/20 11:16 下午
 */
public class Utils {

    public static String getUuId(){
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

}
