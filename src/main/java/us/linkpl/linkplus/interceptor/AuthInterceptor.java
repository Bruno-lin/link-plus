package us.linkpl.linkplus.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpCookie;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Configuration
public class AuthInterceptor implements HandlerInterceptor {

/*    @Autowired
    StringRedisTemplate stringRedisTemplate;*/

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8082");
        response.addHeader("Access-Control-Allow-Credentials", String.valueOf(true));
        response.addHeader("content-test","123");
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        System.out.println(Arrays.toString(cookies));
        if (cookies != null) {
            String key1 = "1";
            String key2 = "2";
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("id")) {
                    key1 = (String) session.getAttribute(cookie.getValue());
                }
                if (cookie.getName().equals("key")) {
                    key2 = cookie.getValue();
                }
            }
            if (key1.equals(key2)) {
                return true;
            }
        }
        response.setStatus(401);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,  ModelAndView modelAndView) throws Exception {

    }
}
