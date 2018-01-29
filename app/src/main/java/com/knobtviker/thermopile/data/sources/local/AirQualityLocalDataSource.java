package com.knobtviker.thermopile.data.sources.local;

import android.support.annotation.NonNull;

import com.knobtviker.thermopile.data.models.local.AirQuality;
import com.knobtviker.thermopile.data.sources.AirQualityDataSource;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by bojan on 26/06/2017.
 */

public class AirQualityLocalDataSource implements AirQualityDataSource.Local {

    @Inject
    public AirQualityLocalDataSource() {
    }

    @Override
    public RealmResults<AirQuality> latest(@NonNull final Realm realm) {
        return realm
            .where(AirQuality.class)
            .sort("timestamp", Sort.DESCENDING)
            .findAll();
    }

    @Override
    public void save(@NonNull final AirQuality item) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.insert(item));
        realm.close();
    }
}