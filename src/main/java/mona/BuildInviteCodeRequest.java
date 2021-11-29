package mona;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author jambestwick
 * @create 2021/11/30 0030  0:09
 * @email jambestwick@126.com
 */
public class BuildInviteCodeRequest {

    public static Map<String,String> buildInviteParam(String address,String inviteCode,String signature){

        Map<String,String>map =new HashMap<>();

        map.put("address",address);
        map.put("invite_code",inviteCode);
        map.put("signature",signature);
        return map;

    }
}
