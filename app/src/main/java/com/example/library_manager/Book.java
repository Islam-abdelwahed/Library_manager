package com.example.library_manager;

public class Book {

        public String BOOK_SN="null";
        public int BOOK_ID=0;
        public String BOOK_NAME="null";
        public String AUTHOR_NAME="null";
        public int BOOK_COPIES=0;
        public  byte[] BOOK_IMAGE=null;

    public byte[] getBOOK_IMAGE() {return BOOK_IMAGE;}

    public void setBOOK_IMAGE(byte[] BOOK_IMAGE) {this.BOOK_IMAGE = BOOK_IMAGE;}

    public String getBOOK_NAME() {
            return BOOK_NAME;
        }

        public void setBOOK_NAME(String BOOK_NAME) {
            this.BOOK_NAME = BOOK_NAME;
        }

        public String getAUTHOR_NAME() {
            return AUTHOR_NAME;
        }

        public void setAUTHOR_NAME(String AUTHOR_NAME) {
            this.AUTHOR_NAME = AUTHOR_NAME;
        }

        public int getBOOK_COPIES() {return BOOK_COPIES;}

        public void setBOOK_COPIES(int BOOK_COPIES) {
            this.BOOK_COPIES = BOOK_COPIES;
        }

        public int getBOOK_ID() {
            return BOOK_ID;
        }

        public void setBOOK_ID(int BOOK_ID) {
            this.BOOK_ID = BOOK_ID;
        }

        public String getBOOK_SN() {
            return BOOK_SN;
        }

        public void setBOOK_SN(String BOOK_SN) {
            this.BOOK_SN = BOOK_SN;
        }
}