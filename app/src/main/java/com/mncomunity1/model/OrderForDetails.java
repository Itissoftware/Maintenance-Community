package com.mncomunity1.model;

import java.util.List;

/**
 * Created by Bas on 7/26/2017 AD.
 */

public class OrderForDetails {


    /**
     * result : true
     * nameth : ทททท
     * email : 888@gmail.com
     * address : 68/301 ซอยรามคำแหง 164
     ถนนรามคำแหง แขวงมีนบุรี
     เขตมีนบุรี กทม.10510
     * nameCom : IS SOFTWARE MC
     * phone : 0910833820
     * total : [{"id":"84","nameth":"EcoBond Cartridge","total":"44","company_code":"00004","ts":"2017-07-25"},{"id":"84","nameth":"ผ้ากรอง","total":"120","company_code":"00004","ts":"2017-07-25"}]
     */

    private boolean result;
    private String nameth;
    private String email;
    private String address;
    private String nameCom;
    private String phone;
    private List<TotalBean> total;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getNameth() {
        return nameth;
    }

    public void setNameth(String nameth) {
        this.nameth = nameth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNameCom() {
        return nameCom;
    }

    public void setNameCom(String nameCom) {
        this.nameCom = nameCom;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
         * nameth : EcoBond Cartridge
         * total : 44
         * company_code : 00004
         * ts : 2017-07-25
         */

        private String id;
        private String nameth;
        private String total;
        private String company_code;
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
    }
}
