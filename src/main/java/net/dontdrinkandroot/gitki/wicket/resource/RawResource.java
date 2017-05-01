package net.dontdrinkandroot.gitki.wicket.resource;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.service.git.GitService;
import net.dontdrinkandroot.gitki.wicket.security.Instantiate;
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.resource.FileSystemResource;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.nio.file.Path;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Instantiate(value = Role.WATCHER, allowAnonymous = true)
public class RawResource extends FileSystemResource
{
    @Inject
    private GitService gitService;

    @Override
    protected ResourceResponse newResourceResponse(Attributes attributes)
    {
        FilePath filePath = PageParameterUtils.toFilePath(attributes.getParameters());
        Path fileSystemPath = null;
        try {
            fileSystemPath = this.gitService.resolve(filePath.toPath(), true);
        } catch (FileNotFoundException e) {
            throw new AbortWithHttpErrorCodeException(404);
        }

        return this.createResourceResponse(fileSystemPath);
    }
}
