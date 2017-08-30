package com.mncomunity1.model;

import java.util.List;

/**
 * Created by Bas on 8/24/2017 AD.
 */

public class BitItem {


    /**
     * result : 1
     * total : [{"id":"6","user_id":"192","category":"2","title":"ทดสอบ","details":"ทดสอบ","path_photo":"1503496005975.jpg","amount":"20"}]
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
         * id : 6
         * user_id : 192
         * category : 2
         * title : ทดสอบ
         * details : ทดสอบ
         * path_photo : 1503496005975.jpg
         * amount : 20
         */

        private String id;
        private String user_id;
        private String category;
        private String title;
        private String details;
        private String path_photo;
        private String amount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getPath_photo() {
            return path_photo;
        }

        public void setPath_photo(String path_photo) {
            this.path_photo = path_photo;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
}
