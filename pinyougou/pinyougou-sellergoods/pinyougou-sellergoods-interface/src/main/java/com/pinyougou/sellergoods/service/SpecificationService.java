package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Specification;

import java.util.List;
import java.util.Map;

public interface SpecificationService extends BaseService<TbSpecification> {

    PageResult search(Integer page, Integer rows, TbSpecification specification);

    void add(Specification specification);

    /**
     * 根据规格id查询规格及其规格选项列表返回Specification
     * @param id 规格id
     * @return Specification
     */
    Specification findOne(Long id);

    /**
     * 根据规格id更新规格及删除该规格对应的所有选项，再将前端传递的规格列表数据新增到数据库表中。
     * @param specification 规格对象
     */
    void update(Specification specification);

    /**
     * 批量删除规格及其选项
     * @param ids 规格id数组
     */
    void deleteSpecificationByIds(Long[] ids);

    /**
     * 查询规格列表；格式如下：
     * [{id:'1',text:'内存大小'},{id:'2',text:'屏幕尺寸'}]
     * @return 规格列表
     */
    List<Map<String, Object>> selectOptionList();
}