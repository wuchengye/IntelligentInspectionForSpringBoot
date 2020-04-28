<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  

<style>
    table#detail td{
        border: 1px solid black;
    }
    .filedTd{
        padding-right: 10px;
        text-align: right;
        width: 18%;
    }
    .contentTd{
        padding-left: 8px;
        white-space: pre-line;
    }
    .dialog-button { 
    	padding: 5px; 
    	text-align: center; 
    } 
</style>

<div style="padding: 10px 20px">
	<table id="detail" style="width:100%;border-collapse: collapse;">
	    <tr>
	        <td class="filedTd" >工作任务</td><td class="contentTd" id="workTask">${originalData['workTask']}</td>
	    </tr>
	    <tr>
	        <td class="filedTd" >工作票类型</td><td id="ticketType" class="contentTd">${originalData['ticketType']}</td>
	    </tr>
	    <tr>
	        <td class="filedTd" >工作负责人</td><td class="contentTd">${originalData['workPri']}</td>
	    </tr>
	    <tr>
	        <td class="filedTd" >单位和班组</td><td class="contentTd">${originalData['unit']}</td>
	    </tr>
	    <tr>
	        <td class="filedTd" >站/线路</td><td class="contentTd">${originalData['line']}</td>
	    </tr>
	    <tr>
	        <td class="filedTd" >计划开始时间</td><td class="contentTd">${originalData['planStart']}</td>
	    </tr>
	    <tr>
	        <td class="filedTd" >计划结束时间</td><td class="contentTd">${originalData['planEnd']}</td>
	    </tr>
	    <tr>
	        <td class="filedTd" >工作地点</td><td class="contentTd" id="workPlace">${originalData['workPlace']}</td>
	    </tr>
	    <tr>
	        <td class="filedTd" >工作票状态</td><td id="ticketStatus" class="contentTd">${originalData['ticketStatus']}</td>
	    </tr>
	    
	    <tr>
	        <td class="filedTd" >工作票票号</td><td class="contentTd">${originalData['ticketNum']}</td>
	    </tr>
	    <tr>
	        <td class="filedTd" >运维单位</td><td class="contentTd">${originalData['operation']}</td>
	    </tr>
	    <tr>
	        <td class="filedTd" >工作票签发人</td><td class="contentTd">${originalData['ticketLssue']}</td>
	    </tr>
	    <tr>
	        <td class="filedTd" >签发时间</td><td class="contentTd">${originalData['lssueTime']}</td>
	    </tr>
	    <tr>
	        <td class="filedTd" >值班负责人</td><td class="contentTd">${originalData['attendance']}</td>
	    </tr>
	    <tr>
	        <td class="filedTd" >工作许可人</td><td class="contentTd">${originalData['workLicensor']}</td>
	    </tr>
	    <tr>
	        <td class="filedTd" >许可工作时间</td><td class="contentTd">${originalData['licensTime']}</td>
	    </tr>
	    <tr>
	        <td class="filedTd" >工作终结许可人</td><td class="contentTd">${originalData['workLastPerson']}</td>
	    </tr>
	    <tr>
	        <td class="filedTd" >工作终结时间</td><td class="contentTd">${originalData['workLastTime']}</td>
	    </tr>
	    <tr>
	        <td class="filedTd" >延期时间</td><td class="contentTd">${originalData['delay']}</td>
	    </tr>
	    
	</table>        
</div>				

<script type="text/javascript">
	$(function() {
		seajs.use([ 'module/electric/threePerson2.js?v=20190605' ], function(kwa) {
			kwa.formatData();
		});		
	});
</script>
