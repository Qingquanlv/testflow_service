package com.github.qingquanlv.testflow_service_api.entity.yapi.interf;

/**
 * @Author: qingquan.lv
 */
public class GetInterfaceResponse {

        private int errcode;
        private String errmsg;
        private Data data;
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

        public void setData(Data data) {
                this.data = data;
        }
        public Data getData() {
                return data;
        }

}
