package com.joshrotenberg.springbatchimageprocessing.function;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A {@link ResourceAwareItemReaderItemStream} implementation that delegates reads to a {@link Function},
 * and optionally delegates open/update to a {@link Consumer} and close to a {@link Runnable}. Reads can
 * be configured to automatically run once without having to explicitly return null from the read implementation.
 *
 * @param <T> the return type of the read implementation
 */
public class FunctionItemReader<T> implements ResourceAwareItemReaderItemStream<T> {

    private Resource resource;
    private final Function<Resource, T> readFunction;
    private Consumer<ExecutionContext> openFunction;
    private Consumer<ExecutionContext> updateFunction;
    private Runnable closeFunction;
    private Boolean readOnce = false;

    private Boolean read = false;

    /**
     * @param readFunction the read {@link Function} delegate
     */
    public FunctionItemReader(Function<Resource, T> readFunction) {
        Assert.notNull(readFunction, "A read function is required");
        this.readFunction = readFunction;
    }

    /**
     * @param openFunction the open {@link Consumer} delegate
     */
    public void setOpenFunction(Consumer<ExecutionContext> openFunction) {
        this.openFunction = openFunction;
    }

    /**
     * @param updateFunction the update {@link Consumer} delegate
     */
    public void setUpdateFunction(Consumer<ExecutionContext> updateFunction) {
        this.updateFunction = updateFunction;
    }

    /**
     * @param closeFunction the close {@link Runnable} delegate
     */
    public void setCloseFunction(Runnable closeFunction) {
        this.closeFunction = closeFunction;
    }

    /**
     * @param readOnce if set to true, read will only be called once per {@link Resource}. defaults to false
     */
    public void setReadOnce(Boolean readOnce) {
        this.readOnce = readOnce;
    }

    @Override
    public void open(ExecutionContext executionContext) {
        if (openFunction != null) {
            this.openFunction.accept(executionContext);
        }
    }

    @Override
    public void update(ExecutionContext executionContext) {
        if (updateFunction != null) {
            this.updateFunction.accept(executionContext);
        }
    }

    @Override
    public void close() {
        if (closeFunction != null) {
            this.closeFunction.run();
        }
    }

    @Override
    public T read() throws Exception {
        if (readOnce && read) {
            return null;
        }
        if (readOnce && !read) {
            read = true;
        }
        return this.readFunction.apply(this.resource);
    }

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
