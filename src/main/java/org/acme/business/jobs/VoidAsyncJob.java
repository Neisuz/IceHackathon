package org.acme.business.jobs;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public interface VoidAsyncJob extends AsyncJob<Void> {

    @Override
    default CompletionStage<Void> doAsyncJob() {
        return CompletableFuture.supplyAsync(() -> {
            doJob();
            return null;
        });
    }

}
