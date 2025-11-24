package ao.com.mcali;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

@RefreshScope
@SpringBootApplication
@EnableFeignClients(basePackages = "ao.com.mcali.clients")
public class LoanServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(LoanServiceApplication.class, args);
	}
}
