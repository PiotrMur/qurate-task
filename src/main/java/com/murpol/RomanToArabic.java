package com.murpol;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntPredicate;

public class RomanToArabic {


    private RomanToArabic() {

    }

    static int convertRomanNumbersToArabic(String romanNumber) {

        romanNumber = romanNumber.toUpperCase();
        char[] enteredByUserRomanSymbols = romanNumber.toCharArray();
        int arabicNumber = 0;

        checkIfIllegalSymbolsAreUsed(romanNumber, enteredByUserRomanSymbols);
        checkIfIncorrectPatternsOfRomanSymbolsAreUsed(enteredByUserRomanSymbols, romanNumber);


        for (int i = 0; i < enteredByUserRomanSymbols.length; i++) {

            if (i < enteredByUserRomanSymbols.length - 1) {

                if (enteredByUserRomanSymbols[i] == 'I' && enteredByUserRomanSymbols[i + 1] == 'V') {
                    arabicNumber += 4;
                    i++;
                } else if (enteredByUserRomanSymbols[i] == 'I' && enteredByUserRomanSymbols[i + 1] == 'X') {
                    arabicNumber += 9;
                    i++;
                } else if (enteredByUserRomanSymbols[i] == 'X' && enteredByUserRomanSymbols[i + 1] == 'L') {
                    arabicNumber += 40;
                    i++;
                } else if (enteredByUserRomanSymbols[i] == 'X' && enteredByUserRomanSymbols[i + 1] == 'C') {
                    arabicNumber += 90;
                    i++;
                } else if (enteredByUserRomanSymbols[i] == 'C' && enteredByUserRomanSymbols[i + 1] == 'D') {
                    arabicNumber += 400;
                    i++;
                } else if (enteredByUserRomanSymbols[i] == 'C' && enteredByUserRomanSymbols[i + 1] == 'M') {
                    arabicNumber += 900;
                    i++;
                } else {

                    arabicNumber = getArabicNumber(enteredByUserRomanSymbols, arabicNumber, i);
                }
            } else {
                arabicNumber = getArabicNumber(enteredByUserRomanSymbols, arabicNumber, i);

            }
        }
        return arabicNumber;
    }

    private static int getArabicNumber(char[] enteredByUserRomanSymbols, int arabicNumber, int i) {
        switch (enteredByUserRomanSymbols[i]) {
            case 'M' -> arabicNumber += 1000;
            case 'D' -> arabicNumber += 500;
            case 'C' -> arabicNumber += 100;
            case 'L' -> arabicNumber += 50;
            case 'X' -> arabicNumber += 10;
            case 'V' -> arabicNumber += 5;
            case 'I' -> arabicNumber += 1;
        }
        return arabicNumber;
    }


    public static void checkIfIllegalSymbolsAreUsed(String romanNumber, char[] enteredByUserRomanSymbols) {
        int counter = 0;
        char[] romanLegalSymbols = {'I', 'V', 'X', 'L', 'C', 'D', 'M'};
        for (char enteredRomanSymbol : enteredByUserRomanSymbols) {
            for (char romanLegalSymbol : romanLegalSymbols) {
                if (enteredRomanSymbol == romanLegalSymbol) {
                    counter += 1;
                    break;
                }
            }
        }
        if (counter != enteredByUserRomanSymbols.length) {
            throw new IllegalSymbolsUsedException(romanNumber);
        }
    }

    static IntPredicate checkIfIncorrectPatternsOfRomanSymbolsAreUsed(char[] singleRomanSymbols, String romanNumber) {
        Map<Character, Integer> characterOccurrenceAmount = new HashMap<>();
        characterOccurrenceAmount.put('I', 0);
        characterOccurrenceAmount.put('V', 0);
        characterOccurrenceAmount.put('X', 0);
        characterOccurrenceAmount.put('L', 0);
        characterOccurrenceAmount.put('C', 0);
        characterOccurrenceAmount.put('D', 0);
        characterOccurrenceAmount.put('M', 0);

        for (Character romanSymbol : singleRomanSymbols) {
            for (Character mapKey : characterOccurrenceAmount.keySet()) {
                if (romanSymbol.equals(mapKey)) {
                    characterOccurrenceAmount.put(mapKey, characterOccurrenceAmount.get(mapKey) + 1);
                }
            }
        }

        checkIfIllegalCombinationOfDoubleSignsOccurs(romanNumber, characterOccurrenceAmount);


        checkIfThereAreTooManyTheSameSigns(romanNumber, characterOccurrenceAmount);
        return null;
    }

    private static void checkIfThereAreTooManyTheSameSigns(String romanNumber, Map<Character, Integer> characterOccurrenceAmount) {
        Optional<Integer> areThereFourTheSameSymbols = characterOccurrenceAmount.values().stream().filter(a -> a >= 4).findFirst();
        if (areThereFourTheSameSymbols.isPresent()) {
            throw new IllegalRomanNumberException(romanNumber);
        }
    }

    private static void checkIfIllegalCombinationOfDoubleSignsOccurs(String romanNumber, Map<Character, Integer> characterOccurrenceAmount) {
        if (characterOccurrenceAmount.get('V') >= 2 || characterOccurrenceAmount.get('L') >= 2 || characterOccurrenceAmount.get('D') >= 2) {
            throw new MoreThanOneVLDSignsUsedException(romanNumber);
        }
    }

    static class MoreThanOneVLDSignsUsedException extends RuntimeException {
        public MoreThanOneVLDSignsUsedException(String romanNumber) {
            super("This number [" + romanNumber + "] is incorrect. Using two or more of 'V', 'L' or 'D' symbols is illegal in Roman numeric system");
        }
    }

    static class IllegalRomanNumberException extends RuntimeException {
        public IllegalRomanNumberException(String romanNumber) {
            super("This number [" + romanNumber + "] is incorrect. Four same symbols cannot be used in Roman numeric system");
        }
    }

    static class IllegalSymbolsUsedException extends RuntimeException {
        public IllegalSymbolsUsedException(String romanNumber) {
            super("This number [" + romanNumber + "] is incorrect. Illegal symbols outside of Roman system were used");
        }
    }
}
