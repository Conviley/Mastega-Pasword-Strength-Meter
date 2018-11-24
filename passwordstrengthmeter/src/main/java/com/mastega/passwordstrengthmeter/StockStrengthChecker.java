package com.mastega.passwordstrengthmeter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tjelvar Guo on 2018-11-24.
 */

public class StockStrengthChecker implements StrengthChecker{

    @Override
    public int CalculateStrength(String password) {
        int strength = 0;

        boolean foundUpperCase = false;
        boolean foundLowerCase = false;
        boolean foundSpecialChar= false;
        boolean foundDigit= false;

        if (password.length() >= 8){
            strength += 1;

            if (password.length() >= 12) {
                strength += 1;
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
        return strength;
    }
}
