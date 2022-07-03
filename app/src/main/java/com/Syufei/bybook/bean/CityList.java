package com.Syufei.bybook.bean;

import java.util.List;

//获取城市区划
public class CityList {
    public static class Province {
        private String status;
        private List<List<Result>> result;

        public Province() {
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<List<Result>> getResult() {
            return result;
        }

        public void setResult(List<List<Result>> result) {
            this.result = result;
        }

        public static class Result {
            private String id;
            private String name;

            public Result() {
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    public static class ChildrenCity {

        private int status;
        private String message;
        private String data_version;
        private List<List<Result>> result;

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setData_version(String data_version) {
            this.data_version = data_version;
        }

        public String getData_version() {
            return data_version;
        }

        public void setResult(List<List<Result>> result) {
            this.result = result;
        }

        public List<List<Result>> getResult() {
            return result;
        }

        public static class Result {

            private String id;
            private String fullname;
            private Location location;

            public void setId(String id) {
                this.id = id;
            }

            public String getId() {
                return id;
            }

            public void setFullname(String fullname) {
                this.fullname = fullname;
            }

            public String getFullname() {
                return fullname;
            }

            public void setLocation(Location location) {
                this.location = location;
            }

            public Location getLocation() {
                return location;
            }

        }

        public static class Location {

            private double lat;
            private double lng;

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLat() {
                return lat;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public double getLng() {
                return lng;
            }

        }
    }
}
