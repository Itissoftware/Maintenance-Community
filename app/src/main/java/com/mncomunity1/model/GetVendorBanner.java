package com.mncomunity1.model;

import java.util.List;

/**
 * Created by Bas on 7/27/2017 AD.
 */


public class GetVendorBanner {


    /**
     * result : true
     * total : [{"id":"1","img_path":"photo/img-80caa6b38a0336a2f180125273df41ed.png","company_code":"00004","company_name":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก."}]
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
         * id : 1
         * img_path : photo/img-80caa6b38a0336a2f180125273df41ed.png
         * company_code : 00004
         * company_name : โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก.
         */

        private String id;
        private String img_path;
        private String company_code;
        private String company_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
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
