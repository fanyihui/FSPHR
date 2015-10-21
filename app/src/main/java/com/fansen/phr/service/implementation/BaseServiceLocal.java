package com.fansen.phr.service.implementation;

import android.content.Context;

import com.fansen.phr.db.FsPhrDB;

/**
 * Created by 310078142 on 2015/10/20.
 */
public abstract class BaseServiceLocal {
    protected FsPhrDB fsPhrDB = null;

    public BaseServiceLocal(Context context){
        fsPhrDB = FsPhrDB.getInstance(context);
    }
}
