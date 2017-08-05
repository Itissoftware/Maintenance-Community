package com.mncomunity1.model;

import java.util.List;

/**
 * Created by Bas on 7/20/2017 AD.
 */

public class SearchModel {


    /**
     * result : true
     * total : [{"id":"6","nameProduct":"FA SX wound polypropylene from 1-3 to 100 micron","titleEn":"","brand":"FA SX wound polypropylene from 1-3 to 100 micron","company":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก.","cat":"2","img":"photo/img-80caa6b38a0336a2f180125273df41ed.png","companycode":"00005","details":"","vendor":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก."},{"id":"7","nameProduct":"ผ้ากรอง","titleEn":"","brand":"AAA","company":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก.","cat":"2","img":"photo/thum400-img-464315036069d31c238999097b8b886a.png","companycode":"00004","details":"Twin cloths with a connection neck for automatic filter press.","vendor":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก."},{"id":"8","nameProduct":"EcoBond Cartridge","titleEn":"","brand":"BBB","company":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก.","cat":"2","img":"photo/thum400-img-dc56d120b1efbfcc1618bd2becd9b445.png","companycode":"00004","details":"Applications : Photographic chemicals, DI Water, Plating Solutions, R.O.Prefiltration, Organic Sovents, Food & Beverages, Chemical ","vendor":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก."},{"id":"9","nameProduct":"A SX stainless steel net - 70 ","titleEn":"","brand":"CCC","company":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก.","cat":"2","img":"photo/thum400-img-97e760a6333f25d5f87ad7eedf36ae14.png","companycode":"00004","details":"ไม่มีข้อมูล","vendor":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก."},{"id":"10","nameProduct":"Viledon Air Filter","titleEn":"","brand":"Viledon Air Filter","company":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก.","cat":"2","img":"photo/img-34b7231641a72d442b7510863ca47498.jpg","companycode":"00006","details":"","vendor":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก."},{"id":"11","nameProduct":"หม้อต้มน้ำมันร้อน","titleEn":"","brand":"หม้อต้มน้ำมันร้อน","company":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก.","cat":"2","img":"photo/img-e6873eb9deddcce3771d26007022366c.jpg","companycode":"00077","details":"","vendor":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก."},{"id":"12","nameProduct":"หัวเผา เชื้อเพลิงแก๊ส","titleEn":"","brand":"หัวเผา เชื้อเพลิงแก๊ส","company":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก.","cat":"2","img":"photo/img-3cda6c9b4388ea11acd327d5d33764ab.jpg","companycode":"00077","details":"","vendor":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก."},{"id":"13","nameProduct":"เครื่องเป่าขวดพลาสติก","titleEn":"","brand":"เครื่องเป่าขวดพลาสติก","company":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก.","cat":"2","img":"photo/img-b32aa43ad49a282d8b18fa674bc5aed6.jpg","companycode":"00083","details":"","vendor":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก."},{"id":"15","nameProduct":"Inven Cloud","titleEn":"","brand":"Inven Cloud","company":"IS SOFTWARE","cat":"15","img":"photo/logo_cmms_large[1].gif","companycode":"00084","details":"","vendor":"IS SOFTWARE"}]
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
         * id : 6
         * nameProduct : FA SX wound polypropylene from 1-3 to 100 micron
         * titleEn :
         * brand : FA SX wound polypropylene from 1-3 to 100 micron
         * company : โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก.
         * cat : 2
         * img : photo/img-80caa6b38a0336a2f180125273df41ed.png
         * companycode : 00005
         * details :
         * vendor : โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก.
         */

        private String id;
        private String nameProduct;
        private String titleEn;
        private String brand;
        private String company;
        private String cat;
        private String img;
        private String companycode;
        private String details;
        private String vendor;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNameProduct() {
            return nameProduct;
        }

        public void setNameProduct(String nameProduct) {
            this.nameProduct = nameProduct;
        }

        public String getTitleEn() {
            return titleEn;
        }

        public void setTitleEn(String titleEn) {
            this.titleEn = titleEn;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCat() {
            return cat;
        }

        public void setCat(String cat) {
            this.cat = cat;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getCompanycode() {
            return companycode;
        }

        public void setCompanycode(String companycode) {
            this.companycode = companycode;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }
    }
}
