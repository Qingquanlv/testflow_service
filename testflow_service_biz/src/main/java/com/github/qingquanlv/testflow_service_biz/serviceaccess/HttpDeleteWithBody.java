package com.github.qingquanlv.testflow_service_biz.serviceaccess;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

/**
 * @Author: qingquan.lv
 */
public class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {


        public static final String METHOD_NAME = "DELETE";

        /**
         * 获取方法（必须重载）
         *
         * @return
         */
        @Override
        public String getMethod() {
                return METHOD_NAME;
        }

        public HttpDeleteWithBody(final String uri) {
                super();
                setURI(URI.create(uri));
        }

        public HttpDeleteWithBody(final URI uri) {
                super();
                setURI(uri);
        }

        public HttpDeleteWithBody() {
                super();
        }
}
