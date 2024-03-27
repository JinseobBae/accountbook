package myhome.accountbook.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AuthUtils {

    private static final String KEY = "220611";
    private static final Map<String, Boolean> successMap = new HashMap<>();
    private static final Map<String, LocalDateTime> successTimeMap = new HashMap<>();


    public static boolean checkAuth() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String mapKey = request.getRemoteAddr();
        Boolean auth = successMap.getOrDefault(mapKey, Boolean.FALSE);
        if (auth) {
            LocalDateTime authTime = successTimeMap.get(request.getRemoteAddr());

            if (authTime != null && LocalDateTime.now().isBefore(authTime.plusHours(1))){
                return true;
            } else  {
                successMap.put(mapKey, Boolean.FALSE);
                return false;
            }

        }

        return auth;
    }

    public static boolean doAuth(String input) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String mapKey = request.getRemoteAddr();
        boolean auth = false;
        if (KEY.equals(input)) {
            successMap.put(mapKey, Boolean.TRUE);
            successTimeMap.put(request.getRemoteAddr(), LocalDateTime.now());
            auth = true;
        }

        return auth;
    }
}
