package com.github.qingquanlv.testflow_service_api.entity.yapi.interf;

/**
 * @Author: qingquan.lv
 */
public class Req_headers {

        private String required;
        private String _id;
        private String name;
        private String value;
        public void setRequired(String required) {
                this.required = required;
        }
        public String getRequired() {
                return required;
        }

        public void set_id(String _id) {
                this._id = _id;
        }
        public String get_id() {
                return _id;
        }

        public void setName(String name) {
                this.name = name;
        }
        public String getName() {
                return name;
        }

        public void setValue(String value) {
                this.value = value;
        }
        public String getValue() {
                return value;
        }

}
