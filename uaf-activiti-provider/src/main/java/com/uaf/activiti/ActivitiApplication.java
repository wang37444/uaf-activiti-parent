package com.uaf.activiti;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.uaf.log.MySlf4j;

/**
 * @filename ActivitiApplication
 * @description 工作流引擎启动类
 * @autor 王承
 * @date 2019/11/28 18:40
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ActivitiApplication implements CommandLineRunner {

	/**
	 * 工作流引擎服务启动类方法
	 * @param args  参数
	 * @author 王承
	 * @date 2019/11/28 18:40
	 */
	public static void main(String[] args) {
		SpringApplication.run(ActivitiApplication.class, args);
	}

	/**
	 * 启动完成后执行
	 * @param args 参数
	 * @return void
	 * @author 王承
	 * @date 2019/11/28 18:40
	 */
	@Override
	public void run(String... args) throws Exception {
		MySlf4j.textInfo("工作流引擎服务启动成功....");
	}

}
