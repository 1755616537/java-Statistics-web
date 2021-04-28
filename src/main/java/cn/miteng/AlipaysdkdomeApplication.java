package cn.miteng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AlipaysdkdomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlipaysdkdomeApplication.class, args);
	}

}
