package com.example;

import org.ngmon.structlog.annotation.Var;
import org.ngmon.structlog.annotation.VarContext;
import org.ngmon.structlog.injection.VariableContext;

@VarContext
public class BlockCacheContext extends VariableContext {

    @Var
    public BlockCacheContext blockId(long blockId) {
        this.inject("blockId", blockId);
        return this;
    }

    @Var
    public BlockCacheContext dataNodeUuid(long dataNodeUuid) {
        this.inject("dataNodeUuid", dataNodeUuid);
        return this;
    }

    @Var
    public BlockCacheContext uncachedBy(String uncachedBy) {
        this.inject("uncachedBy", uncachedBy);
        return this;
    }

    @Var
    public BlockCacheContext reason(String reason) {
        this.inject("reason", reason);
        return this;
    }

    @Var
    public BlockCacheContext numCached(int numCached) {
        this.inject("numCached", numCached);
        return this;
    }

    @Var
    public BlockCacheContext neededCached(int neededCached) {
        this.inject("neededCached", neededCached);
        return this;
    }

    @Var
    public BlockCacheContext pendingUncached_isEmpty(boolean pendingUncached_isEmpty) {
        this.inject("pendingUncached_isEmpty", pendingUncached_isEmpty);
        return this;
    }

    @Var
    public BlockCacheContext pendingCached_isEmpty(boolean pendingCached_isEmpty) {
        this.inject("pendingCached_isEmpty", pendingCached_isEmpty);
        return this;
    }
}
