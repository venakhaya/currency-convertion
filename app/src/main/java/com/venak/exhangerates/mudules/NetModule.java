package com.venak.exhangerates.mudules;

import com.venak.exhangerates.services.ApiClient;
import com.venak.exhangerates.services.handler.ServiceExecutor;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class NetModule {

    @Provides
    @CurrencyApplicationScope
    Retrofit provideRetrofit() {
        return ApiClient.provideRetrofit();
    }

    @Provides
    @CurrencyApplicationScope
    public ServiceExecutor serviceExecutor() {
        return new ServiceExecutor();
    }

}
