package com.github.qingquanlv.testflow_service_api.entity.yapi.interf;

import java.util.List;

/**
 * @Author: qingquan.lv
 */
public class Query_path {

        private String path;
        private List<String> params;
        public void setPath(String path) {
                this.path = path;
        }
        public String getPath() {
                return path;
        }

        public void setParams(List<String> params) {
                this.params = params;
        }
        public List<String> getParams() {
                return params;
        }

}
