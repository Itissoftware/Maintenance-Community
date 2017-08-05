package com.mncomunity1.model;

import java.util.List;

public class ModelVP {


    public ModelVP() {

    }

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
        /**
         * code : 0
         * title :
         * file_img : http://mn-community.com/community_service/slide/head.png
         */

        private int code;
        private String title;
        private String file_img;

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

        public String getFile_img() {
            return file_img;
        }

        public void setFile_img(String file_img) {
            this.file_img = file_img;
        }
    }
}
