package com.l.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.MultipartConfigElement;


//开启事务管理
@EnableTransactionManagement
@SpringBootApplication
//mapper扫描路径
@ComponentScan(basePackages = "com.l.demo")
@MapperScan(basePackages = "com.l.demo.mapper")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/**
	 * 文件上传配置
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//单个文件最大   10M
		factory.setMaxFileSize("1024000KB"); //KB,MB
		/// 设置总上传数据总大小
		factory.setMaxRequestSize("10240000KB");
		return factory.createMultipartConfig();
	}
}
