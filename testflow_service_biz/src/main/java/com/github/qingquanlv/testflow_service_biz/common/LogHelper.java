package com.github.qingquanlv.testflow_service_biz.common;

import com.github.qingquanlv.testflow_service_biz.TestFlowManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogHelper {

    public static ThreadLocal<Integer> index = new ThreadLocal<>();
    private static Logger logger = LoggerFactory.getLogger(TestFlowManager.class);

    public static void initLog() {
        index.set(0);
    }

    public static void deposeLog() {
        index.set(0);
    }

    public static void stepExecLog(String stepName, String...stepParam) throws Exception {
        index.set(index.get() + 1);
        String param = "";
        for (int i=0;i<stepParam.length;i++) {
            param +=stepParam[i] + ",";
        }
        logger.info(String.format("*********************************************************************************************************************************************************************************************************"));
        String log = String.format("Start execute %s step: step Name \'%s\' step Param \'%s\'.", index.get(), stepName, param);
        logger.info(log);
        BufferManager.appendBufferByKey("tf_log", log);
    }

    public static void stepAfterLog(String stepName, String buffer) {
        String log = String.format("Step %s Passed: buffer key\'%s\' buffer \'%s\'.", index.get(), stepName, buffer);
        logger.info(log);
        try {
            BufferManager.appendBufferByKey("tf_log", log);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
