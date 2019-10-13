package cn.itcast.lucene;

import cn.itcast.dao.BookDao;
import cn.itcast.dao.impl.BookDaoImpl;
import cn.itcast.pojo.Book;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IndexManageTest {

    /**
     * 读取索引
     */
    @Test
    public void indexSearch() throws Exception {
        //创建分析器对象（Analyzer），用于分词
        //Analyzer analyzer = new StandardAnalyzer();
        Analyzer analyzer = new IKAnalyzer();
        //创建查询对象（Query）
        /**
         * 参数1：文档中对应的域名
         * 参数2：分词器
         */
        QueryParser queryParser = new QueryParser("bookname", analyzer);

        //查询bookname中带有lucene的词条的文档数据
        Query query = queryParser.parse("lucene");

        //创建索引库目录对象（Directory），指定索引库的位置
        Directory directory = FSDirectory.open(new File("D:\\itcast\\test\\lucene"));
        //创建索引数据读取对象（IndexReader），把索引数据读取到内存中
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建索引搜索对象（IndexSearcher），执行搜索，返回搜索的结果集TopDocs
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        //设置分页数据
        //页号
        int pageNo = 2;
        //页大小
        int pageSize = 2;
        //起始索引号
        int start = (pageNo - 1) * pageSize;
        //本次到哪一个索引号结束
        int end = start + pageSize;

        //处理搜索结果
        /**
         * 参数1：查询对象，可以指定查询什么域和要查询的关键字
         * 参数2：符合查询条件的前n条数据
         */
        //TopDocs topDocs = indexSearcher.search(query, 10);
        TopDocs topDocs = indexSearcher.search(query, end);

        System.out.println("符合查询条件的总记录数为：" + topDocs.totalHits);

        if (end > topDocs.totalHits) {
            end = topDocs.totalHits;
        }

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        /*for (ScoreDoc scoreDoc : scoreDocs) {
            System.out.println("-------------------------------------");
            System.out.println("在lucene中文档的id为：" + scoreDoc.doc + "；得分为：" + scoreDoc.score);
            //根据文档id获取文档
            Document doc = indexSearcher.doc(scoreDoc.doc);
            System.out.println("id = " + doc.get("id"));
            System.out.println("bookname = " + doc.get("bookname"));
            System.out.println("price = " + doc.get("price"));
            System.out.println("pic = " + doc.get("pic"));
            System.out.println("bookdesc = " + doc.get("bookdesc"));
        }*/
        for (int i = start; i < end; i++) {
            ScoreDoc scoreDoc = scoreDocs[i];
            System.out.println("-------------------------------------");
            System.out.println("在lucene中文档的id为：" + scoreDoc.doc + "；得分为：" + scoreDoc.score);
            //根据文档id获取文档
            Document doc = indexSearcher.doc(scoreDoc.doc);
            System.out.println("id = " + doc.get("id"));
            System.out.println("bookname = " + doc.get("bookname"));
            System.out.println("price = " + doc.get("price"));
            System.out.println("pic = " + doc.get("pic"));
            System.out.println("bookdesc = " + doc.get("bookdesc"));
        }

        //释放资源
        indexReader.close();
    }


    /**
     * 写入索引
     * @throws Exception
     */
    @Test
    public void indexWrite() throws Exception {
        //采集数据
        BookDao bookDao = new BookDaoImpl();
        List<Book> bookList = bookDao.queryBookList();
        //创建文档对象（Document）
        List<Document> documentList = new ArrayList<Document>();
        Document doc = null;
        for (Book book : bookList) {
            doc = new Document();
            /**
             * 参数1：文档中的域名称（对应数据库表列，或者java属性）
             * 参数2：域值
             * 参数3：是否要存储；lucene分索引区和文档区
             *
             */
            doc.add(new TextField("id", book.getId().toString(), Field.Store.YES));
            doc.add(new TextField("bookname", book.getBookname(), Field.Store.YES));
            doc.add(new TextField("price", book.getPrice().toString(), Field.Store.YES));
            doc.add(new TextField("pic", book.getPic(), Field.Store.YES));
            doc.add(new TextField("bookdesc", book.getBookdesc(), Field.Store.YES));
            documentList.add(doc);
        }

        //创建分析器对象（Analyzer），用于分词
        //Analyzer analyzer = new StandardAnalyzer();
        Analyzer analyzer = new IKAnalyzer();
        //创建索引库的配置对象（IndexWriterConfig），配置索引库
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
        //
        //创建索引库的目录对象（Directory），指定索引库的存储位置
        File file = new File("D:\\itcast\\test\\lucene");
        Directory directory = FSDirectory.open(file);
        //创建索引库操作对象（IndexWriter），操作索引库
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        //使用IndexWriter对象，把文档对象写入索引库
        for (Document document : documentList) {
            indexWriter.addDocument(document);
        }
        //释放资源
        indexWriter.close();
    }
}
