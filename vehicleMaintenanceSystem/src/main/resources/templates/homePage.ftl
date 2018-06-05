<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>车辆维修管理系统</title>
    <link rel="stylesheet" href="./plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="./plugins/font-awesome/css/font-awesome.min.css" media="all">
    <link rel="stylesheet" href="./src/css/app.css" media="all">
</head>

<body>
    <div class="layui-layout layui-layout-admin kit-layout-admin">
        <div class="layui-header">
            <div class="layui-logo">车辆维修管理系统</div>
            <div class="layui-logo kit-logo-mobile">K</div>
           
            <ul class="layui-nav layui-layout-right kit-nav">
                <li class="layui-nav-item">
                    <a href="javascript:;">
                        <img src="http://m.zhengjinfan.cn/images/0.jpg" class="layui-nav-img">${loginName}
                    </a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;">基本资料</a></dd>
                        <dd><a href="javascript:;">修改密码</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item"><a href="login"><i class="fa fa-sign-out" aria-hidden="true"></i> 注销</a></li>
            </ul>
        </div>

        <div class="layui-side layui-bg-black kit-side">
            <div class="layui-side-scroll">
                <div class="kit-side-fold"><i class="fa fa-navicon" aria-hidden="true"></i></div>
                <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
                <ul class="layui-nav layui-nav-tree" lay-filter="kitNavbar" kit-navbar>
                    <li class="layui-nav-item">
                        <a class="" href="javascript:;"><i class="fa fa-plug" aria-hidden="true"></i><span>基础信息配置</span></a>
                        <dl class="layui-nav-child">
                            <dd><a href="javascript:;" kit-target data-options="{url:'user',icon:'&#xe770;',title:'配置用户',id:'1'}"><i class="layui-icon">&#xe770;</i><span>配置用户</span></a></dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item">
                        <a class="" href="javascript:;"><i class="fa fa-plug" aria-hidden="true"></i><span> 单位模块</span></a>
                        <dl class="layui-nav-child">
                            <dd><a href="javascript:;" kit-target data-options="{url:'units',icon:'&#xe770;',title:'单位信息',id:'1'}"><i class="layui-icon">&#xe770;</i><span> 单位信息</span></a></dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item layui-nav-itemed">
                        <a href="javascript:;"><i class="fa fa-plug" aria-hidden="true"></i><span> 车辆模块</span></a>
                        <dl class="layui-nav-child">
                            <dd><a href="javascript:;" kit-target data-options="{url:'managecar',icon:'&#xe66c;',title:'车辆管理',id:'6'}"><i class="layui-icon">&#xe66c;</i><span> 车辆管理</span></a></dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'/maintenanceRecord_list',icon:'&#xe631;;',title:'维修管理',id:'7'}"><i class="layui-icon">&#xe631;</i><span> 维修管理</span></a></dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'/equipmenetMmanagement_list',icon:'&#xe639;',title:'器材管理',id:'8'}"><i class="layui-icon">&#xe639;</i><span> 器材管理</span></a></dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'/releaseRecord_list',icon:'&#xe770;',title:'发放记录',id:'1'}"><i class="layui-icon">&#xe770;</i><span>发放记录</span></a></dd>
                        </dl>
                    </li>
                </ul>
            </div>
        </div>
        <div class="layui-body" id="container">
            <!-- 内容主体区域 -->
            <div style="padding: 15px;">主体内容加载中,请稍等...</div>
        </div>

        <div class="layui-footer">
            <!-- 底部固定区域 -->
            2018 &copycopyright by
            <a href="#">czy&zk</a>

        </div>
    </div>
    <script src="./plugins/layui/layui.js"></script>
    <script>
        var message;
        layui.config({
            base: 'src/js/'
        }).use(['app', 'message'], function() {
            var app = layui.app,
                $ = layui.jquery,
                layer = layui.layer;
            //将message设置为全局以便子页面调用
            message = layui.message;
            //主入口
            app.set({
                type: 'iframe'
            }).init();
        });
    </script>
</body>

</html>