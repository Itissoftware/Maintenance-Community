package com.mncomunity1.model;

import java.util.List;

/**
 * Created by Bas on 7/26/2017 AD.
 */

public class Profile {


    /**
     * result : true
     * total : [{"id":"84","nameth":"ทททท","pass":"123456","email":"888@gmail.com","phone":"0910833820","address":"68/301 ซอยรามคำแหง 164\nถนนรามคำแหง แขวงมีนบุรี \nเขตมีนบุรี กทม.10510"}]
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
         * nameth : ทททท
         * pass : 123456
         * email : 888@gmail.com
         * phone : 0910833820
         * address : 68/301 ซอยรามคำแหง 164
         ถนนรามคำแหง แขวงมีนบุรี
         เขตมีนบุรี กทม.10510
         */

        private String id;
        private String nameth;
        private String pass;
        private String email;
        private String phone;
        private String address;

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

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
