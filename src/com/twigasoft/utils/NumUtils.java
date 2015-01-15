/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twigasoft.utils;

import com.twigasoft.properties.SystemInfo;
import java.text.DecimalFormat;
import java.util.Properties;

/**
 *
 * @author Victor
 */
public class NumUtils {

    static Properties props = new SystemInfo().loadParams();

    public NumUtils() {
    }

    public static String getCurrency() {
        return props.getProperty("currency.name").split("-")[2];
    }

    public static String formatAmount(String amount) {
        double value = Double.parseDouble(amount);
        if (value == 0) {
            return "0.00";
        } else {
            DecimalFormat formatter = new DecimalFormat(props.getProperty("currency.format"));
            return formatter.format(value);
        }
    }

    public static String unformatAmount(String value) {
        StringBuilder result = new StringBuilder(value.length());
        String[] words;
        words = value.split(",");
        for (int i = 0, l = words.length; i < l; ++i) {
            if (i > 0) {
                result.append("");
            }
            result.append(Character.toUpperCase(words[i].charAt(0)))
                    .append(words[i].substring(1));
        }
        return Integer.toString((int) Double.parseDouble(result.toString()));
    }

    public static String appendPerc(String value) {
        return value + "%";
    }

    public static String appendPerc(double value) {
        return Integer.toString((int) value) + "%";
    }

    public static double removeSign(String s, char c) {
        StringBuilder result = new StringBuilder(s.length());
        String[] words;
        if (c == '%') {
            words = s.split("%");
        } else {
            words = s.split(",");
        }

        for (int i = 0, l = words.length; i < l; ++i) {
            if (i > 0) {
                result.append("");
            }
            result.append(Character.toUpperCase(words[i].charAt(0)))
                    .append(words[i].substring(1));

        }
        return Double.parseDouble(result.toString());
    }

    public static int removeSignInt(String s, char c) {
        StringBuilder result = new StringBuilder(s.length());
        String[] words;
        if (c == '%') {
            words = s.split("%");
        } else {
            words = s.split(",");
        }

        for (int i = 0, l = words.length; i < l; ++i) {
            if (i > 0) {
                result.append("");
            }
            result.append(Character.toUpperCase(words[i].charAt(0)))
                    .append(words[i].substring(1));
            Double.parseDouble(result.toString());

        }
        return (int) (Double.parseDouble(result.toString()));
    }

    public static String removeSignStr(String s, char c) {
        StringBuilder result = new StringBuilder(s.length());
        String[] words;
        if (c == '%') {
            words = s.split("%");
        } else {
            words = s.split(",");
        }

        for (int i = 0, l = words.length; i < l; ++i) {
            if (i > 0) {
                result.append("");
            }
            result.append(Character.toUpperCase(words[i].charAt(0)))
                    .append(words[i].substring(1));
            Double.parseDouble(result.toString());

        }
        return Integer.toString((int) (Double.parseDouble(result.toString())));
    }

}
