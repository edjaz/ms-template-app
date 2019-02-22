package fr.edjaz.backoffice.config;

import fr.edjaz.socle.config.JHipsterProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Backoffice.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

}
