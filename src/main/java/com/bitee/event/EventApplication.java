package com.bitee.event;

import com.bitee.event.Config.SpringSecurityAuditorAware;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

//@OpenAPIDefinition(
//		info = @Info(
//				title = "Event Wave Backend",
//				description = "Backend Rest Apis for EW",
//				version = "v1.0",
//				contact = @Contact(
//						name = "Caleb Bitiyong",
//						email = "calebduniya45@gmail.com",
//						url = "https://github.com/Bitee-cd"
//				),
//				license = @License(
//						name = "Caleb Bitiyong",
//						url = "https://github.com/Bitee-cd"
//				)
//		),
//		externalDocs = @ExternalDocumentation(
//				description = "Event Wave",
//				url = "https://github.com/Bitee-cd"
//		)
//)

@EnableScheduling
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class EventApplication {

	@Bean
	public AuditorAware<String> auditorAware(){
		return new SpringSecurityAuditorAware();
	}
	public static void main(String[] args) {
		SpringApplication.run(EventApplication.class, args);
	}

}
