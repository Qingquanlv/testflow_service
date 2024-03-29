package com.github.qingquanlv.testflow_service_api.entity.feature_v2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Nodes {

        private Long id;
        private Long x;
        private Long y;
        private String label;
        private String clazz;

        public void setId(Long id) {
                this.id = id;
        }

        public Long getId() {
                return id;
        }

        public void setX(Long x) {
                this.x = x;
        }

        public Long getX() {
                return x;
        }

        public void setY(Long y) {
                this.y = y;
        }

        public Long getY() {
                return y;
        }

        public void setLabel(String label) {
                this.label = label;
        }

        public String getLabel() {
                return label;
        }

        public void setClazz(String clazz) {
                this.clazz = clazz;
        }

        public String getClazz() {
                return clazz;
        }
}
