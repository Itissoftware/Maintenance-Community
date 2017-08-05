package com.mncomunity1.model;

import java.util.List;

public class NewsRc {


    private boolean result;
    private String type;
    private List<Arr1Bean> arr1;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Arr1Bean> getArr1() {
        return arr1;
    }

    public void setArr1(List<Arr1Bean> arr1) {
        this.arr1 = arr1;
    }

    public static class Arr1Bean {


        private String cover;
        private String type;
        private String content;
        private String category;
        private String check;
        private String ts;

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCheck() {
            return check;
        }

        public void setCheck(String check) {
            this.check = check;
        }

        public String getTs() {
            return ts;
        }

        public void setTs(String ts) {
            this.ts = ts;
        }
    }
}
