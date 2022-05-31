package me.harishpartha.spotifyswiper;

import android.app.Application;
import android.content.Context;

import org.acra.ACRA;
import org.acra.config.CoreConfigurationBuilder;
import org.acra.config.MailSenderConfigurationBuilder;
import org.acra.config.ToastConfigurationBuilder;
import org.acra.data.StringFormat;

public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

//        ACRA.init(this, new CoreConfigurationBuilder()
//                //core configuration:
//                .withBuildConfigClass(BuildConfig.class)
//                .withReportFormat(StringFormat.JSON)
//                .withPluginConfigurations(
//                        //each plugin you chose above can be configured with its builder like this:
//                        new ToastConfigurationBuilder()
//                                .setText("tost")
//                                .build()
//                )
//        );z`

    }
}