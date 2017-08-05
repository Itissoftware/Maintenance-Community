package com.mncomunity1.model;

import java.util.List;

/**
 * Created by chonlakantsattaboot on 4/20/2017 AD.
 */

public class Register {

    private String success;
    private List<CompleteBean> complete;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<CompleteBean> getComplete() {
        return complete;
    }

    public void setComplete(List<CompleteBean> complete) {
        this.complete = complete;
    }

    public static class CompleteBean {
        /**
         * code : 113
         * nameth : Chonlakant
         * email : 777@gmail.com
         * regid : d7DVuCJT1VU:APA91bFneqbfjJ0jMWG9yfO5lH7hsYz6NTCGGUVaUOFbU_HVdWuGx1uSbOQsyioZqSHSfD2Y1Hwd-9rzfVbq_n4eaSFe4LBh4-lMjJSg0W-49oFtUaXQ6E2LKeCDdp_v7ohkHRzb9fB0
         * company_code : 00085
         * check_status : 0
         * statusVendor : 0
         */

        private String code;
        private String nameth;
        private String email;
        private String regid;
        private String company_code;
        private String check_status;
        private String statusVendor;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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

        public String getRegid() {
            return regid;
        }

        public void setRegid(String regid) {
            this.regid = regid;
        }

        public String getCompany_code() {
            return company_code;
        }

        public void setCompany_code(String company_code) {
            this.company_code = company_code;
        }

        public String getCheck_status() {
            return check_status;
        }

        public void setCheck_status(String check_status) {
            this.check_status = check_status;
        }

        public String getStatusVendor() {
            return statusVendor;
        }

        public void setStatusVendor(String statusVendor) {
            this.statusVendor = statusVendor;
        }
    }
}
