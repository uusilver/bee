package org.tmind.bee; /**
 * @COPYRIGHT (C) 2017 Schenker AG
 * <p>
 * All rights reserved
 */


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

/**
 * TODO The class main is supposed to be documented...
 *
 * @author Vani Li
 */
@SpringBootApplication
public class main {

    public static void main(String[] args) {
        SpringApplication.run(main.class, args);
    }

    @Bean
    public TemplateResolver templateResolver() {
        TemplateResolver tr = new ClassLoaderTemplateResolver();
        tr.setPrefix("templates/");  // 注意template/前面没有"/"，也不要加"classpath:"，
        // 否则如果用mvn启动会找不到模板（通过目录读class找不到，通过读jar可找到，即用java -jar正常）
        tr.setSuffix(".html");
        tr.setTemplateMode("HTML5");
        tr.setCharacterEncoding("UTF-8");
        tr.setOrder(0);
        return tr;
    }

    // static class
    // static constants
    // static method
    // instance data
    // constructor
    // public
    // private
    // protected
}
