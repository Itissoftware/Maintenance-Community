package com.mncomunity1.model;

import java.util.List;


public class News {


    /**
     * result : true
     * total : [{"id":"11","nameth":"Google เปิดแคมเปญ #BeInternetAwesome สอนน้องท่องเน็ตอย่างปลอดภัย","cover":"photo/10529_170608192151wO_22.jpg","category":"1","ts":"2017-06-09","status":"1","read":"1"},{"id":"10","nameth":"Meyer Optic ปล่อยเลนส์โบเก้เทพ ","cover":"photo/10533_17060911394991.jpg","category":"1","ts":"2017-06-09","status":"1","read":"1"},{"id":"9","nameth":"\u200eiCommunity\u202c ระบบบริหารจัดการฐานข้อมูลสมาชิก","cover":"photo/56eacdc17bdf1.jpg","category":"1","ts":"2016-01-10","status":"1","read":"0"},{"id":"8","nameth":"กลุ่มบริษัท ไอเอส ซอฟต์แวร์ จำกัด ได้ร่วมออกบูธงาน Food Innovation Network @ TSP","cover":"photo/56fcca3e89546.jpg","category":"1","ts":"2016-06-15","status":"1","read":"0"}]
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
         * id : 11
         * nameth : Google เปิดแคมเปญ #BeInternetAwesome สอนน้องท่องเน็ตอย่างปลอดภัย
         * cover : photo/10529_170608192151wO_22.jpg
         * category : 1
         * ts : 2017-06-09
         * status : 1
         * read : 1
         */

        private String id;
        private String nameth;
        private String cover;
        private String category;
        private String ts;
        private String status;
        private String read;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNameth() {
            return nameth;
        }

        public void setNameth(String nameth) {
            this.nameth = nameth;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getTs() {
            return ts;
        }

        public void setTs(String ts) {
            this.ts = ts;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRead() {
            return read;
        }

        public void setRead(String read) {
            this.read = read;
        }
    }
}
