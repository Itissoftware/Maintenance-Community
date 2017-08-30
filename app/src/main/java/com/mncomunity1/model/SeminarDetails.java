package com.mncomunity1.model;

import java.util.List;

/**
 * Created by Bas on 8/29/2017 AD.
 */

public class SeminarDetails {


    /**
     * result : null
     * total : [{"id":"1","title":"รหัสหลักสูตร : MT-001","course":"สิ่งที่วิศวกรซ่อมบำรุงมืออาชีพ ควรรู้ (4วัน)","details":"bbb","times":"4 วัน","topics":"vvv","path_image":"https://www.thairath.co.th/media/NjpUs24nCQKx5e1A55RKsUe1cbf527NnrKrY6uujCE9.jpg"}]
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
         * title : รหัสหลักสูตร : MT-001
         * course : สิ่งที่วิศวกรซ่อมบำรุงมืออาชีพ ควรรู้ (4วัน)
         * details : bbb
         * times : 4 วัน
         * topics : vvv
         * path_image : https://www.thairath.co.th/media/NjpUs24nCQKx5e1A55RKsUe1cbf527NnrKrY6uujCE9.jpg
         */

        private String id;
        private String title;
        private String course;
        private String details;
        private String times;
        private String topics;
        private String path_image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getTopics() {
            return topics;
        }

        public void setTopics(String topics) {
            this.topics = topics;
        }

        public String getPath_image() {
            return path_image;
        }

        public void setPath_image(String path_image) {
            this.path_image = path_image;
        }
    }
}
