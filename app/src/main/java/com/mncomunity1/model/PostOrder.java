package com.mncomunity1.model;

import java.util.List;

/**
 * Created by chonlakantsattaboot on 4/17/2017 AD.
 */

public class PostOrder {


    private List<CompleteBean> complete;

    public List<CompleteBean> getComplete() {
        return complete;
    }

    public void setComplete(List<CompleteBean> complete) {
        this.complete = complete;
    }

    public static class CompleteBean {
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
