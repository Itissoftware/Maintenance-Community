package com.mncomunity1.model;

import java.util.List;

/**
 * Created by chonlakantsattaboot on 5/25/2017 AD.
 */

public class DetailsVendor {

    /**
     * result : true
     * total : [{"id":"84","nameth":"หม้อต้มน้ำมันร้อน","total":"10","company_code":"00077","ts":"2017-07-26","status":"รอยืนยัน"}]
     */

    private boolean result;
    private List<TotalBean> total;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<TotalBean> getTotal() {
        return total;
    }

    public void setTotal(List<TotalBean> total) {
        this.total = total;
    }

    public static class TotalBean {
        /**
         * id : 84
         * nameth : หม้อต้มน้ำมันร้อน
         * total : 10
         * company_code : 00077
         * ts : 2017-07-26
         * status : รอยืนยัน
         */

        private String id;
        private String nameth;
        private String total;
        private String company_code;
        private String ts;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNameth() {
            return nameth;
        }

        public void setNameth(String nameth) {
            this.nameth = nameth;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getCompany_code() {
            return company_code;
        }

        public void setCompany_code(String company_code) {
            this.company_code = company_code;
        }

        public String getTs() {
            return ts;
        }

        public void setTs(String ts) {
            this.ts = ts;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
