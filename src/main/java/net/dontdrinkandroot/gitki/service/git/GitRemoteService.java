package net.dontdrinkandroot.gitki.service.git;

import net.dontdrinkandroot.gitki.config.GitkiConfigurationProperties;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Service
public class GitRemoteService
{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private GitkiConfigurationProperties.Git.Remote remoteConfiguration;

    private GitService gitService;

    private TaskScheduler taskScheduler;

    protected GitRemoteService()
    {
        /* Reflection Instantiation */
    }

    @Inject
    public GitRemoteService(
            @Value("#{gitkiConfigurationProperties.git.remote}") GitkiConfigurationProperties.Git.Remote remote,
            GitService gitService
    )
    {
        this.remoteConfiguration = remote;
        this.gitService = gitService;
    }

    @Scheduled(fixedDelayString = "${gitki.git.remote.autopull.interval}")
    public void autoPull()
    {
        if (this.remoteConfiguration.getAutopull().isEnabled()) {
            this.logger.info("Autopulling");
            try {
                PullResult pullResult = this.gitService.pull();
                if (pullResult.isSuccessful()) {
                    this.logger.info("Autopull result: success");
                } else {
                    System.err.println(pullResult.getFetchResult());
                }
            } catch (GitAPIException e) {
                this.logger.error("Autopull failed", e);
            }
        }
    }

    @Scheduled(fixedDelayString = "${gitki.git.remote.autopush.interval}")
    public void autoPush()
    {
        if (this.remoteConfiguration.getAutopush().isEnabled()) {
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
}
