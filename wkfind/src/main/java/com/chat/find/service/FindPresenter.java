package com.chat.find.service;




import java.lang.ref.WeakReference;



public class FindPresenter implements FindContract.FindPersenter {
    private final WeakReference<FindContract.FindView> findView;

    public FindPresenter(FindContract.FindView findView) {
        this.findView = new WeakReference<>(findView);
    }



    @Override
    public void showLoading() {

    }

    @Override
    public void getBannerList() {
        FindModel.getInstance().getBannerList((o) -> {
            if (findView.get() != null) findView.get().bannerResult(o);
        });
    }

    @Override
    public void getFindList() {
        FindModel.getInstance().getFindList((o) -> {
            if (findView.get() != null) findView.get().FindResult(o);
        });
    }
}
