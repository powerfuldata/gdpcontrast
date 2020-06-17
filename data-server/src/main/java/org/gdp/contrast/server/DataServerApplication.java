package org.gdp.contrast.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author heshiyuan
 * @date 2020/6/15 18:18
 */
@SpringBootApplication
public class DataServerApplication {

    public static final String url = "https://www.douban.com/" ;
    public static final String regexRule = "+http://.*.jianshu.com/.*";
    public static void main(String[] args) {
        new SpringApplication(DataServerApplication.class).run(args);
    }
}
