package com.nabilla.project.contohrealm.realm;


import android.app.Application;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;


/*Untuk membuat database/tabel baru */

public class MainApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        RealmDatabase realmDatabase = new RealmDatabase(this);
        realmDatabase.setMigration(new DataMigration());
    }

    private class DataMigration implements RealmMigration {

        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
            RealmSchema schema = realm.getSchema();

            if (oldVersion == 0){
                schema.create("Barang")
                        .addField("id", int.class)
                        .addField("nama_barang", String.class)
                        .addField("jumlah_barang", int.class)
                        .addField("harga_barang", int.class)
                        .addField("ket", String.class);
                oldVersion++;
            }
        }
    }
}
