package com.mastega.passwordstrengthmeter;

import android.content.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tjelvar Guo on 2018-11-24.
 */

public class StockStrengthChecker extends StrengthChecker{

    private boolean foundUpperCase = false;
    private boolean foundLowerCase = false;
    private boolean foundSpecialChar = false;
    private boolean foundDigit = false;
    private boolean adequateLength = false;
    private boolean idealLength = false;

    StockStrengthChecker(Context context) {
        super(context);
    }


    @Override
    public int CalculateStrength(String password) {
        foundUpperCase = false;
        foundLowerCase = false;
        foundSpecialChar = false;
        foundDigit = false;
        adequateLength = false;
        idealLength = false;

        int strength = 0;

        if (password.length() >= 1) {
            strength += 1;
            if (password.length() >= 8){
                strength += 1;
                adequateLength = true;

                if (password.length() >= 12) {
                    strength += 1;
                    idealLength = true;
                }

                for (int i = 0; i < password.length(); i++) {
                    if (foundUpperCase && foundLowerCase && foundSpecialChar && foundDigit) {
                        break;
                    }

                    Character character = password.charAt(i);

                    if (Character.isDigit(character)) {
                        foundDigit = true;
                    } else if (Character.isUpperCase(character)){
                        foundUpperCase = true;
                    } else if (Character.isLowerCase(character)) {
                        foundLowerCase = true;
                    } else {
                        Pattern p = Pattern.compile("[^A-Za-z0-9]");
                        Matcher m = p.matcher(password);
                        if (m.find()) {
                            foundSpecialChar = true;
                        }
                    }
                }

                if (foundLowerCase && foundUpperCase) {
                    strength += 1;
                }
                if (foundDigit) {
                    strength += 1;
                }
                if (foundSpecialChar) {
                    strength += 1;
                }
            }
        }
        return strength;
    }

    @Override
    public String getHelperText() {
        if (!adequateLength) {
            return ctx.getString(R.string.under_eight);
        }
        else if (!foundLowerCase || !foundUpperCase) {
            return ctx.getString(R.string.no_mixed_case);
        }
        else if (!foundDigit) {
            return ctx.getString(R.string.no_numerical_char);
        }
        else if (!foundSpecialChar) {
            return ctx.getString(R.string.no_special_char);
        }
        else if (!idealLength) {
            return ctx.getString(R.string.under_twelve);
        }
        return ctx.getString(R.string.ideal_password);
    }
}
