package net.dontdrinkandroot.gitki.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Configuration
public class GitkiConfiguration
{
    //    @Bean
    //    @ConditionalOnProperty("gitki.oauth.github.enabled")
    //    @ConfigurationProperties("gitki.oauth.github.client")
    //    public AuthorizationCodeResourceDetails github()
    //    {
    //        return new AuthorizationCodeResourceDetails();
    //    }
    //
    //    @Bean
    //    @ConditionalOnProperty("gitki.oauth.github.enabled")
    //    @ConfigurationProperties("gitki.oauth.github.resource")
    //    public ResourceServerProperties githubResource()
    //    {
    //        return new ResourceServerProperties();
    //    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
