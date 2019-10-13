app.service("uploadService",function ($http) {

    this.uploadFile = function () {
        //创建表单数据对象
        var formData = new FormData();
        //添加表单项名称file，值为前台html页面中选择的文件
        formData.append("file", file.files[0]);
        return $http({
            url:"../upload.do",
            method:"post",
            data:formData,
            headers:{"Content-Type": undefined},
            transformRequest: angular.identity
        });
    };
});