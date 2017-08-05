package com.mncomunity1.model;

import java.util.List;

/**
 * Created by Bas on 7/25/2017 AD.
 */

public class OrderVendor {


    /**
     * result : true
     * total : [{"id":"37","nameth":"ถิรายุ","company_code":"00006","company_name":"โทนัน อาเชีย ออโต้เทค บจก."},{"id":"84","nameth":"ทททท","company_code":"00085","company_name":"เวิลด์ แทร็คเตอร์ (1996) บจก."}]
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
         * id : 37
         * nameth : ถิรายุ
         * company_code : 00006
         * company_name : โทนัน อาเชีย ออโต้เทค บจก.
         */

        private String id;
        private String nameth;
        private String company_code;
        private String company_name;

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

        public String getCompany_code() {
            return company_code;
        }

        public void setCompany_code(String company_code) {
            this.company_code = company_code;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }
    }
}
