package com.github.qingquanlv.testflow_service_api.entity.yapi.menu;

import java.util.List;

/**
 * @Author: qingquan.lv
 */
public class GetMenuResponse {


        private int errcode;
        private String errmsg;
        private List<Data> data;

        public void setErrcode(int errcode) {
                this.errcode = errcode;
        }

        public int getErrcode() {
                return errcode;
        }

        public void setErrmsg(String errmsg) {
                this.errmsg = errmsg;
        }

        public String getErrmsg() {
                return errmsg;
        }

        public void setData(List<Data> data) {
                this.data = data;
        }

        public List<Data> getData() {
                return data;
        }

}
