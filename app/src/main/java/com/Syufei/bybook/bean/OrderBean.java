package com.Syufei.bybook.bean;

import java.util.List;

public class OrderBean {
    /*
     * 订单列表
     * */
    public static class OrderList extends BookBean.CommonBean {
        private Data data;

        public OrderList() {
        }

        public void setData(Data data) {
            this.data = data;
        }

        public Data getData() {
            return data;
        }

        public static class Data {

            private int limit;
            private int Offset;
            private List<Result> list;

            public Data() {
            }

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

        public static class  Result {

            private int id;
            private double price;
            private int status;
            private String address;
            private String createTime;
            private String updateTime;
            private Book book;

            public Result() {
            }

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

        public static class Book {

            private String name;
            private String author;
            private double price;

            public Book() {
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

    /*
     * 创建订单
     * */
    public static class CreateOrder {
        private int bookId;
        private String address;

        public CreateOrder() {
        }

        public void setBookId(int bookId) {
            this.bookId = bookId;
        }

        public int getBookId() {
            return bookId;
        }

        public void setAddress(String adress) {
            this.address = adress;
        }

        public String getAddress() {
            return address;
        }

    }

    /*
     * 取消订单
     * */
    public static class CancelOrder{
        private String orderId;
        public CancelOrder(String id){
            this.orderId=id;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }
}