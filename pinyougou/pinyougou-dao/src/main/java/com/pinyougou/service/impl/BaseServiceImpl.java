package com.pinyougou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.io.Serializable;
import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T> {

    //在spring 4.x版本之后可以使用泛型依赖注入，根据泛型类查找对应的mapper
    @Autowired
    private Mapper<T> mapper;

    @Override
    public T findOne(Serializable id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> findAll() {
        return mapper.selectAll();
    }

    @Override
    public List<T> findByWhere(T t) {
        return mapper.select(t);
    }

    @Override
    public PageResult findPage(Integer page, Integer rows) {
        //设置分页
        PageHelper.startPage(page, rows);
        //查询
        List<T> list = mapper.selectAll();
        //转换为分页信息对象
        PageInfo<T> pageInfo = new PageInfo<>(list);

        //返回分页结果对象
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public PageResult findPage(Integer page, Integer rows, T t) {
        //设置分页
        PageHelper.startPage(page, rows);
        //查询
        List<T> list = mapper.select(t);
        //转换为分页信息对象
        PageInfo<T> pageInfo = new PageInfo<>(list);

        //返回分页结果对象
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());

    }

    @Override
    public void add(T t) {
        //选择新增：如果对象的属性为null的话；那么该属性对应的表字段则不会出现则插入语句中
        // first_char 字段对应的java类属性为null；则语句为：insert into tb_brand(id, name) values(?, ?)
        mapper.insertSelective(t);
    }

    @Override
    public void update(T t) {
        //选择更新：如果对象的属性为null的话；那么该属性对应的表字段则不会出现则更新语句中
        // first_char 字段对应的java类属性为null；则语句为：update tb_brand set name=? where id = ?
        // 如果不是选择性更新；则语句为：update tb_brand set name=?, first_char=null where id = ?
        mapper.updateByPrimaryKeySelective(t);


    }

    @Override
    public void deleteByIds(Serializable[] ids) {
        if (ids != null && ids.length > 0) {
            for (Serializable id : ids) {
                mapper.deleteByPrimaryKey(id);
            }
        }
    }
}
