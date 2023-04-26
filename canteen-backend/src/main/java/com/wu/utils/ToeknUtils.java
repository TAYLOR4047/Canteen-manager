package com.wu.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;


import java.util.Date;

public class ToeknUtils {

    public static String getToken(String userId,String sign){
        return JWT.create().withAudience(userId).withExpiresAt(DateUtil.offsetHour(new Date(),2)).sign(Algorithm.HMAC256(sign));
    }
}
