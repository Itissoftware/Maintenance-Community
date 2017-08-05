package com.mncomunity1.model;

import java.util.List;

/**
 * Created by chonlakantsattaboot on 4/18/2017 AD.
 */

public class Delete {


    private List<TotalBean> total;

    public List<TotalBean> getTotal() {
        return total;
    }

    public void setTotal(List<TotalBean> total) {
        this.total = total;
    }

    public static class TotalBean {
        /**
         * status : 1
         */

        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
