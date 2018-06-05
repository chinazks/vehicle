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
	<a href="addvehiclemanagement" class="layui-btn">新增</a>
	<button type="button" class="layui-btn" id="leadexcel"><i class="layui-icon"></i>上传文件</button>
	 <div class="layui-inline">
	 	<input class="layui-input" name="id" id="demoReload" autocomplete="off" placeholder="请输入车牌号">
     </div>
     <button class="layui-btn" data-type="reload">搜索</button>
	<a href="#" id="excelurl" class="layui-btn">生成excel文件</a>
	<a href="#" id="downloadexcel" class="layui-btn">下载excel文件</a>
</div>

<table class="layui-hide" id="managecar" lay-filter="vichetable"></table>

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
		    ,url: '/vehiclemanagement/lead'
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
            elem: '#managecar'
            ,url: '/vehiclemanagement/list/vehicles'
            ,cols: [[
                {checkbox: true, fixed: true}
                ,{field:'equipmentName', title:'装备名称', width:100}
                ,{field:'equipmentModel', title:'装备型号', width:100, sort: true}
                ,{field:'licensePlateNumber', title:'车牌号', width:100}
                ,{field:'vehicleType', title:'车型号', width:100}
                ,{field:'driverName', title:'司机名称', width:100, sort: true}
                ,{field:'unitId', title:'单位id', width:100, sort: true}
                ,{field:'remarke', title:'备注', width:100, sort: true}
                ,{field:'right', title: '操作', width:300, height:80,toolbar:"#barDemo"}
            ]]
            ,id: 'testReload'
            ,page: true
            ,height: 600
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
		            licensePlateNumber: demoReload.val()
		        }
		      });
		    }
		  };
  
	  $('.demoTable .layui-btn').on('click', function(){
	    var type = $(this).data('type');
	    active[type] ? active[type].call(this) : '';
	  });

     table.on('tool(vichetable)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                layer.open({
  				title: '详细信息'
 				 ,content: '<div>装备名称:'+data.equipmentName+'</div><div>装备型号:'+data.equipmentModel+'</div><div>车牌号:'+data.licensePlateNumber+'</div><div>车辆类型:'+data.vehicleType+'</div><div>司机名称：'+data.driverName+'</div><div>单位id:'+data.unitId+'</div><div>备注:'+data.remarke+'</div>'
				}); 
            } else if(obj.event === 'del'){
                layer.confirm('确定删除车牌号'+data.licensePlateNumber+'的车辆吗', function(index){
                    $.ajax({
                        url: "/vehiclemanagement/deletevehicle",
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
               location.href="/updatevehiclemanagement/"+data.id;
            }
        });

	$('#excelurl').on('click', function(){
		layer.msg('正在生成excel', {
  			icon: 16
  			,shade: 0.1
		});
		$.ajax({
        	url: '/vehiclemanagement/putout',
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