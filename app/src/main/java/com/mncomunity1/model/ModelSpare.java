package com.mncomunity1.model;

import java.util.List;


public class ModelSpare {

    /**
     * result : true
     * total : [{"id":"7","nameProduct":"ผ้ากรอง","titleEn":"","brand":"AAA","company":"{}","cat":"2","img":"photo/thum400-img-464315036069d31c238999097b8b886a.png","companycode":"00004","details":"Twin cloths with a connection neck for automatic filter press.","vendor":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก."},{"id":"8","nameProduct":"EcoBond Cartridge","titleEn":"","brand":"BBB","company":"{}","cat":"2","img":"photo/thum400-img-dc56d120b1efbfcc1618bd2becd9b445.png","companycode":"00004","details":"Applications : Photographic chemicals, DI Water, Plating Solutions, R.O.Prefiltration, Organic Sovents, Food & Beverages, Chemical ","vendor":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก."},{"id":"9","nameProduct":"A SX stainless steel net - 70 ","titleEn":"","brand":"CCC","company":"{}","cat":"2","img":"photo/thum400-img-97e760a6333f25d5f87ad7eedf36ae14.png","companycode":"00004","details":"ไม่มีข้อมูล","vendor":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก."}]
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
         * id : 7
         * nameProduct : ผ้ากรอง
         * titleEn :
         * brand : AAA
         * company : {}
         * cat : 2
         * img : photo/thum400-img-464315036069d31c238999097b8b886a.png
         * companycode : 00004
         * details : Twin cloths with a connection neck for automatic filter press.
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
