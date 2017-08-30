package com.mncomunity1.model;

import java.util.List;

/**
 * Created by Bas on 8/24/2017 AD.
 */

public class PriceVndor {


    /**
     * result : 1
     * total : [{"id":"22","user_id":"192","category":"2","title":"ทดสอบ","details":"ทดสอบ","path_photo":"1503496005975.jpg","amount":"20","nameth":"777","company_nameth":"ไอเอส","email":"777@gmail.com"},{"id":"24","user_id":"192","category":"2","title":"ทดสอบ1","details":"ทดสอบ1","path_photo":"1591503570269.02646.jpg","amount":"","nameth":"777","company_nameth":"ไอเอส","email":"777@gmail.com"},{"id":"28","user_id":"192","category":"4","title":"ผ้า","details":"ททท","path_photo":"1503636087324.jpg","amount":"20","nameth":"777","company_nameth":"ไอเอส","email":"777@gmail.com"},{"id":"29","user_id":"192","category":"2","title":"ผ้ากรอง","details":"hhh","path_photo":"1503636204302.jpg","amount":"20","nameth":"777","company_nameth":"ไอเอส","email":"777@gmail.com"},{"id":"42","user_id":"192","category":"4","title":"ผ้ากรอง","details":"ทดสอบ","path_photo":"1503636934078.jpg","amount":"20","nameth":"777","company_nameth":"ไอเอส","email":"777@gmail.com"},{"id":"51","user_id":"192","category":"4","title":"ผ้า","details":"ทดสอบบ","path_photo":"1503641719365.jpg","amount":"50","nameth":"777","company_nameth":"ไอเอส","email":"777@gmail.com"},{"id":"52","user_id":null,"category":null,"title":null,"details":null,"path_photo":null,"amount":null,"nameth":null,"company_nameth":null,"email":null},{"id":"53","user_id":null,"category":null,"title":null,"details":null,"path_photo":null,"amount":null,"nameth":null,"company_nameth":null,"email":null},{"id":"54","user_id":"159","category":"","title":"ผ้ากรอง","details":"","path_photo":"1591503646659.72414.jpg","amount":"200","nameth":"nick11","company_nameth":"12","email":"nook"},{"id":"55","user_id":"181","category":"","title":"ผ้า","details":"","path_photo":"1811503647804.34704.jpg","amount":"200","nameth":null,"company_nameth":null,"email":null},{"id":"56","user_id":"192","category":"4","title":"ผ้ากรอง","details":"ทเสอบ","path_photo":"1503649197134.jpg","amount":"50","nameth":"777","company_nameth":"ไอเอส","email":"777@gmail.com"},{"id":"57","user_id":"192","category":"4","title":"ผ้ากรอง","details":"ทเสอบ","path_photo":"1503649197134.jpg","amount":"50","nameth":"777","company_nameth":"ไอเอส","email":"777@gmail.com"},{"id":"58","user_id":"192","category":"4","title":"ผ้ากรอง","details":"ทเสอบ","path_photo":"1503649197134.jpg","amount":"50","nameth":"777","company_nameth":"ไอเอส","email":"777@gmail.com"},{"id":"59","user_id":"192","category":"4","title":"ผ้ากรอง","details":"ททท","path_photo":"1503649235426.jpg","amount":"50","nameth":"777","company_nameth":"ไอเอส","email":"777@gmail.com"},{"id":"60","user_id":"192","category":"4","title":"ผ้ากรอง","details":"ทดสอล","path_photo":"1503649947448.jpg","amount":"50","nameth":"777","company_nameth":"ไอเอส","email":"777@gmail.com"},{"id":"61","user_id":"192","category":"4","title":"ผ้ากรอง","details":"ทดสอล","path_photo":"1503649947448.jpg","amount":"50","nameth":"777","company_nameth":"ไอเอส","email":"777@gmail.com"},{"id":"62","user_id":"192","category":"4","title":"ผ้ากรอง","details":"ทดสอล","path_photo":"1503649947448.jpg","amount":"50","nameth":"777","company_nameth":"ไอเอส","email":"777@gmail.com"},{"id":"63","user_id":"192","category":"4","title":"ผ้ากรอง","details":"ทดสอล","path_photo":"1503649947448.jpg","amount":"50","nameth":"777","company_nameth":"ไอเอส","email":"777@gmail.com"},{"id":"64","user_id":"192","category":"4","title":"ผ้ากรอง","details":"ทดสอล","path_photo":"1503649947448.jpg","amount":"50","nameth":"777","company_nameth":"ไอเอส","email":"777@gmail.com"},{"id":"65","user_id":"192","category":"4","title":"ผ้ากรอง","details":"ทดสอล","path_photo":"1503649947448.jpg","amount":"50","nameth":"777","company_nameth":"ไอเอส","email":"777@gmail.com"},{"id":"66","user_id":"192","category":"4","title":"ผ้ากรอง","details":"ทดสอล","path_photo":"1503649947448.jpg","amount":"50","nameth":"777","company_nameth":"ไอเอส","email":"777@gmail.com"},{"id":"68","user_id":"194","category":"4","title":"ผ้า","details":"เทส","path_photo":"1503651719043.jpg","amount":"100","nameth":"888","company_nameth":"is","email":"888@gmail.com"},{"id":"69","user_id":"194","category":"4","title":"กรอง","details":"กรองอากาศ","path_photo":"1503653115851.jpg","amount":"100","nameth":"888","company_nameth":"is","email":"888@gmail.com"}]
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
         * id : 22
         * user_id : 192
         * category : 2
         * title : ทดสอบ
         * details : ทดสอบ
         * path_photo : 1503496005975.jpg
         * amount : 20
         * nameth : 777
         * company_nameth : ไอเอส
         * email : 777@gmail.com
         */

        private String id;
        private String user_id;
        private String category;
        private String title;
        private String details;
        private String path_photo;
        private String amount;
        private String nameth;
        private String company_nameth;
        private String email;
        private String status;

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

        public String getNameth() {
            return nameth;
        }

        public void setNameth(String nameth) {
            this.nameth = nameth;
        }

        public String getCompany_nameth() {
            return company_nameth;
        }

        public void setCompany_nameth(String company_nameth) {
            this.company_nameth = company_nameth;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
