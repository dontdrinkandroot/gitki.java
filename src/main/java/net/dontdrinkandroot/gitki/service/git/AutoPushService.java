package net.dontdrinkandroot.gitki.service.git;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Service
@ConditionalOnProperty("gitki.autopush")
public class AutoPushService
{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private GitService gitService;

    protected AutoPushService()
    {
        /* Reflection Instantiation */
    }

    @Inject
    public AutoPushService(GitService gitService)
    {
        this.gitService = gitService;
    }

    @Scheduled(fixedDelayString = "${gitki.autopush}")
    public void autoPush()
    {
        this.logger.info("Autopushing");
        try {
            Iterable<PushResult> pushResults = this.gitService.push();
            for (PushResult pushResult : pushResults) {
                Collection<RemoteRefUpdate> remoteUpdates = pushResult.getRemoteUpdates();
                for (RemoteRefUpdate remoteUpdate : remoteUpdates) {
                    RemoteRefUpdate.Status status = remoteUpdate.getStatus();
                    if (status.equals(RemoteRefUpdate.Status.OK) || status.equals(RemoteRefUpdate.Status.UP_TO_DATE)) {
                        this.logger.info("Autopush result: " + remoteUpdate.getStatus());
                    } else {
                        this.logger.error("Autopush result: " + remoteUpdate.getStatus());
                    }
                }
            }
        } catch (GitAPIException e) {
            this.logger.error("Autopush failed", e);
        }
    }
}
