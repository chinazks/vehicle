<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>layui</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/plugins/layui/css/layui.css" media="all">
</head>

<body>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>新增发放记录</legend>
</fieldset>
<form class="layui-form layui-form-pane" action="">
    <div class="layui-form-item">
        <table>
            <tr>
                <td>
                    <label class="layui-form-label">发料单位</label>
                    <div class="layui-input-inline">
                        <input type="text" name="materialIssuingUnit" autocomplete="off" maxlength="10" class="layui-input" lay-verify="require|lengthLess50">
                    </div>
                </td>
                <td>
                    <label class="layui-form-label">收料单位</label>
                    <div class="layui-input-inline">
                        <input type="text" name="materialReceiveUnit" autocomplete="off" maxlength="10" class="layui-input" lay-verify="require|lengthLess50">
                    </div>
                </td>
                <td>
                    <label class="layui-form-label">出库类别</label>
                    <div class="layui-input-inline">
                        <input type="text" name="outboundCategory" autocomplete="off" maxlength="20"  class="layui-input" lay-verify="">
                    </div>
                </td>
            </tr>
            <tr><td colspan="3" height="10"></td></tr>
            <tr>
                <td>
                    <label class="layui-form-label">器材编码</label>
                    <div class="layui-input-inline">
                        <input type="text" name="accessoriesId"  maxlength="50" autocomplete="off" class="layui-input" lay-verify="require|lengthLess50" >
                    </div>
                </td>
                <td>
                    <label class="layui-form-label">规格</label>
                    <div class="layui-input-inline">
                        <input type="text" name="specification" autocomplete="off" maxlength="10" class="layui-input" lay-verify="">
                    </div>
                </td>
                <td>
                    <label class="layui-form-label">单位</label>
                    <div class="layui-input-inline">
                        <input type="text" name="units" autocomplete="off" maxlength="10" class="layui-input" lay-verify="">
                    </div>
                </td>
            </tr>
            <tr><td colspan="3" height="10"></td></tr>
            <tr>
                <td>
                    <label class="layui-form-label">原厂编号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="orginalNumber"  autocomplete="off" maxlength="100" class="layui-input" lay-verify="" >
                    </div>
                </td>
                <td>
                    <label class="layui-form-label">出库数</label>
                    <div class="layui-input-inline">
                        <input type="number" name="deliveryNumber" autocomplete="off" maxlength="10" class="layui-input" lay-verify="require" >
                    </div>
                </td>
                <td>
                    <label class="layui-form-label">供应单价</label>
                    <div class="layui-input-inline">
                        <input type="text" id="price" name="price" autocomplete="off" maxlength="9" class="layui-input" lay-verify="require|price">
                    </div>
                </td>
            </tr>
            <tr><td colspan="3" height="10"></td></tr>
            <tr>
                <td>
                    <label class="layui-form-label">车牌号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="licensePlateNumber" maxlength="10" autocomplete="off" class="layui-input" lay-verify="require">
                    </div>
                </td>
                <td colspan="2">
                    <label class="layui-form-label">出库日期</label>
                    <div class="layui-input-inline">
                        <input type="text" name="deliveryDate" id="date" autocomplete="off" class="layui-input" lay-verify="require" readonly>
                    </div>
                </td>
                <#--<td>
                    <label class="layui-form-label">总金额</label>
                    <div class="layui-input-inline">
                        <input type="text" id="sumMoney" name="sumMoney" autocomplete="off" maxlength="100" class="layui-input" lay-verify="require|price">
                    </div>
                </td>-->
            </tr>
            <tr><td colspan="3" height="10"></td></tr>
            <tr>
                <td>
                    <label class="layui-form-label">经办人</label>
                    <div class="layui-input-inline">
                        <input type="text" name="reponsiableName" maxlength="10" autocomplete="off" class="layui-input" lay-verify="">
                    </div>
                </td>
                <td>
                    <label class="layui-form-label">车牌号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="licensePlateNumber" maxlength="10" autocomplete="off" class="layui-input" lay-verify="require">
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="unitsubmit">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            <a href="releaseRecord_list" class="layui-btn layui-btn-normal">返回</a>
        </div>
    </div>
</form>

<script src="/plugins/layui/layui.js"></script>
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
            lengthLess20:[
                /^.{1,20}$/
                ,'长度小于50'
            ],
            number:[
                /^[0-9]{0,9}$/
                ,'数量填写有误'
            ],
            price:[
                /(^[1-9]\d*(\.\d{1,2})?$)|(^0(\.\d{1,2})?$)/
                ,'价格填写有误'
            ]
        });

        //监听提交
        var $ = layui.$;
        form.on('submit(unitsubmit)', function(datas) {
            $.ajax({
                url: '/releaseRecord/insert',
                type: "POST",
                data:datas.field,
                dataType: "json",
                success: function(data){
                    if(data.code==200){
                        layer.msg(data.msg, {icon: 6});
                    }else{
                        layer.msg("新增失败", {icon: 5});
                    }
                    location.href="releaseRecord_list";
                }
            });
            return false;
        });
    });
</script>

</body>

</html>