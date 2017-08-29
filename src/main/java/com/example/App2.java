package com.example;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.ngmon.structlog.StructLog;

public class App2 {
    public static void main(String[] args) throws JsonMappingException {
        StructLog<BlockCacheContext> LOGX = new StructLog<>(BlockCacheContext.class, new SimpleLogger());

        int numCached = 0;
        int neededCached = 0;
        long blockId = 0;
        long datanodeUuid = 0;
        String reason = "reason";

        LOGX.info("...oval for dataNode from PENDING_UNCACHED - it was uncached by the dataNode.")
                .blockId(blockId).dataNodeUuid(datanodeUuid).log();     //Event_2d9cfc60_d9e20513
        LOGX.info("Cannot cache block")
                .blockId(blockId).reason(reason).log();                 //Event_205317be_7dc94af1
        LOGX.info("... for dataNode from PENDING_CACHED - we already have enough cached replicas")
                .blockId(blockId).dataNodeUuid(datanodeUuid).numCached(numCached)
                .neededCached(neededCached).log();                      //Event_dd3ebb4e_f046faee
        LOGX.info("...for dataNode from PENDING_UNCACHED - we do not have enough cached replicas")
                .blockId(blockId).dataNodeUuid(datanodeUuid).numCached(numCached)
                .neededCached(neededCached).log();                      //Event_dd3ebb4e_a7e9c059
        LOGX.info("...ocks - neededCached == 0, and pendingUncached and pendingCached are empty.")
                .blockId(blockId).blockId(blockId + 10).log();          //Event_616b8b66_53bc3e0f
    }
}