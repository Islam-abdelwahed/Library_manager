package com.example.library_manager;

public class Borrowing {
    public String BORROWER_NAME;
    public String BORROWER_ID;
    public String BOOK_SN;


    public String getBORROWER_NAME() {
        return BORROWER_NAME;
    }

    public void setBORROWER_NAME(String BORROWER_NAME) {
        this.BORROWER_NAME = BORROWER_NAME;
    }

    public String getBORROWER_ID() {
        return BORROWER_ID;
    }

    public void setBORROWER_ID(String BORROWER_ID) {
        this.BORROWER_ID = BORROWER_ID;
    }

    public String getBOOK_SN() {
        return BOOK_SN;
    }

    public void setBOOK_SN(String BOOK_SN) {
        this.BOOK_SN = BOOK_SN;
    }
}

