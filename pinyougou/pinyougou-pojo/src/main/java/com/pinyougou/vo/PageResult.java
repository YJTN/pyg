package com.pinyougou.vo;

import java.io.Serializable;
import java.util.List;

public class PageResult implements Serializable {
    //总记录数
    private long total;
    //记录列表，?表示一个占位符与泛型符号类似，只是它的值设置之后不可再修改
    private List<?> rows;

    public PageResult(long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
