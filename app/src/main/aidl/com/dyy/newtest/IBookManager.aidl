package com.dyy.newtest;
import com.dyy.newtest.book;
interface IBookManager {
    void addBook(in Book book);
    List<Book> getBookList();
}