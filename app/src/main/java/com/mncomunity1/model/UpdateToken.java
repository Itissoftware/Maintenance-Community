package com.mncomunity1.model;

import java.util.List;

/**
 * Created by Bas on 7/20/2017 AD.
 */

public class UpdateToken {


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
