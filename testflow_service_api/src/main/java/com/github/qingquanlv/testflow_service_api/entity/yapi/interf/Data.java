package com.github.qingquanlv.testflow_service_api.entity.yapi.interf;

import java.util.List;

/**
 * @Author: qingquan.lv
 */
public class Data {


        private Query_path query_path;
        private int edit_uid;
        private String status;
        private String type;
        private boolean req_body_is_json_schema;
        private boolean res_body_is_json_schema;
        private boolean api_opened;
        private int index;
        private List<String> tag;
        private int _id;
        private String method;
        private String title;
        private String path;
        private List<String> req_params;
        private List<String> req_body_form;
        private List<Req_headers> req_headers;
        private List<Req_query> req_query;
        private String req_body_type;
        private String res_body_type;
        private String res_body;
        private String req_body_other;
        private int project_id;
        private int catid;
        private int uid;
        private long add_time;
        private long up_time;
        private int __v;
        private String markdown;
        private String desc;
        private String username;

        public void setQuery_path(Query_path query_path) {
                this.query_path = query_path;
        }

        public Query_path getQuery_path() {
                return query_path;
        }

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

        public void setType(String type) {
                this.type = type;
        }

        public String getType() {
                return type;
        }

        public void setReq_body_is_json_schema(boolean req_body_is_json_schema) {
                this.req_body_is_json_schema = req_body_is_json_schema;
        }

        public boolean getReq_body_is_json_schema() {
                return req_body_is_json_schema;
        }

        public void setRes_body_is_json_schema(boolean res_body_is_json_schema) {
                this.res_body_is_json_schema = res_body_is_json_schema;
        }

        public boolean getRes_body_is_json_schema() {
                return res_body_is_json_schema;
        }

        public void setApi_opened(boolean api_opened) {
                this.api_opened = api_opened;
        }

        public boolean getApi_opened() {
                return api_opened;
        }

        public void setIndex(int index) {
                this.index = index;
        }

        public int getIndex() {
                return index;
        }

        public void setTag(List<String> tag) {
                this.tag = tag;
        }

        public List<String> getTag() {
                return tag;
        }

        public void set_id(int _id) {
                this._id = _id;
        }

        public int get_id() {
                return _id;
        }

        public void setMethod(String method) {
                this.method = method;
        }

        public String getMethod() {
                return method;
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

        public void setReq_params(List<String> req_params) {
                this.req_params = req_params;
        }

        public List<String> getReq_params() {
                return req_params;
        }

        public void setReq_body_form(List<String> req_body_form) {
                this.req_body_form = req_body_form;
        }

        public List<String> getReq_body_form() {
                return req_body_form;
        }

        public void setReq_headers(List<Req_headers> req_headers) {
                this.req_headers = req_headers;
        }

        public List<Req_headers> getReq_headers() {
                return req_headers;
        }

        public void setReq_query(List<Req_query> req_query) {
                this.req_query = req_query;
        }

        public List<Req_query> getReq_query() {
                return req_query;
        }

        public void setReq_body_type(String req_body_type) {
                this.req_body_type = req_body_type;
        }

        public String getReq_body_type() {
                return req_body_type;
        }

        public void setRes_body_type(String res_body_type) {
                this.res_body_type = res_body_type;
        }

        public String getRes_body_type() {
                return res_body_type;
        }

        public void setRes_body(String res_body) {
                this.res_body = res_body;
        }

        public String getRes_body() {
                return res_body;
        }

        public void setReq_body_other(String req_body_other) {
                this.req_body_other = req_body_other;
        }

        public String getReq_body_other() {
                return req_body_other;
        }

        public void setProject_id(int project_id) {
                this.project_id = project_id;
        }

        public int getProject_id() {
                return project_id;
        }

        public void setCatid(int catid) {
                this.catid = catid;
        }

        public int getCatid() {
                return catid;
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

        public void setMarkdown(String markdown) {
                this.markdown = markdown;
        }

        public String getMarkdown() {
                return markdown;
        }

        public void setDesc(String desc) {
                this.desc = desc;
        }

        public String getDesc() {
                return desc;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getUsername() {
                return username;
        }

}