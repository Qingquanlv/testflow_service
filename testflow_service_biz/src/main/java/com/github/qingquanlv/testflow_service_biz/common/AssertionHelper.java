package com.github.qingquanlv.testflow_service_biz.common;

/**
 * @Author: qingquan.lv
 */
public class AssertionHelper {

        public static void assertion(String error)  {
                try {
                        BufferManager.appendBufferByKey("tf_assertion", error);
                }
                catch (Exception ex)
                {
                        System.out.println(ex.getMessage());
                }
                throw new AssertionError(error);
        }
}
