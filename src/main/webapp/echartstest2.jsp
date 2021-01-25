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
            var countData = [
                {value:result.data.count.approval_task, name:'报批任务'},
                {value:result.data.count.proposed_tender, name:'拟制标书'},
                {value:result.data.count.select_suppliers, name:'遴选供应商'},
                {value:result.data.count.procurement_review, name:'采购评审'},
                {value:result.data.count.result_announcement, name:'结果公示'},
                {value:result.data.count.sign_contract, name:'签订合同'},
                {value:result.data.count.delivery_acceptance, name:'交付验收'},
                {value:result.data.count.balance, name:'结算'},
            ]

            var option = {
                title : {
                    text: '采购任务进程一览',
                    x:'center'
                },
                textStyle : {
                    fontSize :16 ,
                },
                tooltip : {
                    // 相对位置，放置在容器正中间
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['报批任务','拟制标书','遴选供应商','采购评审','结果公示','签订合同','交付验收','结算']
                },
                series : [
                    {
                        name: '任务数量',
                        type: 'pie',
                        radius : '70%',
                        center: ['50%', '60%'],
                        data: countData,
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };

            //使用刚才指定的配置项和数据显示图表
                myChart.setOption(option,true);
        });
    </script>
</head>
<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;"></div>
</body>
</html>
