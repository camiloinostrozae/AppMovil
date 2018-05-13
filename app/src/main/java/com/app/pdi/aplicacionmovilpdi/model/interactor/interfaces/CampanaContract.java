package com.app.pdi.aplicacionmovilpdi.model.interactor.interfaces;

import com.app.pdi.aplicacionmovilpdi.model.Object.Campana;

import java.util.ArrayList;
import java.util.List;

public interface CampanaContract {


    //Para manejar la información que se le muestra a los usuarios llamandos obteniendo los datos del Interactor.
    interface presenter{

        void onDestroy();
        void onRefreshButtonClick();
        void requestDataFromServer();

    }

    //Para mostrar los datos a los usuarios.
    interface viewCampanas{

        void showProgress();
        void hideProgress();
        void setDataToRecyclerView(List<Campana> campanaArrayList);
        void onResponseFailure(Throwable throwable);
    }

    //COn las interfaz interactor podremos en el interactor obtener los datos desde las Clases Interactor
    interface getCampanasInteractor{

        interface OnFinishedListener{
            void onFinished(List<Campana> campanaArrayList);
            void onFailure(Throwable t);
        }

        void getCampanaArrayList(OnFinishedListener onFinishedListener);
    }
}
