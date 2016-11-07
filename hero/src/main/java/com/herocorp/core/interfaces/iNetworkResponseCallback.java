package com.herocorp.core.interfaces;

import com.herocorp.core.models.BaseEntity;

import java.util.ArrayList;

/**
 * Network Response Call Back.
 * Created by JitainSharma on 12/06/16.
 */
public interface iNetworkResponseCallback <T extends BaseEntity>{

    public void onSuccess(ArrayList<T> data);
    public void onSuccess(T data);
    public void onFailure(String message, Boolean showToast);

}