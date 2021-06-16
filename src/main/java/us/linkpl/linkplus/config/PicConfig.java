package us.linkpl.linkplus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import us.linkpl.linkplus.commom.Consts;


@Configuration
public class PicConfig implements WebMvcConfigurer {

    /**
     * 通过/images/访问图片资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("file:" + Consts.FILE_ROOT+"/images/");
    }
}
