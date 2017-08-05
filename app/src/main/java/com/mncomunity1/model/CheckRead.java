package com.mncomunity1.model;

import java.util.List;

/**
 * Created by chonlakantsattaboot on 6/9/2017 AD.
 */

public class CheckRead {

    private boolean result;
    private List<CountBean> count;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<CountBean> getCount() {
        return count;
    }

    public void setCount(List<CountBean> count) {
        this.count = count;
    }

    public static class CountBean {
        /**
         * total : 2
         */

        private String total;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }
    }
}
