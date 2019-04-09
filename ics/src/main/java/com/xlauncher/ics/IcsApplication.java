package com.xlauncher.ics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ics重构服务，采用RabbitMQ消息队列作为中间件。
 * @author Administrator
 */
//@SpringBootApplication
//public class IcsApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(IcsApplication.class, args);
//	}
//
//}

@RestController
@EnableAutoConfiguration
@Configuration
@SpringBootApplication
public class IcsApplication  extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(IcsApplication.class, args);
	}

	/**
	 * 需要把web项目打成war包部署到外部tomcat运行时需要改变启动方式
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(IcsApplication.class);
	}

}

