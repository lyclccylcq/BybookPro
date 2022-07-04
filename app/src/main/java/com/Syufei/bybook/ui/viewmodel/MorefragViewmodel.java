package com.Syufei.bybook.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.Syufei.bybook.bean.BookBean;
import com.Syufei.bybook.bean.CommunityBean;
import com.Syufei.bybook.network.DataSuccessListenter;
import com.Syufei.bybook.repo.CommunityRepo;
import com.Syufei.bybook.ui.adapter.MomentItemAdapter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MorefragViewmodel extends ViewModel {
    private MutableLiveData<List<CommunityBean.Moment>>  momentLivedata=new MutableLiveData<>();
    private MutableLiveData<Boolean> showprogresData=new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isLastPage=new MutableLiveData<>(false);
    private MutableLiveData<String> showBigImg=new MutableLiveData<>("");
    private MutableLiveData<Integer> updateLike=new MutableLiveData<>(-1);
    private MomentItemAdapter adapter;
    private int type=1;//0请求个人，1请求所有人

    public void setType(int type) {
        this.type = type;
        queryData();
    }

    public void setAdapter(MomentItemAdapter adapter) {
        this.adapter = adapter;
    }

    public AtomicInteger getOffset() {
        return offset;
    }

    public void nextPage() {
        if (!isLastPage.getValue()){
            offset.incrementAndGet();
            queryData();
        }
    }

    public MutableLiveData<Integer> getUpdateLike() {
        return updateLike;
    }

    public MutableLiveData<String> getShowBigImg() {
        return showBigImg;
    }

    public MutableLiveData<Boolean> getShowprogresData() {
        return showprogresData;
    }
    public MutableLiveData<List<CommunityBean.Moment>> getMomentLivedata() {
        return momentLivedata;
    }

    public MutableLiveData<Boolean> getIsLastPage() {
        return isLastPage;
    }

    private AtomicInteger offset=new AtomicInteger(1);
    public MorefragViewmodel(){
        super();

    }
    public void refreshData(){
        showprogresData.setValue(true);
        if (type==0){
            CommunityRepo.getSelfList(offset.get(), 10, new DataSuccessListenter() {
                @Override
                public void onDataSuccess(Object obj) {
                    CommunityBean.SelfMomentList momentList=( CommunityBean.SelfMomentList)obj;
                    if (momentList!=null &&momentList.getData().getList().size()>0){
                        momentLivedata.setValue(momentList.getData().getList());
                        isLastPage.setValue(false);
                    }else if (momentList!=null &&momentList.getData().getList().size()==0){
                        isLastPage.setValue(true);
                    }
                    showprogresData.setValue(false);
                }

                @Override
                public void onError(String error) {

                }
            });
        }else if (type==1){
            CommunityRepo.getMomentList(offset.get(), 10, new DataSuccessListenter() {
                @Override
                public void onDataSuccess(Object obj) {
                    CommunityBean.MomentList momentList=( CommunityBean.MomentList)obj;
                    if (momentList!=null &&momentList.getData().getList().size()>0){
                        momentLivedata.setValue(momentList.getData().getList());
                        isLastPage.setValue(false);
                    }else if (momentList!=null &&momentList.getData().getList().size()==0){
                        isLastPage.setValue(true);
                    }
                    showprogresData.setValue(false);
                }

                @Override
                public void onError(String error) {
                    showprogresData.setValue(false);
                }
            });
        }
    }
    public void queryData(){
        showprogresData.setValue(true);
        if (type==0){
            CommunityRepo.getSelfList(offset.get(), 10, new DataSuccessListenter() {
                @Override
                public void onDataSuccess(Object obj) {
                    CommunityBean.SelfMomentList momentList=( CommunityBean.SelfMomentList)obj;
                    if (momentList!=null &&momentList.getData().getList().size()>0){
                        momentLivedata.setValue(momentList.getData().getList());
                        isLastPage.setValue(false);
                    }else if (momentList!=null &&momentList.getData().getList().size()==0){
                        isLastPage.setValue(true);
                    }
                    showprogresData.setValue(false);
                }

                @Override
                public void onError(String error) {

                }
            });
        }else if (type==1){
            CommunityRepo.getMomentList(offset.get(), 10, new DataSuccessListenter() {
                @Override
                public void onDataSuccess(Object obj) {
                    CommunityBean.MomentList momentList=( CommunityBean.MomentList)obj;
                    if (momentList!=null &&momentList.getData().getList().size()>0){
                        momentLivedata.setValue(momentList.getData().getList());
                        isLastPage.setValue(false);
                    }else if (momentList!=null &&momentList.getData().getList().size()==0){
                        isLastPage.setValue(true);
                    }
                    showprogresData.setValue(false);
                }

                @Override
                public void onError(String error) {
                    showprogresData.setValue(false);
                }
            });
        }
    }
    public void momentLike(int momentId,int postion){
        CommunityRepo.momentLike(momentId, new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                BookBean.CommonResponse commonResponse=(BookBean.CommonResponse) obj;
                if (commonResponse.getStatus().equals("success")){
                    updateLike.setValue(postion);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    public void delMoment(int momentId,int pos){
        CommunityRepo.deleteMoment(momentId, new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                adapter.removeItem(pos);
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
