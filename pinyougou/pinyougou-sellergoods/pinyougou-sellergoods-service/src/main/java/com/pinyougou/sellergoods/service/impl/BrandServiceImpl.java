package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * 暴露服务
 * 也就是在注册中心注册服务名称
 */
@Service(interfaceClass = BrandService.class)
public class BrandServiceImpl extends BaseServiceImpl<TbBrand> implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<TbBrand> queryAll() {
        return brandMapper.queryAll();
    }

    @Override
    public List<TbBrand> testPage(Integer page, Integer rows) {
        //设置分页；只对紧接着查询语句生效
        PageHelper.startPage(page, rows);

        //查询
        return brandMapper.selectAll();

    }

    @Override
    public PageResult search(Integer page, Integer rows, TbBrand brand) {
        //根据品牌名称、首字母模糊查询
        //sql：select * from tb_brand where name like %?% and first_char = ?

        //设置分页
        PageHelper.startPage(page, rows);

        // 查询条件
        Example example = new Example(TbBrand.class);

        //创建查询条件对象
        Example.Criteria criteria = example.createCriteria();

        //根据首字母查询
        //if (brand.getFirstChar() != null && !"".equals(brand.getFirstChar())) {
        if (!StringUtils.isEmpty(brand.getFirstChar())) {
            criteria.andEqualTo("firstChar", brand.getFirstChar());
        }

        //根据名称模糊查询
        if (!StringUtils.isEmpty(brand.getName())) {
            criteria.andLike("name", "%" + brand.getName() + "%");
        }

        //查询
        List<TbBrand> list = brandMapper.selectByExample(example);

        PageInfo<TbBrand> pageInfo = new PageInfo<>(list);

        //返回结果
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public List<Map<String, Object>> selectOptionList() {
        return brandMapper.selectOptionList();
    }
}
