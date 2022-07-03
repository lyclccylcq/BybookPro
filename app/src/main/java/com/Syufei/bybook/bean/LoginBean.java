package com.Syufei.bybook.bean;

public class LoginBean {
    public static class RegisterBody {

        public RegisterBody(String userId, String password, String userName) {
            this.userId = userId;
            this.password = password;
            this.userName = userName;
        }

        private String userId;
        private String password;
        private String userName;
        private String phone;

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserId() {
            return userId;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPassword() {
            return password;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserName() {
            return userName;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhone() {
            return phone;
        }

    }

    /*
     * 登录请求
     * */
    public static class LoginBody {
        public LoginBody(String userId, String password) {
            this.userId = userId;
            this.password = password;
        }

        private String userId;
        private String password;

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserId() {
            return userId;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPassword() {
            return password;
        }

    }

    /*
     * 登录响应
     * */
    public static class LoginResponse {
        public LoginResponse() {
        }

        private String status;
        private Data data;

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public Data getData() {
            return data;
        }

        public static class Data {

            private String token;
            private int type;

            public void setToken(String token) {
                this.token = token;
            }

            public String getToken() {
                return token;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getType() {
                return type;
            }

        }
    }

    /*
     * 用户信息响应
     * */
    public static class UserInfo {
        private String status;
        private Data data;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public UserInfo() {
        }

        public static class Data {
            private String userName;
            private String userId;
            private String phone;
            private int type;

            public Data() {

            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserId() {
                return userId;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPhone() {
                return phone;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getType() {
                return type;
            }
        }
    }
}
