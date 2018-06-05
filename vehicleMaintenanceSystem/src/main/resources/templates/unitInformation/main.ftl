<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>unit</title>
    <link rel="stylesheet" href="./plugins/layui/css/layui.css" media="all">
	<style type="text/css">
	.layui-table-cell{
	    height:34px;
	    line-height: 34px;
	}
	</style>
</head>

<body>
<div style="width:500px;height:30px;padding-left:20px;"></div>

<div class="demoTable">
<a href="addunitinformation" class="layui-btn">新增</a>
<button type="button" class="layui-btn" id="leadexcel"><i class="layui-icon"></i>上传文件</button>
  搜索ID：
  <div class="layui-inline">
    <input class="layui-input" name="id" id="demoReload" autocomplete="off">
  </div>
  <button class="layui-btn" data-type="reload">搜索</button>
  <a href="#" id="excelurl" class="layui-btn">生成excel文件</a>
<a href="#" id="downloadexcel" class="layui-btn">下载excel文件</a>
</div>

<table class="layui-hide" id="unit" lay-filter="unitdata"></table>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-primary  layui-btn-sm" lay-event="detail">查看</a>
    <a href="#" class="layui-btn  layui-btn-sm" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger  layui-btn-sm" lay-event="del">删除</a>
</script>

<script src="./plugins/layui/layui.js"></script>
 <script>
    layui.use(['table','upload'], function() {
        var table = layui.table
        ,$ = layui.jquery
  		,upload = layui.upload;
       
         upload.render({
		    elem: '#leadexcel'
		    ,method: 'post'
		    ,url: '/unitInformation/lead'
		    ,accept: 'file'
		    ,exts:'xls' //上传文件后缀
		    ,done: function(res){ 
			    if(res.code == 0){
			   	 	 layer.msg("文件上传成功", {icon: 6});
			    }
			 }
		    ,error: function(){
		     	layer.msg("文件上传失败", {icon: 5});
		    }
		  });
        
    //方法级渲染
    table.render({
        elem: '#unit'
        ,url: '/unitInformation/list/unitinformations'
        ,cols: [[
        	{field:'unitId', width:150, title:'单位id'}
            ,{field:'unitName', width:150, title:'单位名称'}
            ,{field:'equipmentName', width:150, title:'装备名称'}
            ,{field:'dispensingTime', width:150, title:'配发时间'}
            ,{field:'stockQuantity', width:80, title:'数量',sort: true}
            ,{field:'technicalStatus', width:150, title:'技术状况'}
            ,{field:'right', title: '操作', width:250, height:80, toolbar:"#barDemo"}
        ]]
        ,id: 'testReload'
        ,page: true
    });
        var $ = layui.$, active = {
		    reload: function(){
		      var demoReload = $('#demoReload'); 
		      //执行重载
		      table.reload('testReload', {
		        page: {
		          curr: 1 //重新从第 1 页开始
		        }
		        ,where: {
		            id: demoReload.val()
		        }
		      });
		    }
		  };
  
	  $('.demoTable .layui-btn').on('click', function(){
	    var type = $(this).data('type');
	    active[type] ? active[type].call(this) : '';
	  });

    //监听工具条
    table.on('tool(unitdata)', function(obj){
        var data = obj.data;
        if(obj.event === 'detail'){
            layer.msg('ID：'+ data.id + ' 的查看操作');
            layer.open({
  				title: '详细信息'
 				 ,content: '<div>单位id:'+data.unitId+'</div><div>单位名称:'+data.unitName+'</div><div>装备名称:'+data.equipmentName+'</div><div>配发时间:'+data.dispensingTime+'</div><div>数量：'+data.stockQuantity+'</div><div>技术状况:'+data.technicalStatus+'</div>'
				});   
        } else if(obj.event === 'del'){
            layer.confirm('确定删除“'+data.unitName+'”的数据吗', function(index){
                console.log(data);
                $.ajax({
                    url: '/unitInformation/deleteunitinformation',
                    type: "POST",
                    data:{"id":data.id},
                    dataType: "json",
                    success: function(data){
                        if(data.code==1){
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
        
      		//弹出窗编辑，已注释
         /* layer.open({
			 type: 1,
			 title:'编辑',
			 skin: 'layui-layer-rim', //加上边框
			 area: ['500px', '500'], //宽高
			 content: '<form class="layui-form" action=""><table class="layui-table">'+
			 '<tr><td>单位名称</td><td><input type="text" name="unitId" lay-verify="required" class="layui-input"></td><tr>'+
			 '<tr><td>装备名称</td><td><input type="text" name="unitId" lay-verify="required" class="layui-input"></td><tr>'+
			 '<tr><td>配发时间</td><td><input type="text" name="unitId" lay-verify="required" class="layui-input"></td><tr>'+
			 '<tr><td>数量</td><td><input type="text" name="unitId" lay-verify="required" class="layui-input"></td><tr>'+
			 '<tr><td>技术状况</td><td><input type="text" name="unitId" lay-verify="required" class="layui-input"></td><tr></table></form>'
			});*/
			location.href="/updateunitinformation/"+data.id;
        }
    });  

	$('#excelurl').on('click', function(){
		layer.msg('正在生成excel', {
  			icon: 16
  			,shade: 0.1
		});
		$.ajax({
        	url: '/unitInformation/putout',
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