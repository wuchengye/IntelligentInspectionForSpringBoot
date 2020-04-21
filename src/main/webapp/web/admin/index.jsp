<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>试金石智能应用系统</title>
        <%@include file="header.jspf" %>
        <style type="text/css">
        	/*修改选项的背景颜色*/
        	#menuitem .menu-active  {
			    border-color: #55d1cf;
			    color: #404040;
			    background: #55d1cf;
			}
			
			.tools .login-info {
			    display: inline-block;
			    text-align: center;
			    height: 26px;
			    width: auto;
			   	background-image:none;
			}
			
			.easyui-linkbutton{
				width:78px;
				padding-top:3px;
				height:30px;
				width:120px;
				color:#ffffff;
				background:none;
			}
			.l-btn-selected{
				color:#ffffff;
				background:#12A4DD;
			}
			.liItem:hover{
				background:#dedede;
			}
        </style>
        <link rel="stylesheet" href="assets/library/jquery/jquery.mCustomScrollbar.css">
    </head>
<body>
    <input type="hidden" id="type"><!-- 校验结果图表：点击的扇形、柱状 -->
    <input type="hidden" id="p_batchTime"><!-- 批次 -->
    <input type="hidden" id="p_ticketType"><!-- 工作票种类 -->
    <input type="hidden" id="p_department"><!-- 单位班组 -->
    <input type="hidden" id="p_workPrincipal"><!-- 工作负责人 -->
    <input type="hidden" id="p_ticketSigner"><!-- 工作票签发人 -->
    <input type="hidden" id="p_workLicensor"><!-- 工作许可人 -->
    <input type="hidden" id="p_ticketEndTime1"><!-- 工作票终结时间 -->
    <input type="hidden" id="p_ticketEndTime2">
    <input type="hidden" id="p_orgids">
    
    <input type="hidden" id="p_newBatch"><!-- 校验产生的新批次 -->
    <div class="header">
    <div class="logo">
        <h3 style="font-size: 18px; color: #fff;font-weight: bold;"><span style="color: #52abcd;">试金石智能应用系统</span></h3>
    </div>
    <div class="tools" id="cxytest">
        <div class="login-info">
            <a href="javascript:void(0)" id="menubutton" >
                <i class="icon-person" style="position:relative;top: 5px;"></i>
                <span><shiro:principal property="userName"/></span>
            </a>
            <div id="menuitem" style="width:50px">   
				<div id="editPsd" data-options="iconCls:'icon-edit'" >修改密码</div>
			</div>
        </div>
        <a class="btn-logout" href="admin/logout">注销</a>
    </div>
   	
	<div id="editPsdDialog"></div>
</div>
<div class="main-box">
    <div class="lr">
        <div class="wrapper" >
            <div id="nav_slider">
                <div class="shadow-box"></div>
            </div>
            <ul id="nav_menu">  
            </ul>
        </div>
    </div>
    <div class="content">
<!--        <iframe src="http://192.168.1.62:8083/bdacmcc/web/map.jsp" height="100%" width="100%" frameborder="0" ></iframe> -->
        <div class="easyui-tabs js-tabs" id="centerTab" fit="true" border="false" tabHeight='29'>
            <!--    welcom home -->
        </div>
    </div>
</div>
<script type="text/javascript" src="assets/library/jquery/jquery.sha256.min.js"></script>
<script type="text/javascript">
	
    $(function(){
    	
        seajs.use([ 'module/admin/index.js?v=20190414' ], function(module) {
            module.init();
        }); 
        
        /* $('#menubutton').splitbutton({    
            menu: '#menuitem',
            hideOnUnhover : false
        });  */
        
    });

</script>
<script src="assets/library/jquery/jquery.mCustomScrollbar.concat.min.js"></script>
</body>
</html>