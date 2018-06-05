<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>layui</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all">
</head>

<body>
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>编辑unit信息</legend>
    </fieldset>
	<form class="layui-form layui-form-pane" lay-filter="example" action="">
	<div class="layui-form-item">
	<input type="text" name="id" autocomplete="off" class="layui-input" style="display:none">
	<table>
		<tr>
			<td>
				<label class="layui-form-label">单位ID</label>
				<div class="layui-input-inline">
	 				<select name="unitid" lay-verify="required">
				        <option value=""></option>
				        <option value="101">单位1</option>
				        <option value="102">单位2</option>
				        <option value="103">单位3</option>
				        <option value="104">单位4</option>
				        <option value="105">单位5</option>
				     </select>
				 </div>
			</td>
			<td>
				<label class="layui-form-label">装备型号</label>
                <div class="layui-input-inline">
                    <input type="text" name="equipmentModel" autocomplete="off" maxlength="50" class="layui-input" lay-verify="require|lengthLess50">
                </div>
			</td>
			<td>
				<label class="layui-form-label">装备名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="equipmentName" autocomplete="off" maxlength="50" class="layui-input" lay-verify="require|lengthLess50">
                </div>
			</td>
		</tr>
		<tr><td colspan="3" height="10"></td></tr>
		<tr>
			<td>
				<label class="layui-form-label">配发时间</label>
                <div class="layui-input-inline">
                    <input type="text" name="dispensingTime" id="date" autocomplete="off" class="layui-input" lay-verify="require" readonly>
                </div>
			</td>
			<td>
				<label class="layui-form-label">数量</label>
                <div class="layui-input-inline">
                    <input type="number" name="stockQuantity" autocomplete="off" maxlength="9" class="layui-input" lay-verify="require">
                </div>
			</td>
			<td>
				<label class="layui-form-label">技术状况</label>
                <div class="layui-input-inline">
                    <input type="text" name="technicalStatus" autocomplete="off" maxlength="100" class="layui-input" lay-verify="require|technicalStatus">
                </div>
			</td>
		</tr>
	</table>
	</div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit="" lay-filter="unitsubmit">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                <a href="../main" class="layui-btn layui-btn-normal">返回</a>
            </div>
        </div>
    </form>

    <script src="../plugins/layui/layui.js"></script>
    <!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
    <script>
        layui.use(['form', 'layedit', 'laydate'], function(){
			  var form = layui.form
			  ,layer = layui.layer
			  ,layedit = layui.layedit
			  ,laydate = layui.laydate;
			  
            //日期
            laydate.render({
                elem: '#date'
            });
            //自定义验证规则
            form.verify({
                require:[
                    /^.{1,}$/
                    ,'字段必填'
                ],
                lengthLess50:[
                    /^.{1,50}$/
                    ,'长度小于50'
                ],
                technicalStatus:[
                    /^.{1,100}$/
                    ,'长度小于100'
                ]
            });
            var $ = layui.$;
			 $.ajax({
                url: '/unitInformation/updateinfo/',
                type: "POST",
                data:{"id":${updateid}},
                dataType: "json",
                async:false,  
                success: function(datas){
					form.val('example', {
					    "unitid": datas.unitId 
					    ,"id": datas.id
					    ,"equipmentModel": datas.equipmentModel
					    ,"equipmentName": datas.equipmentName
					    ,"dispensingTime": datas.dispensingTime 
					    ,"stockQuantity": datas.stockQuantity
					    ,"technicalStatus":datas.technicalStatus
					  })
                }
            });

            //监听提交
             var $ = layui.$;
        	form.on('submit(unitsubmit)', function(datas) {
            	$.ajax({
                    url: '/unitInformation/update',
                    type: "POST",
                    data:datas.field,
                    dataType: "json",
                    success: function(data){
                        if(data.code==200){
                            layer.msg(data.msg, {icon: 6});
                        }else{
                            layer.msg("修改失败", {icon: 5});
                        }
                        location.href="../main";
                    }
                });
           		return false;
        	});
        });
    </script>

</body>

</html>