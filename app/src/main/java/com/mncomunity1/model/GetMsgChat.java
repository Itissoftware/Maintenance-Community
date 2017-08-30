package com.mncomunity1.model;

import java.util.List;

/**
 * Created by Bas on 8/25/2017 AD.
 */

public class GetMsgChat {


    /**
     * result : 1
     * total : [{"id":"4","bit_vender":"6","message":"à¸\u2014à¸\u201dà¸ªà¸­à¸š","image":"url","create_date":"2017-08-25 10:25:56","sender":"192"}]
     */

    private String result;
    private List<TotalBean> total;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
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
         * id : 4
         * bit_vender : 6
         * message : à¸—à¸”à¸ªà¸­à¸š
         * image : url
         * create_date : 2017-08-25 10:25:56
         * sender : 192
         */

        private String id;
        private String bit_vender;
        private String message;
        private String image;
        private String create_date;
        private String sender;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBit_vender() {
            return bit_vender;
        }

        public void setBit_vender(String bit_vender) {
            this.bit_vender = bit_vender;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }
    }
}
