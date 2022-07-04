package com.Syufei.bybook.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import com.Syufei.bybook.R;
import com.Syufei.bybook.bean.CityList;
import com.Syufei.bybook.commonui.PopupDialog;
import com.Syufei.bybook.databinding.ChooseCityDialogBinding;
import com.Syufei.bybook.network.DataSuccessListenter;
import com.Syufei.bybook.repo.CityRepo;

import java.util.ArrayList;
import java.util.List;

public class ChooseCityDialog extends PopupDialog {
    private final ChooseCityDialogBinding chooseCityDialogBinding;
    private final ArrayAdapter<String> arrayAdapter;
    private List<CityList.Province.Result> provincesList;
    private List<String> names;
    private List<CityList.ChildrenCity.Result> citiesList;
    private int level = 0;//1：省   2：市

    public ChooseCityDialog(Context context) {
        this(context, R.layout.choose_city_dialog, 0);
    }

    public ChooseCityDialog(Context context, int resId, int heightType) {
        super(context, resId, 0);
        names = new ArrayList<>();
        chooseCityDialogBinding = ChooseCityDialogBinding.inflate(LayoutInflater.from(mDialog.getContext()));
        mDialog.setContentView(chooseCityDialogBinding.getRoot());
        arrayAdapter = new ArrayAdapter<String>(mDialog.getContext(), R.layout.choose_city_item, names);
        chooseCityDialogBinding.chooseCityRecycler.setAdapter(arrayAdapter);
        chooseCityDialogBinding.chooseCityTipTv1.setOnClickListener(view -> {
            if (level == 2) {
                level = 1;
                queryProvinces();
            }
        });
        chooseCityDialogBinding.chooseCityRecycler.setOnItemClickListener((adapterView, view, i, l) -> {
            if (level == 1) {
                CityList.Province.Result result = provincesList.get(i);
                queryCity(result.getId());
            } else if (level == 2) {
                CityList.ChildrenCity.Result result = citiesList.get(i);
                Bundle bundle = new Bundle();
                bundle.putString("city", result.getFullname());
                setData(bundle);
                dismiss();
            }
        });
        chooseCityDialogBinding.chooseCityTop.titleBack.setOnClickListener(view -> {
            dismiss();
        });
        queryProvinces();
    }

    private void updateUI() {
        chooseCityDialogBinding.chooseCityTipTv2.setVisibility(level==2? View.VISIBLE :View.GONE);
        arrayAdapter.notifyDataSetChanged();
    }

    private void queryProvinces() {
        names.clear();
        CityRepo.queryProvinces(new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                CityList.Province province = (CityList.Province) obj;
                provincesList = province.getResult().get(0);
                for (CityList.Province.Result result : provincesList) {
                    names.add(result.getName());
                }
                level = 1;
                updateUI();
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void queryCity(String id) {
        names.clear();

        CityRepo.queryChildrennCity(id, new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                CityList.ChildrenCity childrenCity = (CityList.ChildrenCity) obj;
                citiesList = childrenCity.getResult().get(0);
                for (CityList.ChildrenCity.Result result : citiesList) {
                    names.add(result.getFullname());
                }
                level = 2;
                updateUI();
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
