package com.github.qingquanlv.testflow_service_biz.common;

/**
 * @Author: qingquan.lv
 * @Date: 2021/4/7 12:21
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
