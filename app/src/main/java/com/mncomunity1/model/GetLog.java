package com.mncomunity1.model;

import java.util.List;

public class GetLog {


    private boolean status;
    private List<MovieBean> Movie;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<MovieBean> getMovie() {
        return Movie;
    }

    public void setMovie(List<MovieBean> Movie) {
        this.Movie = Movie;
    }

    public static class MovieBean {

        private String name;
        private String phone;
        private String email;
        private String deatails;
        private String name_company;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDeatails() {
            return deatails;
        }

        public void setDeatails(String deatails) {
            this.deatails = deatails;
        }

        public String getName_company() {
            return name_company;
        }

        public void setName_company(String name_company) {
            this.name_company = name_company;
        }
    }
}
