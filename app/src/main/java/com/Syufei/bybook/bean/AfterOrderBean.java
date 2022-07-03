package com.Syufei.bybook.bean;

import java.io.Serializable;
import java.util.List;

public class AfterOrderBean {
    public static class SelfBook implements Serializable {
        public SelfBook() {
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
            public Data() {
            }

            private List<Result> list;

            public void setList(List<Result> list) {
                this.list = list;
            }

            public List<Result> getList() {
                return list;
            }
        }

        public static class Result implements Serializable {
            public Result() {
            }

            private int id;
            private String name;
            private String version;
            private double price;
            private String description;
            private int orderId;

            public void setId(int id) {
                this.id = id;
            }

            public int getId() {
                return id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getVersion() {
                return version;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getPrice() {
                return price;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getDescription() {
                return description;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public int getOrderId() {
                return orderId;
            }
        }
    }

    public static class AfterOrder {
        public AfterOrder() {
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
            public Data() {
            }

            private int offset;
            private int limit;
            private List<Result> list;
            public void setOffset(int offset) {
                this.offset = offset;
            }
            public int getOffset() {
                return offset;
            }

            public void setLimit(int limit) {
                this.limit = limit;
            }
            public int getLimit() {
                return limit;
            }

            public void setList(List<Result> list) {
                this.list = list;
            }
            public List<Result> getList() {
                return list;
            }

        }

        public static class Result {
            public Result() {
            }

            private int id;
            private double price;
            private int status;
            private String address;
            private String createTime;
            private String updateTime;
            private BookResp bookResp;
            private UserResp userResp;
            public void setId(int id) {
                this.id = id;
            }
            public int getId() {
                return id;
            }

            public void setPrice(double price) {
                this.price = price;
            }
            public double getPrice() {
                return price;
            }

            public void setStatus(int status) {
                this.status = status;
            }
            public int getStatus() {
                return status;
            }

            public void setAddress(String address) {
                this.address = address;
            }
            public String getAddress() {
                return address;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
            public String getCreateTime() {
                return createTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
            public String getUpdateTime() {
                return updateTime;
            }

            public void setBookResp(BookResp bookResp) {
                this.bookResp = bookResp;
            }
            public BookResp getBookResp() {
                return bookResp;
            }

            public void setUserResp(UserResp userResp) {
                this.userResp = userResp;
            }
            public UserResp getUserResp() {
                return userResp;
            }

        }
        public static class BookResp {
            public BookResp() {
            }

            private int id;
            private String name;
            private String author;
            private double price;
            private String description;
            private int orderId;
            public void setId(int id) {
                this.id = id;
            }
            public int getId() {
                return id;
            }

            public void setName(String name) {
                this.name = name;
            }
            public String getName() {
                return name;
            }

            public void setAuthor(String version) {
                this.author = version;
            }
            public String getAuthor() {
                return author;
            }

            public void setPrice(double price) {
                this.price = price;
            }
            public double getPrice() {
                return price;
            }

            public void setDescription(String description) {
                this.description = description;
            }
            public String getDescription() {
                return description;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }
            public int getOrderId() {
                return orderId;
            }

        }
        public static class UserResp {
            public UserResp() {
            }

            private int id;
            private String userId;
            private String name;
            private String phone;
            public void setId(int id) {
                this.id = id;
            }
            public int getId() {
                return id;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
            public String getUserId() {
                return userId;
            }

            public void setName(String name) {
                this.name = name;
            }
            public String getName() {
                return name;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
            public String getPhone() {
                return phone;
            }

        }
    }


    public static class CreateAfterOrder {
        public CreateAfterOrder() {
        }

        private int saleOrderId;
        private String address;

        public void setSaleOrderId(int saleOrderId) {
            this.saleOrderId = saleOrderId;
        }

        public int getSaleOrderId() {
            return saleOrderId;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress() {
            return address;
        }

    }

    public static class CreateAfterOrderRes {
        public CreateAfterOrderRes() {
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

        public static class User implements Serializable{
            public User() {
            }

            private int id;
            private String userId;
            private String name;
            private String phone;

            public void setId(int id) {
                this.id = id;
            }

            public int getId() {
                return id;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserId() {
                return userId;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPhone() {
                return phone;
            }

        }

        public static class Data {
            public Data() {
            }

            private int id;
            private double price;
            private int status;
            private String address;
            private String createTime;
            private String updateTime;
            private Book book;
            private User user;

            public void setId(int id) {
                this.id = id;
            }

            public int getId() {
                return id;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getPrice() {
                return price;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getStatus() {
                return status;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getAddress() {
                return address;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setBook(Book car) {
                this.book = book;
            }

            public Book getBook() {
                return book;
            }

            public void setUser(User user) {
                this.user = user;
            }

            public User getUser() {
                return user;
            }

        }

        public static class Book {
            public Book() {
            }

            private int id;
            private String name;
            private String author;
            private double price;

            public void setId(int id) {
                this.id = id;
            }

            public int getId() {
                return id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getAuthor() {
                return author;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getPrice() {
                return price;
            }

        }
    }

    public static class CancelOrder {
        public CancelOrder() {
        }

        private int afterSaleOrderId;

        public void setAfterSaleOrderId(int afterSaleOrderId) {
            this.afterSaleOrderId = afterSaleOrderId;
        }

        public int getAfterSaleOrderId() {
            return afterSaleOrderId;
        }

    }
}
