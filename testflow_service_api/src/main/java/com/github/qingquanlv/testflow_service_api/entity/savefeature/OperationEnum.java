package com.github.qingquanlv.testflow_service_api.entity.savefeature;

/**
 * @author qingquanlv
 */

public enum OperationEnum {
        ADD(0),

        UPDATE(1),

        DELETE(2);

        private int index;

        OperationEnum(int idx) {
                this.index = idx;
        }

        public int getIndex() {
                return index;
        }
}
