package com.mncomunity1.model;

import java.util.List;

/**
 * Created by Bas on 8/23/2017 AD.
 */

public class BitOfferDetails {


    /**
     * result : null
     * total : [{"id":"6","nameth":"Special Steel Center Co., Ltd.","address":"495/68-69 ถนน สาธุประดิษฐ์ แขวง ช่องนนทรี เขต ยานนาวา กรุงเทพมหานคร 10120","company_code":"00001","img_path":"photo/Fc-filter_map.gif"},{"id":"6","nameth":"ซือหาว อินดัสทรี (ไทยแลนด์) บจก.","address":"7/243 หมู่ 6 นิคมอุตสาหกรรมอมตะซิตี้ ต. มาบยางพร อ. ปลวกแดง จ. ระยอง 21140  Basic Information [ EN ] ZU HOW INDUSTRY (THAILAND) CO., LTD. 7/243 Moo 6 Amata City Industrial Estate in. Mabyangporn distr","company_code":"00077","img_path":"photo/logo_8313_51efa2c582c8e2.03609477.jpg"},{"id":"6","nameth":"บริษัท ทรัมพ์โพ เอ็นเตอร์ไพร์ส (ประเทศไทย) จำกัด","address":"1040/20-21 ซอยสุขุมวิท 44/2 ต. พระโขนง อ. คลองเตย จ. กรุงเทพมหานคร 10110  Basic Information [ EN ] Chumpo Enterprise (Thailand) Co., Ltd. 1040 / 20-21 Soi Sukhumvit 44/2 in. Phra Khanong district Klon","company_code":"00083","img_path":"photo/logo_9964_51f1eb8ac13340.69152121.jpg"},{"id":"6","nameth":"บริษัท สเปเชียล สตีล เซ็นเตอร์ จำกัด","address":"495/69 ถนนสาธุประดิษฐ์ ต ช่องนนทรี อ ยานนาวา จ กรุงเทพมหานคร 10120","company_code":"00006","img_path":"photo/jjj.jpg"},{"id":"6","nameth":"บริษัทเฟรชคูลอินเตอร์เนชั่นแนลจำกัด","address":"63/125 หมู่ 6 ต. เสาธงหิน อ. บางใหญ่ จ. นนทบุรี 11140","company_code":"00005","img_path":"photo/a.jpg"},{"id":"6","nameth":"โกลเด้น ฟิลเทค คอร์ปอเรชั่น บจก.","address":"26 Soi On Nut 59, Prawet, Prawet, Bangkok 10250","company_code":"00004","img_path":"photo/test1.jpg"}]
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
         * id : 6
         * nameth : Special Steel Center Co., Ltd.
         * address : 495/68-69 ถนน สาธุประดิษฐ์ แขวง ช่องนนทรี เขต ยานนาวา กรุงเทพมหานคร 10120
         * company_code : 00001
         * img_path : photo/Fc-filter_map.gif
         */

        private String id;
        private String nameth;
        private String address;
        private String company_code;
        private String img_path;

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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCompany_code() {
            return company_code;
        }

        public void setCompany_code(String company_code) {
            this.company_code = company_code;
        }

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }
    }
}
