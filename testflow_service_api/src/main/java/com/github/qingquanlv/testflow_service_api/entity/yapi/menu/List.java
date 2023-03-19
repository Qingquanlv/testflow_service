package com.github.qingquanlv.testflow_service_api.entity.yapi.menu;

/**
 * @Author: qingquan.lv
 */
public class List {
        private int edit_uid;
        private String status;
        private int index;
        private java.util.List<Object> tag;
        private long _id;
        private String method;
        private int catid;
        private String title;
        private String path;
        private int project_id;
        private int uid;
        private long add_time;
        private long up_time;
        public void setEdit_uid(int edit_uid) {
                this.edit_uid = edit_uid;
        }
        public int getEdit_uid() {
                return edit_uid;
        }

        public void setStatus(String status) {
                this.status = status;
        }
        public String getStatus() {
                return status;
        }

        public void setIndex(int index) {
                this.index = index;
        }
        public int getIndex() {
                return index;
        }

        public void setTag(java.util.List<Object> tag) {
                this.tag = tag;
        }
        public java.util.List<Object> getTag() {
                return tag;
        }

        public void set_id(long _id) {
                this._id = _id;
        }
        public long get_id() {
                return _id;
        }

        public void setMethod(String method) {
                this.method = method;
        }
        public String getMethod() {
                return method;
        }

        public void setCatid(int catid) {
                this.catid = catid;
        }
        public int getCatid() {
                return catid;
        }

        public void setTitle(String title) {
                this.title = title;
        }
        public String getTitle() {
                return title;
        }

        public void setPath(String path) {
                this.path = path;
        }
        public String getPath() {
                return path;
        }

        public void setProject_id(int project_id) {
                this.project_id = project_id;
        }
        public int getProject_id() {
                return project_id;
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

}
