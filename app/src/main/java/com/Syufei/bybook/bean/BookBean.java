package com.Syufei.bybook.bean;

import java.io.Serializable;
import java.util.List;

public class BookBean {
        public static class CommonBean {
            private String status;

            public CommonBean() {
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }

        public static class CommonResponse {
            private String status;

            public CommonResponse() {
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }

        /*
         * 书籍列表
         * */
        public static class BookList extends CommonBean implements Serializable {
            private BookListData data;

            public BookList() {
            }

            public BookListData getData() {
                return data;
            }

            public void setData(BookListData data) {
                this.data = data;
            }

            public static class BookListData {
                private List<BookListSubData> list;

                public void setList(List<BookListSubData> list) {
                    this.list = list;
                }

                public List<BookListSubData> getList() {
                    return list;
                }

                public BookListData() {
                }
            }

            public static class BookListSubData implements Serializable {

                private int id;
                private String name;
                private String author;
                private double price;
                private String description;
                private String imgUrl;

                public BookListSubData() {
                }

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

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getDescription() {
                    return description;
                }

                public String getImgUrl() {
                    return imgUrl;
                }

                public void setImgUrl(String imgUrl) {
                    this.imgUrl = imgUrl;
                }
            }
        }

        /*
         * 创建试读
         *          * */
        public static class CreateBookRequest {

            private int bookId;
            private String address;
            private String phone;

            public CreateBookRequest() {

            }

            public void setBookId(int bookId) {
                this.bookId = bookId;
            }

            public int getBookId() {
                return bookId;
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

        }

        /*
         * 获取试读列表
         * */
        public static class TryBookList extends CommonBean {
            public TryBookList() {
            }

            private Data data;

            public void setData(Data data) {
                this.data = data;
            }

            public Data getData() {
                return data;
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

                public void setBook(Book book) {
                    this.book = book;
                }

                public Book getBook() {
                    return book;
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
            }
        }

        /*
         * 创建试读
         * */
        public static class CreateTrybook {
            private int bookId;
            private String address;
            private String phone;

            public CreateTrybook(int bookId, String address, String phone) {
                this.bookId = bookId;
                this.address = address;
                this.phone = phone;
            }

            public int getBookId() {
                return bookId;
            }

            public void setBookId(int bookId) {
                this.bookId = bookId;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }

        public static class CommonPage {
            private int Limit;
            private int Offset;

            public CommonPage(int limit, int offset) {
                Limit = limit;
                Offset = offset;
            }

            public int getLimit() {
                return Limit;
            }

            public void setLimit(int limit) {
                Limit = limit;
            }

            public int getOffset() {
                return Offset;
            }

            public void setOffset(int offset) {
                Offset = offset;
            }
        }

        public static class IdCommon{
            private int id;

            public void setId(int id) {
                this.id = id;
            }

            public int getId() {
                return id;
            }
            public IdCommon(){}
        }
    }

