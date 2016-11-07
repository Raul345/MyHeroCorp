package com.herocorp.core.interfaces;

import com.herocorp.core.models.BaseEntity;

/**
 * Created by JitainSharma on 14/06/16.
 */
public interface OnSelectListener<T extends BaseEntity> {

    public void onSelect(T model);
}
