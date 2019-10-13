package cn.itcast.dao.impl;

import cn.itcast.dao.BookDao;
import cn.itcast.pojo.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {
    public List<Book> queryBookList() {
        List<Book> bookList = new ArrayList<Book>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            //1. 加载驱动，创建连接；
            Class.forName("com.mysql.jdbc.Driver");
            //2. 创建执行对象，查询；
            String url = "jdbc:mysql://127.0.0.1:3306/lucene_98";
            connection = DriverManager.getConnection(url, "root", "root");

            statement = connection.prepareStatement("select * from book");

            resultSet = statement.executeQuery();
            //3. 处理返回结果；
            Book book = null;
            while (resultSet.next()) {
                book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setBookname(resultSet.getString("bookname"));
                book.setPrice(resultSet.getFloat("price"));
                book.setPic(resultSet.getString("pic"));
                book.setBookdesc(resultSet.getString("bookdesc"));

                bookList.add(book);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //4. 关闭资源
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return bookList;
    }

    public static void main(String[] args) {
        BookDao bookDao = new BookDaoImpl();
        List<Book> bookList = bookDao.queryBookList();
        if (bookList != null && bookList.size() > 0) {
            for (Book book : bookList) {
                System.out.println(book);
            }
        }
    }
}
