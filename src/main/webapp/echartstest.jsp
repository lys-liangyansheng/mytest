<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <!--引入JQUERY-->
    <script src="jquery/jquery-1.11.1-min.js"></script>
    <!-- 引入 ECharts 文件 -->
    <script src="jquery/echarts/echarts.min.js"></script>
    <title>演示Echarts插件</title>
    <script type="text/javascript">
        $(function () {
            //初始化echarts实例
            var myChart=echarts.init(document.getElementById("main"));
            //alert(myChart);
            //配置图表的配置项和数据
            var option={
                title:{
                    text:'Echarts入门案例'
                },
                tooltip:{},
                legend:{
                    data:['销量']
                },
                xAxis:{
                    data:["衬衫1","羊毛衫","雪纺杉","裤子","高跟鞋","袜子"]
                },
                yAxis:{

                },
                series:[{
                    name:'销量',
                    type:'bar',
                    data:[5,20,35,10,10,20]
                }]
            };
                //使用刚才指定的配置项和数据显示图表
                myChart.setOption(option);
        });
    </script>
</head>
<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;"></div>
</body>
</html>
