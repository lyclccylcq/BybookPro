package com.Syufei.bybook.repo;

import com.alibaba.fastjson.JSON;
import com.Syufei.bybook.bean.BookBean;
import com.Syufei.bybook.bean.CommunityBean;
import com.Syufei.bybook.network.DataSuccessListenter;
import com.Syufei.bybook.network.NetworkUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommunityRepo {
    private static final String All_Moments="/moment/list";
    private static final String Self_Moments="/moment/self_list";
    private static final String Create_Moment="/moment/create";
    private static final String Delete_Moment="/moment/delete";
    private static final String Comment_list="/comment/list";
    private static final String Create_Comment="/comment/create";
    private static final String Delete_Commment="/comment/delete";
    private static final String Moment_like="/moment/thumb_up";

    public static void getMomentList(int offset, int limit, final DataSuccessListenter listenter){
        Map<String,String> params=new HashMap<>();
        params.put("Offset",offset+"");
        params.put("Limit",limit+"");
        NetworkUtil.getInstance().doGet(All_Moments, params, new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                CommunityBean.MomentList momentList= JSON.parseObject(response, CommunityBean.MomentList.class);
                listenter.onDataSuccess(momentList);
            }

            @Override
            public void onFailed(String errorMsg) {
                listenter.onError(errorMsg);
            }
        });
    }
    public static void getSelfList(int offset, int limit, final DataSuccessListenter listenter){
        Map<String,String> params=new HashMap<>();
        params.put("Offset",offset+"");
        params.put("Limit",limit+"");
        NetworkUtil.getInstance().doGet(Self_Moments, params, new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                CommunityBean.SelfMomentList momentList= JSON.parseObject(response, CommunityBean.SelfMomentList.class);
                listenter.onDataSuccess(momentList);
            }

            @Override
            public void onFailed(String errorMsg) {
                listenter.onError(errorMsg);
            }
        });
    }

    public static void createMoment(String context, List<String> paths,final DataSuccessListenter listenter){
        Map<String,String> params=new HashMap<>();
        params.put("context",context);
        NetworkUtil.getInstance().doPost(Create_Moment, params, "files", paths, new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                CommunityBean.CreateCommentResp commonResponse=JSON.parseObject(response, CommunityBean.CreateCommentResp.class);
                listenter.onDataSuccess(commonResponse);
            }

            @Override
            public void onFailed(String errorMsg) {
                listenter.onError(errorMsg);
            }
        });
    }

    public static void momentLike(int id,final DataSuccessListenter listenter){
        CommunityBean.MomentLike momentLike=new CommunityBean.MomentLike();
        momentLike.setMomentId(id);
        NetworkUtil.getInstance().doPost(Moment_like, JSON.toJSONString(momentLike), new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                BookBean.CommonResponse commonResponse=JSON.parseObject(response, BookBean.CommonResponse.class);
                listenter.onDataSuccess(commonResponse);
            }

            @Override
            public void onFailed(String errorMsg) {
                listenter.onError(errorMsg);
            }
        });
    }

    public static void getCommentList(int id,int offset,int limit,final DataSuccessListenter listenter){
        Map<String,String> params=new HashMap<>();
        params.put("Offset",offset+"");
        params.put("Limit",limit+"");
        params.put("MomentId",id+"");
        NetworkUtil.getInstance().doGet(Comment_list, params, new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                CommunityBean.CommentList commentList= JSON.parseObject(response, CommunityBean.CommentList.class);
                listenter.onDataSuccess(commentList);
            }

            @Override
            public void onFailed(String errorMsg) {
                listenter.onError(errorMsg);
            }
        });
    }

    public static void createComment(int id,String context,final DataSuccessListenter listenter){
        CommunityBean.CreateComment createComment=new CommunityBean.CreateComment();
        createComment.setContext(context);
        createComment.setMomentId(id);
        NetworkUtil.getInstance().doPost(Create_Comment, JSON.toJSONString(createComment), new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                BookBean.CommonResponse commonResponse=JSON.parseObject(response, BookBean.CommonResponse.class);
                listenter.onDataSuccess(commonResponse);
            }

            @Override
            public void onFailed(String errorMsg) {
                listenter.onError(errorMsg);
            }
        });
    }
    public static void deleteComment(int id,final DataSuccessListenter listenter){
        CommunityBean.DeleteComment delete=new CommunityBean.DeleteComment();
        delete.setCommentId(id);
        NetworkUtil.getInstance().doPost(Delete_Commment, JSON.toJSONString(delete), new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                BookBean.CommonResponse commonResponse=JSON.parseObject(response, BookBean.CommonResponse.class);
                listenter.onDataSuccess(commonResponse);
            }

            @Override
            public void onFailed(String errorMsg) {
                listenter.onError(errorMsg);
            }
        });
    }

    public static void deleteMoment(int id,final DataSuccessListenter listenter){
        CommunityBean.MomentLike delete=new CommunityBean.MomentLike();
        delete.setMomentId(id);
        NetworkUtil.getInstance().doPost(Delete_Moment, JSON.toJSONString(delete), new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                BookBean.CommonResponse commonResponse=JSON.parseObject(response, BookBean.CommonResponse.class);
                listenter.onDataSuccess(commonResponse);
            }

            @Override
            public void onFailed(String errorMsg) {
                listenter.onError(errorMsg);
            }
        });
    }
}
