package cn.itcast.dao;

import cn.itcast.pojo.Book;

import java.util.List;

public interface BookDao {

    /**
     * 查询图书列表
     * @return 列表
     */
    List<Book> queryBookList();
}
