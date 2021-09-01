/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

/**
 *
 * @author kekko
 */
public class ItalianNumberTranslator {

    /**
     * Translate a given number into letters in Italian
     *
     * @param number the number to be translated
     * @return the given number into letters in Italian
     */
    public static String translate(int number) {
        if (number == 0) {
            return "zero";
        }

        return recursiveTranslate(number);
    }

    private static String recursiveTranslate(int number) {
        if (number == 0) {
            return "";
        }

        if (number <= 19) {
            String[] numbers = new String[] {"uno", "due", "tre", "quattro", "cinque","sei", "sette", "otto", "nove", "dieci",
                "undici", "dodici", "tredici","quattordici", "quindici", "sedici","diciassette", "diciotto", "diciannove"};
            
            return numbers[number - 1];
        } 
        
        if (number <= 99) {
            String[] prefixs = {"venti", "trenta", "quaranta", "cinquanta", "sessanta","settanta", "ottanta", "novanta"};
            String toReturn = prefixs[number / 10 - 2];
            
            //firstDigit is the rightmost digit of the number
            int firstDigit = number % 10;
            //if firstDigit is equals to one or eight, in italian language the last letter of prefix must be truncated 
            if (firstDigit == 1 || firstDigit == 8) {
                toReturn = toReturn.substring(0, toReturn.length() - 1);
            }
            
            return toReturn + recursiveTranslate(number % 10);
        }
        
        if (number <= 199) {
            return "cento" + recursiveTranslate(number % 100);
        }
        
        if (number <= 999) {
            int m = number % 100;
            m /= 10;
            
            String prefix = "cent";
            if (m != 8) {
                prefix = prefix + "o";
            }
            return recursiveTranslate(number / 100) + prefix + recursiveTranslate(number % 100);
        }
        
        if (number <= 1999) {
            return "mille" + recursiveTranslate(number % 1000);
        }
        
        if (number <= 999999) {
            return recursiveTranslate(number / 1000) + "mila" + recursiveTranslate(number % 1000);
        }
        
        if (number <= 1999999) {
            return "unmilione" + recursiveTranslate(number % 1000000);
        }
        
        if (number <= 999999999) {
            return recursiveTranslate(number / 1000000) + "milioni" + recursiveTranslate(number % 1000000);
        }
        
        if (number <= 1999999999) {
            return "unmiliardo" + recursiveTranslate(number % 1000000000);
        } 
        else
            return recursiveTranslate(number / 1000000000) + "miliardi" + recursiveTranslate(number % 1000000000);
    }
}
