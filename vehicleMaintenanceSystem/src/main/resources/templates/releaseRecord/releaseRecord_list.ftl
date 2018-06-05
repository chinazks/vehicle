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
<a href="/releaseRecord_insert" class="layui-btn">新增</a>
<button type="button" class="layui-btn" id="leadexcel"><i class="layui-icon"></i>上传文件</button>
<a href="#" id="excelurl" class="layui-btn">生成excel文件</a>
<a href="#" id="downloadexcel" class="layui-btn">下载excel文件</a>
<table class="layui-hide" id="unit" lay-filter="unitdata"></table>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-primary  layui-btn-sm" lay-event="detail">查看</a>
    <#--<a href="#" class="layui-btn  layui-btn-sm" lay-event="edit">编辑</a>-->
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
            ,url: '/releaseRecord/lead'
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
            ,url: '/releaseRecord/list'
            ,cols: [[
                {field:'materialIssuingUnit', width:120, title:'发料单位'}
                ,{field:'materialReceiveUnit', width:120, title:'收料单位'}
                ,{field:'outboundCategory', width:120, title:'出库类别'}
                ,{field:'accessoriesId', width:120, title:'器材编码'}
                ,{field:'specification', width:120, title:'规格'}
                ,{field:'units', width:120, title:'单位'}
                ,{field:'orginalNumber', width:120, title:'原厂编号'}
                ,{field:'deliveryNumber', width:120, title:'出库数'}
                ,{field:'price', width:120, title:'供应单价'}
                ,{field:'licensePlateNumber', width:120, title:'车牌号'}
                ,{field:'deliveryDate', width:120, title:'出库日期',sort: true}
                ,{field:'sumMoney', width:120, title:'总金额'}
                ,{field:'reponsiableName', width:120, title:'经办人'}
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
                    ,content: '<div>发料单位:'+data.materialIssuingUnit+'</div><div>收料单位:'+data.materialReceiveUnit+'</div><div>出库类别:'+data.outboundCategory+'</div><div>器材编码:'+data.accessoriesId+'</div><div>规格：'+data.specification+'</div><div>单位:'+data.units+'</div>'
                    + '<div>原厂编号:'+data.orginalNumber+'</div><div>出库数:'+data.deliveryNumber+'</div><div>供应单价:'+data.price+'</div><div>车牌号:'+data.licensePlateNumber+'</div><div>出库日期：'+data.deliveryDate+'</div><div>总金额:'+data.sumMoney+'</div>'
                    +'<div>经办人：'+data.reponsiableName+'</div><div>唯一标识:'+data.uuid+'</div>'
                });
            } else if(obj.event === 'del'){
                layer.confirm('确定删除“'+data.unitName+'”的数据吗', function(index){
                    console.log(data);
                    $.ajax({
                        url: '/releaseRecord/delete',
                        type: "POST",
                        data:{"id":data.id},
                        dataType: "json",
                        success: function(data){
                            if(data.code==200){
                                obj.del();
                                layer.close(index);
                                layer.msg("删除成功", {icon: 6});
                                location.reload();
                            }else{
                                layer.msg("删除失败", {icon: 5});
                            }
                        }
                    });
                });
            } else if(obj.event === 'edit'){
                location.href="/releaseRecord/update/"+data.id;

            }
        });

        $('#excelurl').on('click', function(){
            layer.msg('正在生成excel', {
                icon: 16
                ,shade: 0.1
            });
            $.ajax({
                url: '/releaseRecord/putout',
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