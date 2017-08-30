package com.mncomunity1.model;

import java.util.List;

/**
 * Created by Bas on 8/24/2017 AD.
 */

public class QuestionListVendor {


    /**
     * result : null
     * total : [{"userid":"192","id_com":"120","nameth":"บริษัท ทรัมพ์โพ เอ็นเตอร์ไพร์ส (ประเทศไทย) จำกัด","price":"200","address":"1040/20-21 ซอยสุขุมวิท 44/2 ต. พระโขนง อ. คลองเตย จ. กรุงเทพมหานคร 10110  Basic Information [ EN ] Chumpo Enterprise (Thailand) Co., Ltd. 1040 / 20-21 Soi Sukhumvit 44/2 in. Phra Khanong district Klon","img_path":"photo/logo_9964_51f1eb8ac13340.69152121.jpg","timevender":"2017-08-29"}]
     */

    private Object result;
    private List<TotalBean> total;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
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
         * userid : 192
         * id_com : 120
         * nameth : บริษัท ทรัมพ์โพ เอ็นเตอร์ไพร์ส (ประเทศไทย) จำกัด
         * price : 200
         * address : 1040/20-21 ซอยสุขุมวิท 44/2 ต. พระโขนง อ. คลองเตย จ. กรุงเทพมหานคร 10110  Basic Information [ EN ] Chumpo Enterprise (Thailand) Co., Ltd. 1040 / 20-21 Soi Sukhumvit 44/2 in. Phra Khanong district Klon
         * img_path : photo/logo_9964_51f1eb8ac13340.69152121.jpg
         * timevender : 2017-08-29
         */

        private String userid;
        private String id_com;
        private String nameth;
        private String price;
        private String address;
        private String img_path;
        private String timevender;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getId_com() {
            return id_com;
        }

        public void setId_com(String id_com) {
            this.id_com = id_com;
        }

        public String getNameth() {
            return nameth;
        }

        public void setNameth(String nameth) {
            this.nameth = nameth;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }

        public String getTimevender() {
            return timevender;
        }

        public void setTimevender(String timevender) {
            this.timevender = timevender;
        }
    }
}
