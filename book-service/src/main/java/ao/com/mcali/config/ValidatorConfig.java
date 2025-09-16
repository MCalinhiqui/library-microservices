package ao.com.mcali.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import jakarta.validation.Validator;

/**
 *
 * @author mcalinhiqui
 */
@Configuration
public class ValidatorConfig {

    @Bean
    Validator validation() {
        return new LocalValidatorFactoryBean();
    }
}
