<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <!--  JQUERY -->
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>

    <!--  BOOTSTRAP -->
    <link rel="stylesheet" type="text/css" href="jquery/bootstrap_3.3.0/css/bootstrap.min.css">
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

    <!--  PAGINATION plugin -->
    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>
    <title>演示分页插件</title>
    <script type="text/javascript">
        $(function() {
            $("#demo_pag1").bs_pagination({
                currentPage:1,//当前页
                rowsPerPage:10,//每页显示10条
                totalRows:1000,//总记录数
                totalPages:100,//总页数
                visiblePageLinks:5,//显示的翻页卡片数
                showGoToPage:true,//显示跳转到第几页
                showRowsPerPage:true,//每页显示条数
                showRowsInfo:true,
                //每次切换页号时触发这个函数进行处理
                onChangePage:function (e,pageObj) {
                    alert(pageObj.currentPage);
                    alert(pageObj.rowsPerPage);
                }

            })


        });
    </script>
</head>
<body>
<!--  Just create a div and give it an ID -->
<div id="demo_pag1"></div>
</body>
</html>
