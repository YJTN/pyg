//定义业务对象
app.service("brandService", function ($http) {

    //this表示当前的brandService对象
    this.findAll = function () {
        //访问后台加载品牌列表
        return $http.get("../brand/findAll.do");
    };

    //根据分页信息查询
    this.findPage = function (page, rows) {
        return $http.get("../brand/findPage.do?page=" + page + "&rows=" + rows);

    };

    //新增
    this.add = function (entity) {
        //post的参数1：请求地址，post的参数2：请求参数json格式字符串
        return $http.post("../brand/add.do", entity);

    };

    //修改
    this.update = function (entity) {
        //post的参数1：请求地址，post的参数2：请求参数json格式字符串
        return $http.post("../brand/update.do", entity);

    };

    //根据id查询
    this.findOne = function (id) {
        //发送请求到后台根据id获取品牌数据
        return $http.get("../brand/findOne.do?id=" + id);
    };


    this.delete = function (selectedIds) {
        return $http.get("../brand/delete.do?ids=" + selectedIds);
    };

    //条件查询
    this.search = function (page, rows, searchEntity) {
        return $http.post("../brand/search.do?page=" + page + "&rows=" + rows, searchEntity);

    };

    this.selectOptionList = function () {
        return $http.get("../brand/selectOptionList.do");

    };
});
