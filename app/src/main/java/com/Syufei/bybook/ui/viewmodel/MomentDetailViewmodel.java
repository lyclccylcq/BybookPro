package com.Syufei.bybook.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lzhihua.bycar.bean.CommunityBean;
import com.lzhihua.bycar.bean.LoginBean;
import com.lzhihua.bycar.network.DataSuccessListenter;
import com.lzhihua.bycar.repo.CommunityRepo;
import com.lzhihua.bycar.repo.LoginRepo;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MomentDetailViewmodel extends ViewModel {
    private int momentId;
    private AtomicInteger offset = new AtomicInteger(1);
    private MutableLiveData<Boolean> isLastPage = new MutableLiveData<>(false);
    private MutableLiveData<List<CommunityBean.Comment>> commentData = new MutableLiveData<>();
    private MutableLiveData<Boolean> getUserInfo = new MutableLiveData<>(false);
    private MutableLiveData<LoginBean.UserInfo> userSelfData = new MutableLiveData<>();
    private MutableLiveData<Integer> removeIndex=new MutableLiveData<>(-1);
    public void setMomentId(int momentId) {
        this.momentId = momentId;
    }

    public MutableLiveData<Boolean> getGetUserInfo() {
        return getUserInfo;
    }

    public MutableLiveData<Boolean> getIsLastPage() {
        return isLastPage;
    }

    public MutableLiveData<List<CommunityBean.Comment>> getCommentData() {
        return commentData;
    }

    public MutableLiveData<Integer> getRemoveIndex() {
        return removeIndex;
    }

    public MutableLiveData<LoginBean.UserInfo> getUserSelfData() {
        return userSelfData;
    }

    public MomentDetailViewmodel() {
        LoginRepo.getUserInfo(new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                LoginBean.UserInfo userInfo = (LoginBean.UserInfo) obj;
                if (userInfo != null) {
                    userSelfData.setValue(userInfo);
                    getUserInfo.setValue(true);
                }
            }

            @Override
            public void onError(String error) {
            }
        });
    }

    public void nextPage() {
        if (!isLastPage.getValue()) {
            offset.incrementAndGet();
            queryData();
        }
    }

    public void queryData() {
        CommunityRepo.getCommentList(momentId, offset.get(), 10, new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                CommunityBean.CommentList commentList = (CommunityBean.CommentList) obj;
                if (commentList != null && commentList.getData().getList() != null && commentList.getData().getList().size() > 0) {
                    commentData.setValue(commentList.getData().getList());
                    isLastPage.setValue(false);
                } else if (commentList != null && commentList.getData().getList() != null && commentList.getData().getList().size() == 0) {
                    commentData.setValue(commentList.getData().getList());
                    isLastPage.setValue(true);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public void delComment(int commenId,int pos) {
        CommunityRepo.deleteComment(commenId, new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                removeIndex.setValue(pos);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

}
