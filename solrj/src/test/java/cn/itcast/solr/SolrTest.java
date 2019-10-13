package cn.itcast.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class SolrTest {

    private HttpSolrServer httpSolrServer;

    //在执行所有方法之前执行
    @Before
    public void setup(){
        //solr服务器地址
        String baseURL = "http://localhost:8080/solr";
        httpSolrServer = new HttpSolrServer(baseURL);
    }

    /**
     * 新增或者更新文档：
     * 如果文档id存在则更新，不存在则新增
     */
    @Test
    public void addOrUpdate() throws Exception {
        //创建文档
        SolrInputDocument doc = new SolrInputDocument();
        //参数1：域名，必须要与schema.xml文件中配置的域名称一致
        //参数2：域值
        doc.setField("id", "7920226");
        doc.setField("item_title", "333 vivo Z1极光特别版 新一代全面屏AI双摄手机 4GB+64GB 移动联通电信全网通4G手机");
        doc.setField("item_catalog_name", "手机");
        doc.setField("item_price", "1298");
        doc.setField("item_image", "https://item.jd.com/7920226.html");

        //保存
        httpSolrServer.add(doc);

        //提交
        httpSolrServer.commit();
    }
    /**
     * 根据id删除文档：
     */
    @Test
    public void deleteById() throws Exception {
        //删除
        httpSolrServer.deleteById("7920226");

        //提交
        httpSolrServer.commit();
    }

    /**
     * 删除全部
     */
    @Test
    public void deleteByQuery() throws Exception {
        //删除
        httpSolrServer.deleteByQuery("*:*");

        //提交
        httpSolrServer.commit();
    }

    /**
     * 根据条件查询
     */
    @Test
    public void search() throws Exception {

        //创建查询对象
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("item_title:极光 AND item_price:[1000 TO 1300}");

        //查询
        QueryResponse queryResponse = httpSolrServer.query(solrQuery);

        SolrDocumentList results = queryResponse.getResults();

        //处理返回结果
        System.out.println("符合查询条件的总记录数为：" + results.getNumFound());
        for (SolrDocument solrDocument : results) {
            System.out.println("id = " + solrDocument.get("id"));
            System.out.println("item_title = " + solrDocument.get("item_title"));
            System.out.println("item_catalog_name = " + solrDocument.get("item_catalog_name"));
            System.out.println("item_price = " + solrDocument.get("item_price"));
            System.out.println("item_image = " + solrDocument.get("item_image"));
        }

    }

    /**
     * 根据条件查询
     */
    @Test
    public void searchInHighlight() throws Exception {

        //创建查询对象
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("item_title:极光 AND item_price:[1000 TO 1300}");

        //设置高亮域名
        solrQuery.addHighlightField("item_title");
        //高亮的起始标签
        solrQuery.setHighlightSimplePre("<font style='color:red'>");
        //高亮的结束标签
        solrQuery.setHighlightSimplePost("</font>");

        //查询
        QueryResponse queryResponse = httpSolrServer.query(solrQuery);

        SolrDocumentList results = queryResponse.getResults();

        /**
         * 处理高亮的结果;结构如下：
         *  "highlighting": {
         *     "7920226": {
         *       "item_title": [
         *         "222 vivo Z1<font style='color:red'>极光</font>特别版 新一代全面屏AI双摄手机 4GB+64GB 移动联通电信全网通4G手机"
         *       ]
         *     }
         *   }
         */
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

        //处理返回结果
        System.out.println("符合查询条件的总记录数为：" + results.getNumFound());
        for (SolrDocument solrDocument : results) {
            System.out.println("id = " + solrDocument.get("id"));
            System.out.println("item_title = " + solrDocument.get("item_title"));
            //高亮标题
            String title = highlighting.get(solrDocument.get("id").toString()).get("item_title").get(0);

            System.out.println("高亮标题：" + title);

            System.out.println("item_catalog_name = " + solrDocument.get("item_catalog_name"));
            System.out.println("item_price = " + solrDocument.get("item_price"));
            System.out.println("item_image = " + solrDocument.get("item_image"));
        }

    }



    /**
     * 根据条件查询
     */
    @Test
    public void searchWithSolrCore() throws Exception {

        httpSolrServer = new HttpSolrServer("http://localhost:8080/solr/collection2");

        //创建查询对象
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("item_title:极光 AND item_price:[1000 TO 1300}");

        //查询
        QueryResponse queryResponse = httpSolrServer.query(solrQuery);

        SolrDocumentList results = queryResponse.getResults();

        //处理返回结果
        System.out.println("符合查询条件的总记录数为：" + results.getNumFound());
        for (SolrDocument solrDocument : results) {
            System.out.println("id = " + solrDocument.get("id"));
            System.out.println("item_title = " + solrDocument.get("item_title"));
            System.out.println("item_catalog_name = " + solrDocument.get("item_catalog_name"));
            System.out.println("item_price = " + solrDocument.get("item_price"));
            System.out.println("item_image = " + solrDocument.get("item_image"));
        }

    }

}
