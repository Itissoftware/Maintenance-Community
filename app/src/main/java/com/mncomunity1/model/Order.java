package com.mncomunity1.model;

import java.util.List;

/**
 * Created by chonlakantsattaboot on 5/25/2017 AD.
 */

public class Order {


    /**
     * result : true
     * total : [{"id":"50","nameth":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก.","userName":"jngabell","company_code":"00004","status":"0","ts":"2017-07-19"}]
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
         * id : 50
         * nameth : โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก.
         * userName : jngabell
         * company_code : 00004
         * status : 0
         * ts : 2017-07-19
         */

        private String id;
        private String nameth;
        private String userName;
        private String company_code;
        private String status;
        private String ts;

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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCompany_code() {
            return company_code;
        }

        public void setCompany_code(String company_code) {
            this.company_code = company_code;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTs() {
            return ts;
        }

        public void setTs(String ts) {
            this.ts = ts;
        }
    }
}
