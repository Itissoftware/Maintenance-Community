package com.mncomunity1.model;

import java.util.List;


public class getOrder {


    /**
     * result : true
     * total : [{"id":"1","nameth":"vvv","code":"","total":"1","image":"http://mn-community.com/mc_app/photo/test.jpg","company_code":"00004"},{"id":"1","nameth":"motor","code":"","total":"1","image":"http://mn-community.com/mc_app/photo/download.jpeg","company_code":"00004"}]
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
         * nameth : vvv
         * code :
         * total : 1
         * image : http://mn-community.com/mc_app/photo/test.jpg
         * company_code : 00004
         */

        private String id_list;
        private String id;
        private String nameth;
        private String code;
        private String total;
        private String image;
        private String company_code;


        public String getId_list() {
            return id_list;
        }

        public void setId_list(String id_list) {
            this.id_list = id_list;
        }

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

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCompany_code() {
            return company_code;
        }

        public void setCompany_code(String company_code) {
            this.company_code = company_code;
        }
    }
}
