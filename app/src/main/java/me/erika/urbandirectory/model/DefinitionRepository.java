package me.erika.urbandirectory.model;

import android.util.Log;

import me.erika.urbandirectory.network.UrbanDictionaryAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/***
 Description: Model class to communicate with API
 Author: Erika Orozco
 ***/
public class DefinitionRepository {

    private Callbacks mCallbacks;

    //load data from service
    public void loadData(String term) {

        //build service call
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mashape-community-urban-dictionary.p.rapidapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //API Endpoint implementation
        UrbanDictionaryAPI service = retrofit.create(UrbanDictionaryAPI.class);

        Call<DefinitionsList> definitions = service.listDefinitions(term);

        //Send request and receive response
        definitions.enqueue(new Callback<DefinitionsList>() {
            @Override
            public void onResponse(Call<DefinitionsList> call, Response<DefinitionsList> response) {

                mCallbacks.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<DefinitionsList> call, Throwable t) {
                Log.e("Definition", t.getMessage());
            }
        });
    }

    //Set Callbacks to connect with ViewModel
    public void setCallbacks(Callbacks callbacks) {
        mCallbacks = callbacks;
    }

    public interface Callbacks{
         void onResponse(DefinitionsList list);
         void onError(String message);
    }
}

