package com.xuecheng.content;

import com.spring4all.swagger.EnableSwagger2Doc;
import com.xuecheng.base.execption.GlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@EnableSwagger2Doc
@SpringBootApplication()
@Import(GlobalExceptionHandler.class)
public class XuechengPlusContentApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(XuechengPlusContentApiApplication.class, args);

    }

}
