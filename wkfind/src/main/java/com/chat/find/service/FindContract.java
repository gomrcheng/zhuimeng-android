package com.chat.find.service;


import com.chat.base.base.WKBasePresenter;
import com.chat.base.base.WKBaseView;
import com.chat.find.entity.WkFindBanner;
import com.chat.find.entity.WkFindList;

import java.util.List;


public class FindContract {
    public interface FindPersenter extends WKBasePresenter {
        void getBannerList();
        void getFindList();
    }

    public interface FindView extends WKBaseView {
        void bannerResult(List<WkFindBanner> list);
        void FindResult(List<WkFindList> list);

    }
}
