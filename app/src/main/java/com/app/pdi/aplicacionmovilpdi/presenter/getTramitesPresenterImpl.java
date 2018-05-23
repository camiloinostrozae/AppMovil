package com.app.pdi.aplicacionmovilpdi.presenter;

import com.app.pdi.aplicacionmovilpdi.model.Object.Tramite;
import com.app.pdi.aplicacionmovilpdi.model.interactor.interfaces.TramiteContract;

import java.util.ArrayList;
import java.util.List;

public class getTramitesPresenterImpl implements TramiteContract.presenter, TramiteContract.getTramitesInteractor.OnFinishedListener{

    private TramiteContract.viewTramites view;
    private TramiteContract.getTramitesInteractor getTramites;

    public getTramitesPresenterImpl(TramiteContract.viewTramites view, TramiteContract.getTramitesInteractor getTramites){
        this.view = view;
        this.getTramites  = getTramites;
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
        getTramites.getTramiteArrayList(this);

    }

    @Override
    public void requestDataFromServer() {
        getTramites.getTramiteArrayList(this);

    }

    @Override
    public void onFinished(List<Tramite> tramiteArrayList) {
        if(view != null){
            view.setDataToRecyclerView(tramiteArrayList);
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
