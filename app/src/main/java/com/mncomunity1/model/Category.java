package com.mncomunity1.model;

import java.util.List;

/**
 * Created by chonlakantsattaboot on 3/3/2017 AD.
 */

public class Category {


    /**
     * result : true
     * total : [{"nameen":"Mechanical","nameth":"เครื่องกล","image":"/api/ic_mc.jpg","cat":"1"},{"nameen":"Tools and Equipment","nameth":"เครื่องมือและอุปกรณ์ ","image":"","cat":"2"},{"nameen":"Steel, aluminum, stainless steel.","nameth":"เหล็ก อลูมิเนียม สแตนเลส","image":"","cat":"3"},{"nameen":"Conveying Equipment","nameth":"อุปกรณ์ขนส่ง ลําเลียง","image":"","cat":"4"},{"nameen":"Construction materials","nameth":"วัสดุ ก่อสร้าง ","image":"","cat":"5"},{"nameen":"Electronic","nameth":"ไฟฟ้า อิเล็กทรอนิกส์","image":"","cat":"6"},{"nameen":"Valves,pumps,pipe,Hydraulic,Pneumatic","nameth":"วาล์ว ท่อ ปั้ม นิวเมติก ไฮดรอลิก","image":"","cat":"7"},{"nameen":"Car spare parts","nameth":"อะไหล่รถยนต์","image":"","cat":"8"}]
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
         * nameen : Mechanical
         * nameth : เครื่องกล
         * image : /api/ic_mc.jpg
         * cat : 1
         */

        private String nameen;
        private String nameth;
        private String image;
        private String cat;


        public String getNameen() {
            return nameen;
        }

        public void setNameen(String nameen) {
            this.nameen = nameen;
        }

        public String getNameth() {
            return nameth;
        }

        public void setNameth(String nameth) {
            this.nameth = nameth;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCat() {
            return cat;
        }

        public void setCat(String cat) {
            this.cat = cat;
        }
    }
}
