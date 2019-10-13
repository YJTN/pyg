//定义处理器
app.controller("brandController", function ($scope,$controller, brandService) {

    //继承其它的controller；传递$scope之后baseController就有了与brandController的上下文
    $controller("baseController", {$scope:$scope});

    //加载数据
    $scope.findAll = function () {
        //访问后台加载品牌列表
        brandService.findAll().success(function (response) {
            //将返回的品牌json列表对象设置到一个上下文变量中，准备被遍历
            $scope.list = response;
        }).error(function () {
            alert("加载数据失败！");
        });
    };

    //根据分页信息查询
    $scope.findPage = function (page, rows) {
        brandService.findPage(page, rows).success(function (response) {
            //更新列表数据 response --->分页结果对象PageResult
            $scope.list = response.rows;

            //更新分页插件的总记录数，好让该插件重新计算总页数和当前页
            $scope.paginationConf.totalItems = response.total;

        }).error(function () {
            alert("加载数据失败！");
        });

    };

    //保存
    $scope.save = function () {
        var obj;

        if($scope.entity.id != null){
            obj = brandService.update($scope.entity);
        } else {
            obj = brandService.add($scope.entity);
        }

        //post的参数1：请求地址，post的参数2：请求参数json格式字符串
        obj.success(function (response) {
            //response表示返回的结果对象Result(success,message)
            if(response.success){
                //刷新列表
                $scope.reloadList();
            } else {
                alert(response.message);
            }
        });

    };

    //根据id查询
    $scope.findOne = function (id) {
        //发送请求到后台根据id获取品牌数据
        brandService.findOne(id).success(function (response) {
            $scope.entity = response;
        });
    };


    $scope.delete = function () {
        //判断是否选择了
        if ($scope.selectedIds.length == 0) {
            alert("请先选择要删除的记录之后再删除！");
            return;
        }

        //真的要删除吗？如果要删除则发送请求到后台删除数据
        //confirm如果点击了确定则返回true，否则false
        if(confirm("确定要删除选择了的那些记录吗？")){
            brandService.delete($scope.selectedIds).success(function (response) {
                if(response.success){
                    //刷新列表
                    $scope.reloadList();
                } else {
                    alert(response.message);
                }
            });
        }
    };

    //初始化查询条件
    $scope.searchEntity = {};

    //条件查询
    $scope.search = function (page, rows) {
        brandService.search(page, rows, $scope.searchEntity).success(function (response) {
            //response是一个分页结果对象PageResult
            $scope.list = response.rows;

            //更新分页插件的总记录数，好让该插件重新计算总页数和当前页
            $scope.paginationConf.totalItems = response.total;
        });

    };

});
