/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.twigasoft.templates;

/**
 *
 * @author Victor
 */
import java.sql.Timestamp;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class MinimalWorkingExample {
    static Date date = new Date(1990, 4, 28, 12, 59);

    public static String getTimestampDiff(Timestamp t) {
        final DateTime start = new DateTime(date.getTime());
        final DateTime end = new DateTime(t);
        Period p = new Period(start, end);
        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .printZeroAlways().minimumPrintedDigits(2).appendYears()
                .appendSuffix(" year", " years").appendSeparator(", ")
                .appendMonths().appendSuffix(" month", " months")
                .appendSeparator(", ").appendDays()
                .appendSuffix(" day", " days").appendSeparator(" and ")
                .appendHours().appendLiteral(":").appendMinutes()
                .appendLiteral(":").appendSeconds().toFormatter();
        return p.toString(formatter);
    }

    public static void main(String[] args) {
        String diff = getTimestampDiff(new Timestamp(2013, 3, 20, 7, 51, 0, 0));
        System.out.println(diff);
    }
}