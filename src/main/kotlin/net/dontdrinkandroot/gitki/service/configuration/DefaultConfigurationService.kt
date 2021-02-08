package net.dontdrinkandroot.gitki.service.configuration

import net.dontdrinkandroot.gitki.config.GitkiConfigurationProperties
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class DefaultConfigurationService @Inject constructor(configurationProperties: GitkiConfigurationProperties) :
    ConfigurationService {

    override val name: String = configurationProperties.name

    override var isAnonymousBrowsingEnabled: Boolean = configurationProperties.isAnonymousBrowsingEnabled
}