package com.Syufei.bybook.bean;

import java.util.List;

public class ManagerBean {
    public static class SaleList {

        private String status;
        private Data data;

        public SaleList() {
        }

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

        public static class Book {
            public Book() {
            }

            private String name;
            private String author;
            private double price;

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

        public static class Data {
            public Data() {
            }

            private int limit;
            private int Offset;
            private List<Result> list;

            public void setLimit(int limit) {
                this.limit = limit;
            }

            public int getLimit() {
                return limit;
            }

            public void setOffset(int Offset) {
                this.Offset = Offset;
            }

            public int getOffset() {
                return Offset;
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
            private Book book;

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

            public void setBook(Book book) {
                this.book = book;
            }

            public Book getBook() {
                return book;
            }

        }
    }

    public static class OrderIdBean {
        private int orderId;

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int id) {
            this.orderId = id;
        }

        public OrderIdBean() {
        }
    }

    public static class AddBook {
        public AddBook() {
        }

        private String name;
        private String author;
        private String price;
        private String description;

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

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPrice() {
            return price;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

    }

    public static class DeleteBook {
        private int bookId;

        public int getBookId() {
            return bookId;
        }

        public void setBookId(int bookId) {
            this.bookId = bookId;
        }

        public DeleteBook() {
        }
    }

    public static class TrybookList {
        public TrybookList() {
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

        public static class Book {
            public Book() {
            }

            private String name;
            private String author;
            private double price;

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

        public static class Result {
            public Result() {
            }

            private int id;
            private String address;
            private String phone;
            private String createTime;
            private String updateTime;
            private Book book;

            public void setId(int id) {
                this.id = id;
            }

            public int getId() {
                return id;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getAddress() {
                return address;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPhone() {
                return phone;
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

            public void setCar(Book book) {
                this.book = book;
            }

            public Book getCar() {
                return book;
            }
        }

        public static class Data {
            public Data() {
            }

            private int limit;
            private int Offset;
            private List<Result> list;

            public void setLimit(int limit) {
                this.limit = limit;
            }

            public int getLimit() {
                return limit;
            }

            public void setOffset(int Offset) {
                this.Offset = Offset;
            }

            public int getOffset() {
                return Offset;
            }

            public void setList(List<Result> list) {
                this.list = list;
            }

            public List<Result> getList() {
                return list;
            }
        }
    }

    public static class AfterOrderPrice {
        private int afterSaleOrderId;
        private double price;

        public AfterOrderPrice() {
        }

        public void setAfterSaleOrderId(int afterSaleOrderId) {
            this.afterSaleOrderId = afterSaleOrderId;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getPrice() {
            return price;
        }

        public int getAfterSaleOrderId() {
            return afterSaleOrderId;
        }
    }

    public static class AfterOrderList {
        public AfterOrderList() {
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

        public static class BookResp {
            public BookResp() {
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

            public void setCarResp(BookResp bookResp) {
                this.bookResp = bookResp;
            }

            public BookResp getCarResp() {
                return bookResp;
            }

            public void setUserResp(UserResp userResp) {
                this.userResp = userResp;
            }

            public UserResp getUserResp() {
                return userResp;
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

        public static class Data {
            public Data() {
            }

            private int limit;
            private int Offset;
            private List<Result> list;

            public void setLimit(int limit) {
                this.limit = limit;
            }

            public int getLimit() {
                return limit;
            }

            public void setOffset(int Offset) {
                this.Offset = Offset;
            }

            public int getOffset() {
                return Offset;
            }

            public void setList(List<Result> list) {
                this.list = list;
            }

            public List<Result> getList() {
                return list;
            }

        }
    }

    public static class AfterOrderId {
        private int afterSaleOrderId;

        public int getAfterSaleOrderId() {
            return afterSaleOrderId;
        }

        public void setAfterSaleOrderId(int afterSaleOrderId) {
            this.afterSaleOrderId = afterSaleOrderId;
        }

        public AfterOrderId() {

        }
    }
}
