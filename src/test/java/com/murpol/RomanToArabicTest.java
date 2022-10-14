package com.murpol;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RomanToArabicTest {

    @Test
    void shouldParseRomanNamesToArabic() {
        //given
        String romanNumber = "MMCDXCIX";

        //when
        int arabicNumber = RomanToArabic.convertRomanNumbersToArabic(romanNumber);
        //then
        assertThat(arabicNumber).isEqualTo(2499);
    }

    @Test
    void shouldCheckIfIllegalSymbolsAreUsed() {
        //given
        String romanNumber = "ABC";
        char[] enteredByUserRomanSymbols = romanNumber.toCharArray();
        //when

        //then
        assertThatThrownBy(() -> RomanToArabic.checkIfIllegalSymbolsAreUsed(romanNumber, enteredByUserRomanSymbols))
                .isInstanceOf(RomanToArabic.IllegalSymbolsUsedException.class);
    }

    @Test
    void shouldCheckIfSignsLimitIsExceeded() {
        String romanNumber = "IIII";
        char[] romanNumberAsACharArray = romanNumber.toCharArray();

        //then
        assertThatThrownBy(() -> RomanToArabic.checkIfIncorrectPatternsOfRomanSymbolsAreUsed(romanNumberAsACharArray, romanNumber))
                .isInstanceOf(RomanToArabic.IllegalRomanNumberException.class);
    }

    @Test
    void shouldCheckIfVLCAreUsedMoreThanOnce() {
        String romanNumber = "LL";
        char[] romanNumberAsACharArray = romanNumber.toCharArray();

        //then
        assertThatThrownBy(() -> RomanToArabic.checkIfIncorrectPatternsOfRomanSymbolsAreUsed(romanNumberAsACharArray, romanNumber))
                .isInstanceOf(RomanToArabic.MoreThanOneVLDSignsUsedException.class);
    }


}