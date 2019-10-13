app.controller("baseController", function ($scope) {

    //指定分页插件的配置参数
    // 初始化分页参数
    $scope.paginationConf = {
        currentPage:1,// 当前页号
        totalItems:0,// 总记录数
        itemsPerPage:10,// 页大小
        perPageOptions:[10, 20, 30, 40, 50],// 可选择的每页大小
        onChange: function () {// 当上述的参数发生变化了后触发
            $scope.reloadList();
        }
    };

    $scope.reloadList = function () {
        //根据分页信息查询数据
        //$scope.findPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
        $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    };

    //选择了的id数组
    $scope.selectedIds = [];

    //记录id的方法
    $scope.updateSelection = function ($event, id) {
        if($event.target.checked){
            //1、如果选中则将id加入到数组中
            $scope.selectedIds.push(id);
        } else {
            //2、如果反选则将id从数组中移除
            var index = $scope.selectedIds.indexOf(id);
            //参数1：要移除的元素索引号，参数2：要移除的个数
            $scope.selectedIds.splice(index, 1);
        }
    };

    //将一个json格式字符串的内容的某个属性的值串起来以,分隔并返回
    $scope.jsonToString = function (jsonArrayStr, key) {
        var str = "";
        var jsonArray = JSON.parse(jsonArrayStr);

        for (var i = 0; i < jsonArray.length; i++) {
            var jsonObj = jsonArray[i];
            if (str.length > 0) {
                str += "," + jsonObj[key];
            } else {
                str = jsonObj[key];
            }
        }

        return str;

    };

});
