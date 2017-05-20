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
                .blockId(blockId).dataNodeUuid(datanodeUuid).log();//Event_N1656664914_P657257274
        LOGX.info("Cannot cache block")
                .blockId(blockId).reason(reason).log();            //Event_P1920713228_P1300017906
        LOGX.info("... for dataNode from PENDING_CACHED - we already have enough cached replicas")
                .blockId(blockId).dataNodeUuid(datanodeUuid).numCached(numCached)
                .neededCached(neededCached).log();                 //Event_P545087615_N1858460731
        LOGX.info("...for dataNode from PENDING_UNCACHED - we do not have enough cached replicas")
                .blockId(blockId).dataNodeUuid(datanodeUuid).numCached(numCached)
                .neededCached(neededCached).log();                 //Event_P545087615_P609856424
        LOGX.info("...ocks - neededCached == 0, and pendingUncached and pendingCached are empty.")
                .blockId(blockId).blockId(blockId + 10).log();     //Event_P304007109_N1938941448
    }
}