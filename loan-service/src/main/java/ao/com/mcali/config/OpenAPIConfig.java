package ao.com.mcali.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


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
                        .title("Loan service API")
                        .description("API para gestão de emprestimos de livros na biblioteca")
                        .version(versao)
                        .contact(new Contact().name("Moisés Calinhiqui").email("moisesccalinhiqui@gmail.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));

    }
}
