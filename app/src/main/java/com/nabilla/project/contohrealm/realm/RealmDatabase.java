package com.nabilla.project.contohrealm.realm;


import android.content.Context;

import com.nabilla.project.contohrealm.model.ModelBarang;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmDatabase {

    private Realm realm;
    private Context context;

    public RealmDatabase(Context context) {
        this.context = context;
        realm = Realm.getInstance(context);
    }

    public void setMigration(RealmMigration realmMigration){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context)
                .schemaVersion(0)
                .migration(realmMigration)
                .name("Barang.realm")
                .build();
        realm = Realm.getInstance(realmConfiguration);
    }

    public Realm getRealm(){
        return realm;
    }

    public void tambahBarang(List<ModelBarang> modelBarangList){
        realm.beginTransaction();
        realm.copyToRealm(modelBarangList);
        realm.commitTransaction();
    }

    public void editBarang(List<ModelBarang> modelBarang){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(modelBarang);
        realm.commitTransaction();
    }

    public void hapusBarang(Class<? extends RealmObject> object, int id){
        RealmResults realmResults = this.getRealm()
                .where(object.asSubclass(ModelBarang.class)).equalTo("id", id).findAll();

        realm.beginTransaction();
        realmResults.clear();
        realm.commitTransaction();
    }

    public RealmResults<? extends RealmObject> getData (Class<? extends RealmObject> objClass){
        return this.getRealm().where(objClass.asSubclass(ModelBarang.class)).findAll();
    }

    public <T extends RealmObject> T getDataById(Class<? extends RealmObject> objClass, int id){
        return (T)
        this.getRealm().where(objClass.asSubclass(ModelBarang.class)).equalTo("id", id).findFirst();
    }

}
