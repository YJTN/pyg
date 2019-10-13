package com.pinyougou.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/brand")
//@Controller
//组合注解@Controller @ResponseBody 会对该类的所有方法生效
@RestController
public class BrandController {

    //从注册查询brandService对应的地址的代理对象
    @Reference
    private BrandService brandService;

    /**
     * 根据id查询品牌
     * @param id 品牌id
     * @return 品牌
     */
    @GetMapping("/findOne")
    public TbBrand findOne(Long id){
        return brandService.findOne(id);
    }

    /**
     * 根据品牌id数组删除品牌
     * @param ids 品牌id数组
     * @return 品牌
     */
    @GetMapping("/delete")
    public Result findOne(Long[] ids){
        try {
            brandService.deleteByIds(ids);
            return Result.ok("删除品牌成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.fail("删除品牌失败");
    }

    /**
     * 更新品牌数据
     * @param brand 品牌
     * @return 操作结果
     */
    @PostMapping("/update")
    public Result update(@RequestBody TbBrand brand){
        try {
            brandService.update(brand);
            return Result.ok("修改品牌成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.fail("修改品牌失败");
    }
    /**
     * 保存品牌数据
     * @param brand 品牌
     * @return 操作结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody TbBrand brand){
        try {
            brandService.add(brand);
            return Result.ok("新增品牌成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.fail("新增品牌失败");
    }

    /**
     * 根据分页信息分页查询品牌列表
     * @param page 页号
     * @param rows 页大小
     * @return 分页结果对象
     */
    @GetMapping("/findPage")
    public PageResult findPage(@RequestParam(value="page", defaultValue = "1") Integer page,
                               @RequestParam(value="rows", defaultValue = "10")Integer rows){
        return brandService.findPage(page, rows);
    }

    /**
     * 根据分页信息分页查询品牌列表
     * @param page 页号
     * @param rows 页大小
     * @return 品牌列表
     */
    @GetMapping("/testPage")
    public List<TbBrand> testPage(@RequestParam(value="page", defaultValue = "1") Integer page,
                                  @RequestParam(value="rows", defaultValue = "10")Integer rows){
        //return brandService.testPage(page, rows);
        return (List<TbBrand>) brandService.findPage(page, rows).getRows();
    }

    /**
     * 获取品牌列表
     * @return 品牌列表
     */
//    @ResponseBody
//    @RequestMapping(value="/findAll", method = RequestMethod.GET)
    @GetMapping("/findAll")
    public List<TbBrand> findAll(){
        //return brandService.queryAll();
        return brandService.findAll();
    }

    /**
     * 根据条件模糊分页查询
     * @param page 页号
     * @param rows 页大小
     * @param brand 查询条件对象
     * @return 分页结果
     */
    @PostMapping("/search")
    public PageResult search(@RequestParam(value="page", defaultValue = "1") Integer page,
                             @RequestParam(value="rows", defaultValue = "10") Integer rows,
                             @RequestBody TbBrand brand){
        return brandService.search(page, rows, brand);
    }

    /**
     * 查询品牌列表；格式如下：
     * [{id:'1',text:'联想'},{id:'2',text:'华为'}]
     * @return 品牌列表
     */
    @GetMapping("/selectOptionList")
    public List<Map<String, Object>> selectOptionList(){
        return brandService.selectOptionList();
    }
}
