package com.mastega.passwordstrengthmeter;

/**
 * Created by Tjelvar Guo on 2018-11-24.
 */

public interface StrengthChecker {
    int CalculateStrength(String password);
    int setHelperText();
}
