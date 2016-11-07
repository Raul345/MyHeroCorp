package com.herocorp.core.interfaces;

/**
 * Callback Listener for the sync service life cycle.
 * Created by JitainSharma on 12/06/16.
 */
public interface SyncServiceCallBack {

    public void completed();
    public void error();

}