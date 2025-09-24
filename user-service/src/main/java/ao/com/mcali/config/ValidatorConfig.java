package ao.com.mcali.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidatorConfig {
    @Bean
    LocalValidatorFactoryBean beanValidator(){
        return new LocalValidatorFactoryBean();
    }
}
