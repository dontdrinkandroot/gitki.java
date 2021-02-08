package net.dontdrinkandroot.gitki.service.configuration

interface ConfigurationService {

    val name: String
    val isAnonymousBrowsingEnabled: Boolean
}