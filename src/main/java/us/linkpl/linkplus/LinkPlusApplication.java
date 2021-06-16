package us.linkpl.linkplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "us.linkpl.linkplus.mapper")
public class LinkPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinkPlusApplication.class, args);
    }

}
