package com.venak.exhangerates.mudules;

import com.venak.exhangerates.application.CurrencyApplication;
import com.venak.exhangerates.services.handler.BaseServiceHandler;
import com.venak.exhangerates.services.handler.ServiceExecutor;
import com.venak.exhangerates.ui.activities.BaseActivity;
import com.venak.exhangerates.ui.fragments.BaseFragment;


import dagger.Component;

@Component(modules = {AppModule.class, NetModule.class})
@CurrencyApplicationScope
public interface AppComponent {
    void inject(CurrencyApplication currencyApplication);

    void inject(BaseServiceHandler baseServiceHandler);

    void inject(BaseActivity baseActivity);

    void inject(BaseFragment baseFragment);

    void inject(ServiceExecutor baseFragment);
}
