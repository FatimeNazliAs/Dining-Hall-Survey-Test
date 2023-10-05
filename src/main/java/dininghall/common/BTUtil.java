/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.common;

import dininghall.service.common.CurrencyService;
import org.springframework.security.core.context.SecurityContextHolder;
import dininghall.domain.common.Currency;
import dininghall.domain.user.LocalUserSession;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tolga
 */
@ManagedBean(name = "btUtil")
@ApplicationScoped
public class BTUtil implements Serializable {

    @ManagedProperty("#{currencyService}")
    private CurrencyService currencyService;
    private static Currency euroCurrency;


    @PostConstruct
    public void init() {
        // euro currency
        euroCurrency = currencyService.getCurrencyByCurrencyId(4);
    }

    public static LocalUserSession getLocalUserSession() {
        try {
            return (LocalUserSession) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        } catch (Exception e) {
        }

        return null;

    }

    public static String md5Maker(String original) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(original.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger("MD5Make").log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static boolean isLinkExpired(Date linkDate) {
        Date today = new Date();
        if (removeTime(today).equals(removeTime(linkDate))) {
            return false;
        }

        return true;
    }

    public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static java.sql.Timestamp getCurrentTimeStamp() {
        Date today = new Date();
        return new java.sql.Timestamp(today.getTime());
    }

    public static String nextSessionId() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(5);
    }

    public static String randomStringGenerator() {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }

        return sb.toString();
    }

    public static String randomStringGenerator(int count) {
        char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }

        return sb.toString();
    }

    public static String randomNumberGenerator(int count) {
        char[] chars = "1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }

        return sb.toString();
    }

    public static String removeTurkishCharacters(String word) {
        String[] parts = word.trim().split("\\s+");
        int i = 0;
        for (String part : parts) {
            part = part.toLowerCase(Locale.ENGLISH);
            part = part.trim();
            part = part.replace("ş", "s").replace("ğ", "g").replace("ı", "i").replace("ç", "c").replace("ü", "u").replace("ö", "o");
            part = removeSpecialCharacters(part);
            parts[i] = part;
            i++;
        }

        word = "";
        for (String part : parts) {
            word = word.concat(part + "-");
        }
        word = word + randomNumberGenerator(6);

        return word;
    }

    public static String removeSpecialCharacters(String word) {
        Pattern pt = Pattern.compile("[^-a-zA-Z0-9]");
        Matcher match = pt.matcher(word);
        while (match.find()) {
            String s = match.group();
            word = word.replaceAll("\\" + s, "");
        }

        return word;
    }

    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return simpleDateFormat.format(date);

        } catch (Exception e) {

        }

        return null;
    }

    public static int hoursDifference(Date startDate, Date endDate) {
        final int MILLI_TO_HOUR = 1000 * 60 * 60;

        return (int) (endDate.getTime() - startDate.getTime()) / MILLI_TO_HOUR;
    }

    public static String formatNumber(double number) {
        return String.format("%.2f", number);
    }

    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

}
