<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>layui</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="./plugins/layui/css/layui.css" media="all">
</head>

<body>
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>新增车辆信息</legend>
    </fieldset>
	<form class="layui-form layui-form-pane" action="">
	<div class="layui-form-item">
	<table>
		<tr>
			<td>
				<label class="layui-form-label">库房号</label>
				<div class="layui-input-inline">
	 				<select name="storeRoom" lay-verify="required">
				        <option value=""></option>
				        <option value="101" selected>库房1</option>
				        <option value="102">库房2</option>

				     </select>
				 </div>
			</td>
			<td>
				<label class="layui-form-label">收料单位</label>
                <div class="layui-input-inline">
                    <input type="text" name="materialIssuingUnit" autocomplete="off" maxlength="50" class="layui-input" lay-verify="require|lengthLess50">
                </div>
			</td>
			<td>
				<label class="layui-form-label">车牌号</label>
                <div class="layui-input-inline">
                    <input type="text" name="licensePlateNumber" autocomplete="off" maxlength="50"  class="layui-input" lay-verify="require|lengthLess50">
                </div>
			</td>
		</tr>
		<tr><td colspan="3" height="10"></td></tr>
		<tr>
			<td>
				<label class="layui-form-label">车辆类型</label>
                <div class="layui-input-inline">
                    <input type="text" name="vehicleType" autocomplete="off" class="layui-input" lay-verify="require">
                </div>
			</td>
			<td>
				<label class="layui-form-label">配件id</label>
                <div class="layui-input-inline">
                    <input type="text" name="accessoriesId" autocomplete="off" maxlength="9" class="layui-input" lay-verify="require">
                </div>
			</td>
			<td>
				<label class="layui-form-label">配件名字</label>
                <div class="layui-input-inline">
                    <input type="text" name="accessoriesName" autocomplete="off" maxlength="100" class="layui-input" lay-verify="require">
                </div>
			</td>
		</tr>
		<tr><td colspan="3" height="10"></td></tr>
		<tr>
			<td>
				<label class="layui-form-label">规格</label>
                <div class="layui-input-inline">
                    <input type="text" name="specifications" autocomplete="off" maxlength="100" class="layui-input" lay-verify="require">
                </div>
			</td>
			<td>
				<label class="layui-form-label">原厂编号</label>
                <div class="layui-input-inline">
                    <input type="text" name="originalFactoryNumber" autocomplete="off" maxlength="100" class="layui-input" lay-verify="require">
                </div>
			</td>
			<td>
				<label class="layui-form-label">单位</label>
                <div class="layui-input-inline">
                    <input type="text" name="unit" autocomplete="off" maxlength="100" class="layui-input" lay-verify="require">
                </div>
			</td>
		</tr>
		<tr><td colspan="3" height="10"></td></tr>
		<tr>
			<td>
				<label class="layui-form-label">入库单价</label>
                <div class="layui-input-inline">
                    <input type="text" name="warehouseUnitPrice" autocomplete="off" maxlength="100" class="layui-input" lay-verify="require">
                </div>
			</td>
			<td>
				<label class="layui-form-label">库房总库存</label>
                <div class="layui-input-inline">
                    <input type="text" name="stock" autocomplete="off" maxlength="100" class="layui-input" lay-verify="require">
                </div>
			</td>
			<td>
				<label class="layui-form-label">货位号</label>
                <div class="layui-input-inline">
                    <input type="text" name="goodsNum" autocomplete="off" maxlength="100" class="layui-input" lay-verify="require">
                </div>
			</td>
		</tr>
		<tr><td colspan="3" height="10"></td></tr>
		<tr>
			<td colspan="3">
				<label class="layui-form-label">到货日期</label>
                <div class="layui-input-inline">
                    <input type="text" name="deliveryDate" id="date" autocomplete="off" maxlength="100" class="layui-input" lay-verify="require">
                </div>
                
			</td>
		</tr>
	</table>
	</div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit="" lay-filter="equipmentsubmit">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                <a href="managecar" class="layui-btn layui-btn-normal">返回</a>
            </div>
        </div>
    </form>

    <script src="./plugins/layui/layui.js"></script>
    <!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
    <script>
        layui.use(['form', 'layedit', 'laydate'], function() {
            var form = layui.form,
                layer = layui.layer,
                layedit = layui.layedit,
                laydate = layui.laydate;

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
            });

            //监听提交
             var $ = layui.$;
        	form.on('submit(equipmentsubmit)', function(datas) {
            	$.ajax({
                    url: '/equipmentManagement/insert',
                    type: "POST",
                    data:datas.field,
                    dataType: "json",
                    success: function(data){
                        if(data.code==200){
                            layer.msg(data.msg, {icon: 6});
                        }else{
                            layer.msg("新增失败", {icon: 5});
                        }
                        location.href="/equipmenetMmanagement_list";
                    }
                });
           		return false;
        	});
        });
    </script>

</body>

</html>