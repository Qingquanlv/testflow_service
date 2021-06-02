package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import java.sql.Timestamp;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/2 20:07
 */
public class Task {
        /**
         * task_id
         */
        private Long task_id;
        /**
         * task_name
         */
        private String task_name;
        /**
         * task_starttime
         */
        private Timestamp task_starttime;
        /**
         * datachanged_lasttime
         */
        private Timestamp datachanged_lasttime;

        public Long getTask_id() {
                return task_id;
        }

        public void setTask_id(Long task_id) {
                this.task_id = task_id;
        }

        public String getTask_name() {
                return task_name;
        }

        public void setTask_name(String task_name) {
                this.task_name = task_name;
        }

        public Timestamp getTask_starttime() {
                return task_starttime;
        }

        public void setTask_starttime(Timestamp task_starttime) {
                this.task_starttime = task_starttime;
        }

        public Timestamp getDatachanged_lasttime() {
                return datachanged_lasttime;
        }

        public void setDatachanged_lasttime(Timestamp datachanged_lasttime) {
                this.datachanged_lasttime = datachanged_lasttime;
        }
}

