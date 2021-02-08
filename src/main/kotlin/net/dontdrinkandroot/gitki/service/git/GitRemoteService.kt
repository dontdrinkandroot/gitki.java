package net.dontdrinkandroot.gitki.service.git

import net.dontdrinkandroot.gitki.config.GitkiConfigurationProperties
import org.eclipse.jgit.api.errors.GitAPIException
import org.eclipse.jgit.transport.RemoteRefUpdate
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class GitRemoteService @Inject constructor(
    @Value("#{gitkiConfigurationProperties.git.remote}") val remoteConfiguration: GitkiConfigurationProperties.Git.Remote,
    private val gitService: GitService
) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Scheduled(fixedDelayString = "\${gitki.git.remote.autopull.interval}")
    fun autoPull() {
        if (remoteConfiguration.autopull.isEnabled) {
            logger.info("Autopulling")
            try {
                val pullResult = gitService.pull()
                if (pullResult.isSuccessful) {
                    logger.info(
                        String.format(
                            "Autopull result: %s",
                            pullResult.mergeResult.mergeStatus
                        )
                    )
                } else {
                    logger.error(
                        String.format(
                            "Autopull result: %s",
                            pullResult.mergeResult.mergeStatus
                        )
                    )
                }
            } catch (e: GitAPIException) {
                logger.error("Autopull failed", e)
            }
        }
    }

    @Scheduled(fixedDelayString = "\${gitki.git.remote.autopush.interval}")
    fun autoPush() {
        if (remoteConfiguration.autopush.isEnabled) {
            logger.info("Autopushing")
            try {
                val pushResults = gitService.push()
                for (pushResult in pushResults) {
                    val remoteUpdates = pushResult.remoteUpdates
                    for (remoteUpdate in remoteUpdates) {
                        val status = remoteUpdate.status
                        if (status == RemoteRefUpdate.Status.OK || status == RemoteRefUpdate.Status.UP_TO_DATE) {
                            logger.info("Autopush result: " + remoteUpdate.status)
                        } else {
                            logger.error("Autopush result: " + remoteUpdate.status)
                        }
                    }
                }
            } catch (e: GitAPIException) {
                logger.error("Autopush failed", e)
            }
        }
    }
}