package net.dontdrinkandroot.gitki.config

import net.dontdrinkandroot.gitki.wicket.component.bspanel.index.IndexFilePanel
import net.dontdrinkandroot.gitki.wicket.page.file.edit.EditPage
import net.dontdrinkandroot.gitki.wicket.page.file.view.ViewPage
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import java.util.*
import javax.validation.constraints.NotEmpty

@Component
@Validated
@ConfigurationProperties(prefix = GitkiConfigurationProperties.PROPERTY_PREFIX)
class GitkiConfigurationProperties {

    var name: @NotEmpty String = "GitKi"
    val git = Git()
    var isAnonymousBrowsingEnabled = true
    var viewMappings: Map<String, Class<out ViewPage?>> = HashMap()
    var editMappings: Map<String, Class<out EditPage?>> = HashMap()
    var indexFiles: List<String> = ArrayList()
    var indexFileMappings: Map<String, Class<out IndexFilePanel?>> = HashMap()

    class Git {

        var repository: @NotEmpty String? = null
        val remote = Remote()

        class Remote {

            val autopush = Autopush()
            val autopull = Autopull()

            class Autopush {

                var isEnabled = false
                var interval = 300000L
            }

            class Autopull {

                var isEnabled = false
                var interval = 300000L
            }
        }
    }

    companion object {

        const val PROPERTY_PREFIX = "gitki"
    }
}