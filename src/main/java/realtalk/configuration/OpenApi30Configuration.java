package realtalk.configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

import static io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER;

/**
 * Конфиг для сваггера, для контроллеров с @AuthenticationPrincipal нужно навешивать @SecurityRequirement(name = "Bearer Authentication")
 */
@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        in = HEADER
)
public class OpenApi30Configuration {
}
