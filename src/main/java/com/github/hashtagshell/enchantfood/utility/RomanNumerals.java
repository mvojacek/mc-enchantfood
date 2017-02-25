package com.github.hashtagshell.enchantfood.utility;

import java.util.TreeMap;

public class RomanNumerals
{
    private static final TreeMap<Integer, String> mapBase     = new TreeMap<>();
    private static final TreeMap<Integer, String> mapVinculum = new TreeMap<>();

    static
    {
        mapBase.put(1000, "M"); // regular
        mapBase.put(999, "IM");
        mapBase.put(995, "VM");
        mapBase.put(990, "XM");
        mapBase.put(950, "LM");
        mapBase.put(900, "CM");
        mapBase.put(500, "D");
        mapBase.put(499, "ID");
        mapBase.put(490, "XD");
        mapBase.put(450, "LD");
        mapBase.put(400, "CD");
        mapBase.put(100, "C");
        mapBase.put(99, "IC");
        mapBase.put(95, "VC");
        mapBase.put(90, "XC");
        mapBase.put(50, "L");
        mapBase.put(49, "IL");
        mapBase.put(45, "VL");
        mapBase.put(40, "XL");
        mapBase.put(10, "X");
        mapBase.put(9, "IX");
        mapBase.put(5, "V");
        mapBase.put(4, "IV");
        mapBase.put(1, "I");

        mapVinculum.put(1000000, "M^"); // vinculum
        mapVinculum.put(999000, "I^M^");
        mapVinculum.put(995000, "V^M^");
        mapVinculum.put(990000, "X^M^");
        mapVinculum.put(950000, "L^M^");
        mapVinculum.put(900000, "C^M^");
        mapVinculum.put(500000, "D^");
        mapVinculum.put(499000, "I^D^");
        mapVinculum.put(490000, "X^D^");
        mapVinculum.put(450000, "L^D^");
        mapVinculum.put(400000, "C^D^");
        mapVinculum.put(100000, "C^");
        mapVinculum.put(99000, "I^C^");
        mapVinculum.put(95000, "V^C^");
        mapVinculum.put(90000, "X^C^");
        mapVinculum.put(50000, "L^");
        mapVinculum.put(49000, "I^L^");
        mapVinculum.put(45000, "V^L^");
        mapVinculum.put(40000, "X^L^");
        mapVinculum.put(10000, "X^");
        mapVinculum.put(9000, "I^X^");
        mapVinculum.put(5000, "V^");
        mapVinculum.put(4000, "I^V^");
    }

    public static String toRoman(int number)
    {
        if (number == 0) return "0";
        return number < 0
               ? "-" + toRoman(-number, -number >= 4000)
               : toRoman(number, number >= 4000);
    }

    private static String toRoman(int number, boolean vinculum)
    {
        TreeMap<Integer, String> map = vinculum ? mapVinculum : mapBase;
        int l = map.floorKey(number);
        if (number == l)
            return map.get(number);
        return map.get(l) + toRoman(number - l);
    }
}
