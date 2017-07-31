package net.dontdrinkandroot.gitki.wicket.resource;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.service.git.GitService;
import net.dontdrinkandroot.gitki.wicket.security.Instantiate;
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils;
import org.apache.wicket.Application;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.PartWriterCallback;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;
import org.apache.wicket.util.time.Time;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Instantiate(value = Role.WATCHER, allowAnonymousIfConfigured = true)
public class RawResource extends AbstractResource
{
    @SpringBean
    private GitService gitService;

    @Override
    protected ResourceResponse newResourceResponse(Attributes attributes)
    {
        FilePath filePath = PageParameterUtils.toFilePath(attributes.getParameters());
        Path fileSystemPath = null;
        try {
            fileSystemPath = this.gitService.resolveAbsolutePath(filePath, true);
        } catch (FileNotFoundException e) {
            throw new AbortWithHttpErrorCodeException(404);
        }

        try {
            BasicFileAttributes fileAttributes = Files.readAttributes(fileSystemPath, BasicFileAttributes.class);
            long size = fileAttributes.size();

            ResourceResponse resourceResponse = new ResourceResponse();
            resourceResponse.setContentType(this.getMimeType(fileSystemPath));
            resourceResponse.setAcceptRange(ContentRangeType.BYTES);
            resourceResponse.setContentLength(size);
            resourceResponse.setLastModified(Time.millis(fileAttributes.lastModifiedTime().toMillis()));
            resourceResponse.setCacheDuration(Duration.milliseconds(1));
            RequestCycle cycle = RequestCycle.get();
            Long startbyte = cycle.getMetaData(CONTENT_RANGE_STARTBYTE);
            Long endbyte = cycle.getMetaData(CONTENT_RANGE_ENDBYTE);
            resourceResponse.setWriteCallback(
                    new PartWriterCallback(Files.newInputStream(fileSystemPath), size, startbyte, endbyte));

            return resourceResponse;
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
    }

    protected String getMimeType(Path path) throws IOException
    {
        String mimeType = null;

        if (Application.exists()) {
            mimeType = Application.get().getMimeType(path.getFileName().toString());
        }

        if (mimeType == null) {
            mimeType = Files.probeContentType(path);
        }

        return mimeType;
    }
}
