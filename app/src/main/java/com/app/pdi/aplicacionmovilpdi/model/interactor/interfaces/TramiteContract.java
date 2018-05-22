package com.app.pdi.aplicacionmovilpdi.model.interactor.interfaces;

import com.app.pdi.aplicacionmovilpdi.model.Object.Tramite;

import java.util.ArrayList;
import java.util.List;

public interface TramiteContract {

    //Para manejar la información que se le muestra a los usuarios llamandos obteniendo los datos del Interactor.
    interface presenter{

        void onDestroy();
        void onRefreshButtonClick();
        void requestDataFromServer();

    }

    //Para mostrar los datos a los usuarios.
    interface viewTramites{

        void showProgress();
        void hideProgress();
        void setDataToRecyclerView(List<Tramite> tramiteArrayList);
        void onResponseFailure(Throwable throwable);
    }

    //COn las interfaz interactor podremos en el interactor obtener los datos desde las Clases Interactor
    interface getTramitesInteractor{

        interface OnFinishedListener{
            void onFinished(List<Tramite> tramiteArrayList);
            void onFailure(Throwable t);
        }

        void getTramiteArrayList(OnFinishedListener onFinishedListener);
    }
}
