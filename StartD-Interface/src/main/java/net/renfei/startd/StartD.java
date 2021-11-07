package net.renfei.startd;

import net.renfei.startd.inf.config.StartdBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * StartD程序入口启动类
 *
 * @author renfei
 */
@SpringBootApplication
public class StartD {
    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(StartD.class);
        SpringApplication build = builder.build();
        build.setBanner(new StartdBanner());
        build.run(args);
    }
}
