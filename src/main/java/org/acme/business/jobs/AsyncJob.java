package org.acme.business.jobs;

import java.util.concurrent.CompletionStage;

public interface AsyncJob<T> extends Job {

    CompletionStage<T> doAsyncJob();

}
