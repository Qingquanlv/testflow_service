package com.github.qingquanlv.testflow_service_api.entity.yapi.menu;

import java.util.List;

/**
 * @Author: qingquan.lv
 */

public class Data {
        private int index;
        private int _id;
        private String name;
        private int project_id;
        private String desc;
        private int uid;
        private long add_time;
        private long up_time;
        private int __v;
        private List<com.github.qingquanlv.testflow_service_api.entity.yapi.menu.List> list;
        public void setIndex(int index) {
                this.index = index;
        }
        public int getIndex() {
                return index;
        }

        public void set_id(int _id) {
                this._id = _id;
        }
        public int get_id() {
                return _id;
        }

        public void setName(String name) {
                this.name = name;
        }
        public String getName() {
                return name;
        }

        public void setProject_id(int project_id) {
                this.project_id = project_id;
        }
        public int getProject_id() {
                return project_id;
        }

        public void setDesc(String desc) {
                this.desc = desc;
        }
        public String getDesc() {
                return desc;
        }

        public void setUid(int uid) {
                this.uid = uid;
        }
        public int getUid() {
                return uid;
        }

        public void setAdd_time(long add_time) {
                this.add_time = add_time;
        }
        public long getAdd_time() {
                return add_time;
        }

        public void setUp_time(long up_time) {
                this.up_time = up_time;
        }
        public long getUp_time() {
                return up_time;
        }

        public void set__v(int __v) {
                this.__v = __v;
        }
        public int get__v() {
                return __v;
        }

        public void setList(List<com.github.qingquanlv.testflow_service_api.entity.yapi.menu.List> list) {
                this.list = list;
        }
        public List<com.github.qingquanlv.testflow_service_api.entity.yapi.menu.List> getList() {
                return list;
        }

}
