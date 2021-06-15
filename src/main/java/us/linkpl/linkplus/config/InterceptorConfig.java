package us.linkpl.linkplus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import us.linkpl.linkplus.interceptor.AuthInterceptor;
import us.linkpl.linkplus.interceptor.HeaderInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    AuthInterceptor authInterceptor;

    @Autowired
    HeaderInterceptor headerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/account/login")  //过滤掉登录页面
                .excludePathPatterns("/api/account/register")  //过滤掉注册页面
                .excludePathPatterns("/api/account/username")
                .excludePathPatterns("/api/account/nickname")
                .excludePathPatterns("/api/account/show")
                .excludePathPatterns("/api/search/id/**")
                .excludePathPatterns("/api/search/name/**")
                .excludePathPatterns("/static/**");  //过滤掉静态资源

//        registry.addInterceptor(headerInterceptor).addPathPatterns("/api/**");
    }
}
