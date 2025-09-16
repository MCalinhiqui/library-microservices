package ao.com.mcali.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;


/**
 *
 * @author mcalinhiqui
 */
@Configuration
public class OpenAPIConfig {

    @Bean
    OpenAPI openAPI(@Value("${application-version}") String versao) {
        return new OpenAPI()
                .info(new Info()
                        .title("Book service API")
                        .description("API para gestão dos livros na biblioteca")
                        .version(versao)
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                        .contact(new Contact().name("Moisés Calinhiqui").email("moisesccalinhiqui@gmail.com")));

    }
}
