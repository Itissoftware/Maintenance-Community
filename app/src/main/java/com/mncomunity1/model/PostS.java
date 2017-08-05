package com.mncomunity1.model;

import java.util.List;

/**
 * Created by chonlakantsattaboot on 4/24/2017 AD.
 */

public class PostS {


    private int status;
    private List<PostBean> post;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<PostBean> getPost() {
        return post;
    }

    public void setPost(List<PostBean> post) {
        this.post = post;
    }

    public static class PostBean {


        private String code;
        private String title;
        private Object create_date;
        private String details;
        private Object file_img;
        private Object status_img;
        private String OWNER;
        private Object count;
        private String layer;
        private String link;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getCreate_date() {
            return create_date;
        }

        public void setCreate_date(Object create_date) {
            this.create_date = create_date;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public Object getFile_img() {
            return file_img;
        }

        public void setFile_img(Object file_img) {
            this.file_img = file_img;
        }

        public Object getStatus_img() {
            return status_img;
        }

        public void setStatus_img(Object status_img) {
            this.status_img = status_img;
        }

        public String getOWNER() {
            return OWNER;
        }

        public void setOWNER(String OWNER) {
            this.OWNER = OWNER;
        }

        public Object getCount() {
            return count;
        }

        public void setCount(Object count) {
            this.count = count;
        }

        public String getLayer() {
            return layer;
        }

        public void setLayer(String layer) {
            this.layer = layer;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
