package com.Syufei.bybook.Bean;

import java.io.Serializable;
import java.util.List;

public class CommunityBean {

    public static class Moment implements Serializable {
        public Moment() {

        }

        private int id;
        private String context;
        private int like;
        private String imgUrl;
        private String createTime;
        private String updateTime;
        private AfterOrderBean.CreateAfterOrderRes.User user;

        public void setUser(AfterOrderBean.CreateAfterOrderRes.User user) {
            this.user = user;
        }

        public AfterOrderBean.CreateAfterOrderRes.User getUser() {
            return user;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setContext(String content) {
            this.context = content;
        }

        public String getContext() {
            return context;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public int getLike() {
            return like;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getImgUrl() {
            return imgUrl;
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

    }

    public static class Comment {
        public Comment() {

        }

        private int id;
        private String context;
        private int momentId;
        private String createTime;
        private String updateTime;
        private AfterOrderBean.CreateAfterOrderRes.User user;

        public void setUser(AfterOrderBean.CreateAfterOrderRes.User user) {
            this.user = user;
        }

        public AfterOrderBean.CreateAfterOrderRes.User getUser() {
            return user;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public void setMomentId(int momentId) {
            this.momentId = momentId;
        }

        public int getMomentId() {
            return momentId;
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

    }

    public static class MomentList {
        public MomentList() {
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

            private int limit;
            private int offset;
            private List<Moment> list;

            public void setLimit(int limit) {
                this.limit = limit;
            }

            public int getLimit() {
                return limit;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            public int getOffset() {
                return offset;
            }

            public void setList(List<Moment> list) {
                this.list = list;
            }

            public List<Moment> getList() {
                return list;
            }

        }
    }

    public static class SelfMomentList {
        public SelfMomentList() {

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

            private int limit;
            private int offset;
            private List<Moment> list;

            public void setLimit(int limit) {
                this.limit = limit;
            }

            public int getLimit() {
                return limit;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            public int getOffset() {
                return offset;
            }

            public void setList(List<Moment> list) {
                this.list = list;
            }

            public List<Moment> getList() {
                return list;
            }

        }
    }

    public static class CommonMoment {
        private int momentId;

        public void setMomentId(int momentId) {
            this.momentId = momentId;
        }

        public int getMomentId() {
            return momentId;
        }

        public CommonMoment() {

        }
    }

    public static class CommentList {
        public CommentList() {
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

            private int limit;
            private int offset;
            private List<Comment> list;

            public void setLimit(int limit) {
                this.limit = limit;
            }

            public int getLimit() {
                return limit;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            public int getOffset() {
                return offset;
            }

            public void setList(List<Comment> list) {
                this.list = list;
            }

            public List<Comment> getList() {
                return list;
            }

        }
    }

    public static class CreateComment {
        public CreateComment() {

        }

        private int momentId;
        private String context;

        public void setMomentId(int momentId) {
            this.momentId = momentId;
        }

        public int getMomentId() {
            return momentId;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getContext() {
            return context;
        }

    }

    public static class CreateCommentResp implements Serializable {
        public CreateCommentResp() {

        }

        private String status;
        private Moment data;

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public void setData(Moment data) {
            this.data = data;
        }

        public Moment getData() {
            return data;
        }
    }

    public static class DeleteComment {
        private int commentId;

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

        public int getCommentId() {
            return commentId;
        }

        public DeleteComment() {

        }
    }

    public static class MomentLike {
        public MomentLike() {

        }

        private int momentId;

        public int getMomentId() {
            return momentId;
        }

        public void setMomentId(int momentId) {
            this.momentId = momentId;
        }
    }

}
