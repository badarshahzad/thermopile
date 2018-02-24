package com.knobtviker.thermopile.data.sources;

import android.support.annotation.NonNull;

import com.knobtviker.thermopile.data.models.local.Acceleration;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by bojan on 18/07/2017.
 */

public interface AccelerationDataSource {

    interface Local {

        RealmResults<Acceleration> load(@NonNull final Realm realm);

        void save(@NonNull final List<Acceleration> items);
    }
}