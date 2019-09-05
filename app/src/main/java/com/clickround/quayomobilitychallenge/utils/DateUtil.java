/*
 * Copyright (c) 2017. SmartClickTechnologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.clickround.quayomobilitychallenge.utils;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

public final class DateUtil {

    private static final DateUtil ourInstance = new DateUtil();

    public static DateUtil getInstance() {
        return ourInstance;
    }

    private DateUtil() {
    }

    /**
     * Gets current date
     *
     * @param calendar instance of a calender
     * @param format   date format eg. yyyy-MM-dd
     * @return current date
     */
    public String getDateFormatted(Calendar calendar, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    public String getDateFormatted(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(date);
    }

    /**
     * Formats date and time to relative span string eg. 1hour ago..
     *
     * @param presentTime current time
     * @param publishDate date published
     * @return char date , empty otherwise
     */
    public CharSequence formatDateTimeToRelative(String presentTime, long publishDate) {
        CharSequence newsDate = "";
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",
                    Locale.getDefault()).parse(presentTime);
            long milliseconds = date.getTime();

            newsDate = DateUtils.getRelativeTimeSpanString(publishDate,
                    milliseconds,
                    DateUtils.SECOND_IN_MILLIS);

            System.out.println(newsDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newsDate;
    }

    public String getTodayDate() {
        Date presentTime_Date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(presentTime_Date);
    }


    /**
     * Gets today date and time
     *
     * @return date and time
     */
    public String getToday() {
        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        return dateFormat.format(time);
    }

    public Date getTodayObject() {
        return Calendar.getInstance().getTime();
    }

    public String getAge(Date first, Date last) {
        return String.format("%s years %s months %s days",
                getDiffYears(first, last),
                getDiffMonths(first, last),
                getDiffDays(first, last));
    }

    public int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return Math.abs(diff);
    }

    public int getDiffMonths(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.MONTH) - a.get(Calendar.MONTH);
        if (a.get(Calendar.DATE) > b.get(Calendar.DATE)) {
            diff--;
        }
        return Math.abs(diff);
    }

    public int getDiffDays(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.DAY_OF_MONTH) - a.get(Calendar.DAY_OF_MONTH);
        return Math.abs(diff);
    }

    public Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    /**
     * Convert date/time to milliseconds
     *
     * @param srcDate date to be parsed
     * @return date in millisecond , 0 otherwise
     */
    public long getDateInMillis(String srcDate) {
        SimpleDateFormat desiredFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        try {
            Date date = desiredFormat.parse(srcDate);
            return date.getTime();
        } catch (ParseException e) {
            Timber.d("Exception while parsing date: %s ", e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }
}
