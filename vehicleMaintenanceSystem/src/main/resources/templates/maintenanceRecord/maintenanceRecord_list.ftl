<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>unit</title>
    <link rel="stylesheet" href="/plugins/layui/css/layui.css" media="all">
    <style type="text/css">
        .layui-table-cell{
            height:34px;
            line-height: 34px;
        }
    </style>
</head>

<body>
<div style="width:500px;height:30px;padding-left:20px;"></div>
<a href="/maintenanceRecord_insert" class="layui-btn">新增</a>
<button type="button" class="layui-btn" id="leadexcel"><i class="layui-icon"></i>上传文件</button>
<a href="#" id="excelurl" class="layui-btn">生成excel文件</a>
<#--<a href="#" id="downloadexcel" class="layui-btn">下载excel文件</a>-->
<table class="layui-hide" id="unit" lay-filter="unitdata"></table>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-primary  layui-btn-sm" lay-event="detail">查看</a>
    <a href="#" class="layui-btn  layui-btn-sm" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger  layui-btn-sm" lay-event="del">删除</a>
</script>

<script src="/plugins/layui/layui.js"></script>
<script>
    layui.use(['table','upload'], function() {
        var table = layui.table
                ,$ = layui.jquery
                ,upload = layui.upload;

        upload.render({
            elem: '#leadexcel'
            ,method: 'post'
            ,url: '/maintenanceRecord/lead'
            ,accept: 'file'
            ,exts:'xls' //上传文件后缀
            ,done: function(res){
                if(res.code == 0){
                    layer.msg("文件上传成功", {icon: 6});
                    location.reload();
                }
            }
            ,error: function(){
                layer.msg("文件上传失败", {icon: 5});
            }
        });

        //方法级渲染
        table.render({
            elem: '#unit'
            ,url: '/maintenanceRecord/list'
            ,cols: [[
                {field:'unitId', width:120, title:'单位id'}
                ,{field:'unitName', width:120, title:'单位名称'}
                ,{field:'licensePlateNumber', width:120, title:'车牌号'}
                ,{field:'driverName', width:120, title:'司机名称'}
                ,{field:'storeRoom', width:120, title:'库房号'}
                ,{field:'materialReceiveUnit', width:120, title:'使用单位'}
                ,{field:'vehicleType', width:120, title:'车辆类型'}
                ,{field:'accessoriesId', width:120, title:'配件id'}
                ,{field:'useOfAccessories', width:120, title:'配件使用情况'}
                ,{field:'lackOfAccessories', width:120, title:'配件缺少情况'}
                ,{field:'maintenancePrice', width:120, title:'维修价格'}
                ,{field:'maintenanceTime', width:120, title:'维修时间',sort: true}
                ,{field:'right', title: '操作', width:200, height:80, toolbar:"#barDemo"}
            ]]
            ,id: 'testReload'
            ,page: true
        });
        var $ = layui.$, active = {
            reload: function(){
                var demoReload = $('#demoReload');
                table.reload('testReload', {
                    where: {
                        keyword: demoReload.val()
                    }
                });
            }
        };

        //监听工具条
        table.on('tool(unitdata)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                layer.msg('ID：'+ data.id + ' 的查看操作');
                console.log(data);
                layer.open({
                    title: '详细信息'
                    ,content: '<div>单位id:'+data.unitId+'</div><div>单位名称:'+data.unitName+'</div><div>车牌号:'+data.licensePlateNumber+'</div><div>司机名称:'+data.driverName+'</div><div>库房号：'+data.storeRoom+'</div><div>使用单位：'+data.materialReceiveUnit+'</div><div>车辆类型:'+data.vehicleType+'</div>'
                + '<div>配件id:'+data.accessoriesId+'</div><div>配件使用情况:'+data.useOfAccessories+'</div><div>配件缺少情况:'+data.lackOfAccessories+'</div><div>维修价格:'+data.maintenancePrice+'</div><div>维修时间：'+data.maintenanceTime+'</div><div>备注:'+data.remark+'</div>'
            });
            } else if(obj.event === 'del'){
                layer.confirm('确定删除“'+data.unitName+'”的数据吗', function(index){
                    console.log(data);
                    $.ajax({
                        url: '/maintenanceRecord/delete',
                        type: "POST",
                        data:{"id":data.id},
                        dataType: "json",
                        success: function(data){
                            if(data.code==200){
                                obj.del();
                                layer.close(index);
                                layer.msg("删除成功", {icon: 6});
                            }else{
                                layer.msg("删除失败", {icon: 5});
                            }
                        }
                    });
                });
            } else if(obj.event === 'edit'){
                location.href="/maintenanceRecord/update/"+data.id;
            }
        });

        $('#excelurl').on('click', function(){
            layer.msg('正在生成excel', {
                icon: 16
                ,shade: 0.1
            });
            $.ajax({
                url: '/maintenanceRecord/putout',
                type: "POST",
                dataType: "json",
                success: function(data){
                    if(data.code==200){
                        $('#downloadexcel').attr("href",data.data);
                    }
                    layer.msg("已生成excel文件", {icon: 6});
                }
            });
        });


    });

</script>
</body>

</html>