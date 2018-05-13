package com.app.pdi.aplicacionmovilpdi.presenter;

import com.app.pdi.aplicacionmovilpdi.model.Object.Campana;
import com.app.pdi.aplicacionmovilpdi.model.interactor.interfaces.CampanaContract;

import java.util.ArrayList;
import java.util.List;

public class getCampanasPresenterImpl implements CampanaContract.presenter, CampanaContract.getCampanasInteractor.OnFinishedListener{

    private CampanaContract.viewCampanas view;
    private CampanaContract.getCampanasInteractor getCampanas;

    public getCampanasPresenterImpl(CampanaContract.viewCampanas view, CampanaContract.getCampanasInteractor getCampanas){
        this.view = view;
        this.getCampanas  = getCampanas;
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onRefreshButtonClick() {

        if(view!=null){
            view.showProgress();
        }
        getCampanas.getCampanaArrayList(this);

    }

    @Override
    public void requestDataFromServer() {
        getCampanas.getCampanaArrayList(this);

    }

    @Override
    public void onFinished(List<Campana> campanaArrayList) {
        if(view != null){
            view.setDataToRecyclerView(campanaArrayList);
            view.hideProgress();
        }

    }

    @Override
    public void onFailure(Throwable t) {

        if(view != null){
            view.onResponseFailure(t);
            view.hideProgress();
        }

    }
}
