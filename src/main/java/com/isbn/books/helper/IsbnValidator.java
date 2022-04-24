package com.isbn.books.helper;

import org.springframework.stereotype.Service;

@Service
public class IsbnValidator {

    /**
     *
     * @param isbn value from data file
     * @return if ISBN is valid it will return valid ISBN, return empty in case invalid ISBN
     * @throws Exception when it contains invalid ISBN
     */
    public String validateISBN(String isbn) throws Exception {
        isbn = isbn.replace("-", "");
        if((isbn.length() == 10) || (isbn.length() == 13)) {
            isbn = isbnValidator(isbn.toCharArray()) ? isbn : "";
        } else {
            throw new Exception("Invalid ISBN length, it must be 10 or 13 digit long.");
        }
        return isbn;
    }

    /**
     *
     * @param isbnChars as iban chars
     * @return boolean if ISBN is valid or not
     */
    private boolean isbnValidator(char[] isbnChars) {
        int index = 0;
        int reminder;
        int isbnNumericValue;
        int isbnSegmentSum = 0;
        int isbnLength = isbnChars.length;

        while(isbnChars.length > index) {
            reminder = (index % 2) == 0 ? 1 : 3;
            isbnNumericValue = Character.getNumericValue(isbnChars[index]);
            isbnSegmentSum += isbnChars.length == 13 ? isbnNumericValue * reminder : isbnNumericValue * isbnLength;
            isbnLength--;
            index++;
        }
        return isbnChars.length == 13 ? isbnSegmentSum % 10 == 0 : isbnSegmentSum % 11 == 0;
    }
}
