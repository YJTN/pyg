package cn.itcast.lucene;

import cn.itcast.dao.BookDao;
import cn.itcast.dao.impl.BookDaoImpl;
import cn.itcast.pojo.Book;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IndexSearchTest {


    /**
     * 查询分析器查询
     * @throws Exception
     */
    @Test
    public void queryParser() throws Exception {
        /**
         * 参数1：默认查询的域名；当如果在查询表达式中没有指定域名的时候使用
         * 参数2：分词器
         */
        QueryParser queryParser = new QueryParser("bookname", new IKAnalyzer());

        Query query = queryParser.parse("bookname:java AND bookname:lucene");

        search(query);
    }


    /**
     * 组合查询
     * @throws Exception
     */
    @Test
    public void booleanQuery() throws Exception {
        BooleanQuery query = new BooleanQuery();
        Query query1 = NumericRangeQuery.newFloatRange("price", 80f, 100f, true, false);
        query.add(query1, BooleanClause.Occur.MUST);

        //条件2
        Query query2 = new TermQuery(new Term("bookname", "lucene"));
        query.add(query2, BooleanClause.Occur.MUST);


        search(query);
    }


    /**
     * 数值范围查询
     * @throws Exception
     */
    @Test
    public void numeriRangeQuery() throws Exception {
        /**
         * 参数1：域名
         * 参数2：下限
         * 参数3：上限
         * 参数4：是否包含下限
         * 参数5：是否包含上限
         *
         */
        //80 <= price < 100
        Query query = NumericRangeQuery.newFloatRange("price", 80f, 100f, true, false);

        search(query);
    }

    //词条查询
    @Test
    public void termQuery() throws Exception {
        Query query = new TermQuery(new Term("bookname", "lucene"));

        search(query);
    }


    /**
     * 读取索引
     */
    private void search(Query query) throws Exception {
        //创建分析器对象（Analyzer），用于分词
        //Analyzer analyzer = new StandardAnalyzer();
        Analyzer analyzer = new IKAnalyzer();
        //创建查询对象（Query）

        //创建索引库目录对象（Directory），指定索引库的位置
        Directory directory = FSDirectory.open(new File("D:\\itcast\\test\\lucene"));
        //创建索引数据读取对象（IndexReader），把索引数据读取到内存中
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建索引搜索对象（IndexSearcher），执行搜索，返回搜索的结果集TopDocs
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        //处理搜索结果
        /**
         * 参数1：查询对象，可以指定查询什么域和要查询的关键字
         * 参数2：符合查询条件的前n条数据
         */
        TopDocs topDocs = indexSearcher.search(query, 10);

        System.out.println("符合查询条件的总记录数为：" + topDocs.totalHits);

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
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


}
