package com.github.qingquanlv.testflow_service_api.entity.yapi.interf;

/**
 * @Author: qingquan.lv
 */
public class Req_query {

        private String required;
        private String _id;
        private String name;
        private String desc;
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

        public void setDesc(String desc) {
                this.desc = desc;
        }
        public String getDesc() {
                return desc;
        }

}