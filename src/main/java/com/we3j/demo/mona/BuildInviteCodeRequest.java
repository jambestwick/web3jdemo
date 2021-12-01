package com.we3j.demo.mona;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author jambestwick
 * @create 2021/11/30 0030  0:09
 * @email jambestwick@126.com
 */
public class BuildInviteCodeRequest {

    public static Map<String, String> buildInviteParam(String address, String inviteCode, String signature) {

        Map<String, String> map = new HashMap<>();

        map.put("address", address);
        map.put("invite_code", inviteCode);
        map.put("signature", signature);
        return map;

    }

    public static Map<String, String> buildHead(String accept
            , String accept_encoding
            , String accept_language
            , String content_type
            , String origin
            , String referer
            , String secChUa
            , String secChUaMobile
            , String secChUaPlatform
            , String secFetchDest
            , String secFetchMode
            , String secFetchSite
            , String token
            , String user_agent) {
        Map<String, String> map = new HashMap<>();

        map.put("accept", accept);
        map.put("accept-encoding", accept_encoding);
        map.put("accept-language", accept_language);
        map.put("content-type", content_type);
        map.put("origin", origin);
        map.put("referer", referer);
        map.put("sec-ch-ua", secChUa);
        map.put("sec-ch-ua-mobile", secChUaMobile);
        map.put("sec-ch-ua-platform", secChUaPlatform);
        map.put("sec-fetch-dest", secFetchDest);
        map.put("sec-fetch-mode", secFetchMode);
        map.put("sec-fetch-site", secFetchSite);
        map.put("token", token);
        map.put("user-agent", user_agent);
        return map;
    }
}
