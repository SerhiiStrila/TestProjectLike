package com.serhii.strila.testproject.di;

import com.serhii.strila.testproject.ui.activity.BaseActivity;

import dagger.Component;

/**
 * Created by Serhii Strila on 03.07.15
 */
@PerApp
@Component(modules = {
        ApplicationModule.class,
})
public interface ApplicationComponent {

    void inject(BaseActivity activity);

}
