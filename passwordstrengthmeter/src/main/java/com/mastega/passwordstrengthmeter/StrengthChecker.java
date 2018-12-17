package com.mastega.passwordstrengthmeter;

import android.content.Context;

/**
 * Created by Tjelvar Guo on 2018-11-24.
 */

public abstract class StrengthChecker {

    Context ctx;

    public StrengthChecker(Context context) {
        this.ctx = context;
    }
    public abstract int CalculateStrength(String password);
    public abstract String getHelperText();
}
