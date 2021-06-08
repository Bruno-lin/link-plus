package us.linkpl.linkplus.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
//        String id = operations.get("userId");
        HttpSession session = request.getSession();
        Object id = session.getAttribute("userId");
        if (id == null) {
//            System.out.println("401:"+request.getServletPath());
//            session.setAttribute("userId",0);
            response.setStatus(401);
            return false;
//            return true;
        } else {
//            session.setAttribute("userId", Integer.valueOf(id));
            return true;
        }
    }
}