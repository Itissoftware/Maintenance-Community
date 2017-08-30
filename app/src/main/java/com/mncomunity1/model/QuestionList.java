package com.mncomunity1.model;

import java.util.List;

/**
 * Created by Bas on 8/24/2017 AD.
 */

public class QuestionList {


    /**
     * result : null
     * total : [{"id":"1","userid":"192","category":"4","title":"ผ้ากรอง","details":"ทดสอบบ","path_photo":"1503911653211.jpg","amount":"50"},{"id":"2","userid":"192","category":"4","title":"ผ้า","details":"สสสสสสสสสสสอออออ","path_photo":"1503932622875.jpg","amount":"100"},{"id":"3","userid":"192","category":"4","title":"ผ้า","details":"ทเสอบ","path_photo":"1503972323855.jpg","amount":"4580"},{"id":"4","userid":"192","category":"","title":"ผ้ากรอง","details":"ทดสอบบบ","path_photo":"","amount":"59"},{"id":"5","userid":"192","category":"","title":"ผ้า","details":"สสสสส","path_photo":"","amount":"450"},{"id":"6","userid":"192","category":"","title":"ผ้ากรอง","details":"มมม","path_photo":"","amount":"50"},{"id":"7","userid":"192","category":"4","title":"ผ้ากรอง","details":"ทดสอบบ","path_photo":"1503972926527.jpg","amount":"580"},{"id":"8","userid":"192","category":"","title":"ทดสอบ","details":"ทเาอบ","path_photo":"","amount":"580"},{"id":"9","userid":"192","category":"","title":"ผ้า","details":"ทดสอบ","path_photo":"","amount":"580"},{"id":"10","userid":"192","category":"","title":"ทดสอบ","details":"ทดสอบ","path_photo":"","amount":"5000"},{"id":"11","userid":"192","category":"","title":"ผ้ากรอง","details":"ทดสอบ","path_photo":"","amount":"580"},{"id":"12","userid":"192","category":"","title":"ผ้า","details":"ทดสอบ","path_photo":"","amount":"69"},{"id":"13","userid":"192","category":"","title":"ทดสอบ","details":"ทดสอบ","path_photo":"","amount":"5690"},{"id":"14","userid":"192","category":"","title":"ผ้า","details":"บบบ","path_photo":"","amount":"555"},{"id":"15","userid":"192","category":"","title":"F","details":"hhh","path_photo":"","amount":"58"},{"id":"16","userid":"192","category":"","title":"ผ้ากรอง","details":"ทดสอบ","path_photo":"","amount":"50"},{"id":"17","userid":"192","category":"","title":"ผ้ากรอง","details":"ทดสอบ","path_photo":"","amount":"50"},{"id":"18","userid":"192","category":"","title":"ผ้า","details":"รร","path_photo":"","amount":"50"},{"id":"23","userid":"192","category":"","title":"โซ่","details":"ถุุุถถภี","path_photo":"","amount":"500"},{"id":"24","userid":"192","category":"","title":"โช่","details":"เเเดก","path_photo":"","amount":"500"},{"id":"25","userid":"192","category":"4","title":"โซ่","details":"พพพพพ","path_photo":"1503998177058.jpg","amount":"10"},{"id":"26","userid":"192","category":"","title":"ผ้า","details":"ดดดด","path_photo":"","amount":"50"},{"id":"27","userid":"192","category":"","title":"ผ้า","details":"ดดดด","path_photo":"","amount":"50"},{"id":"28","userid":"192","category":"","title":"ผ้า","details":"ดดดด","path_photo":"","amount":"50"}]
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
         * id : 1
         * userid : 192
         * category : 4
         * title : ผ้ากรอง
         * details : ทดสอบบ
         * path_photo : 1503911653211.jpg
         * amount : 50
         */

        private String id;
        private String userid;
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

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
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
