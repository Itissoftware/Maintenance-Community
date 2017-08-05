package com.mncomunity1.model;

import java.util.List;


public class Post {


    private int status;
    private List<PostBean> post;

    public Post(){

    }

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

        private int code;
        private String title;
        private Object details;
        private String file_img;
        private Object status_img;
        private String OWNER;
        private String count;
        private String layer;
        private String link;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getDetails() {
            return details;
        }

        public void setDetails(Object details) {
            this.details = details;
        }

        public String getFile_img() {
            return file_img;
        }

        public void setFile_img(String file_img) {
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

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
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
