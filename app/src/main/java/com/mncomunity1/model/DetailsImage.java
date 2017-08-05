package com.mncomunity1.model;

import java.util.List;

/**
 * Created by Bas on 7/21/2017 AD.
 */

public class DetailsImage {


    /**
     * result : true
     * total : [{"id":"7","nameProduct":"ผ้ากรอง","titleEn":"","brand":"AAA","company":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก.","cat":"2","img":"photo/thum400-img-464315036069d31c238999097b8b886a.png","companycode":"00004","details":"Twin cloths with a connection neck for automatic filter press.","vendor":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก."}]
     * image : [{"path":"photo/img-b32aa43ad49a282d8b18fa674bc5aed6.jpg"},{"path":"photo/img-b32aa43ad49a282d8b18fa674bc5aed6.jpg"},{"path":"photo/img-b32aa43ad49a282d8b18fa674bc5aed6.jpg"}]
     */

    private boolean result;
    private List<TotalBean> total;
    private List<ImageBean> image;

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

    public List<ImageBean> getImage() {
        return image;
    }

    public void setImage(List<ImageBean> image) {
        this.image = image;
    }

    public static class TotalBean {
        /**
         * id : 7
         * nameProduct : ผ้ากรอง
         * titleEn :
         * brand : AAA
         * company : โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก.
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

    public static class ImageBean {
        /**
         * path : photo/img-b32aa43ad49a282d8b18fa674bc5aed6.jpg
         */

        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
