package net.dontdrinkandroot.gitki.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Configuration
public class GitkiConfiguration
{
    @Bean
    @ConditionalOnProperty("gitki.oauth.github.enabled")
    @ConfigurationProperties("gitki.oauth.github.client")
    public AuthorizationCodeResourceDetails github()
    {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConditionalOnProperty("gitki.oauth.github.enabled")
    @ConfigurationProperties("gitki.oauth.github.resource")
    public ResourceServerProperties githubResource()
    {
        return new ResourceServerProperties();
    }
}
