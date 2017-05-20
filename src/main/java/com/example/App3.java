package com.example;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.ngmon.structlog.StructLog;

public class App3 {
    public static void main(String[] args) throws JsonMappingException {
        StructLog<BlockCacheContext> LOGX = new StructLog<>(BlockCacheContext.class, new SimpleLogger());

        int numCached = 0;
        int neededCached = 0;
        long blockId = 0;
        long datanodeUuid = 0;
        String reason = "reason";

        LOGX.info("Block removal for dataNode from PENDING_UNCACHED - it was uncached by the dataNode.").blockId(blockId).dataNodeUuid(datanodeUuid).log();
        LOGX.info("Cannot cache block").blockId(blockId).reason(reason).log();
        LOGX.info("Block removal for dataNode from PENDING_CACHED - we already have enough cached replicas").blockId(blockId).dataNodeUuid(datanodeUuid).numCached(numCached).neededCached(neededCached).log();
        LOGX.info("Block removal for dataNode from PENDING_UNCACHED - we do not have enough cached replicas").blockId(blockId).dataNodeUuid(datanodeUuid).numCached(numCached).neededCached(neededCached).log();
        LOGX.info("Block removal for dataNode from cachedBlocks - neededCached == 0, and pendingUncached and pendingCached are empty.").blockId(blockId).log();
    }
}