<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<style>
#basic_info {
	padding: 1em 2em;
	overflow-y: auto;
}

#basic_info > table {
	width: 100%;
	border-collapse: collapse;
}

#basic_info > table > tbody {
	width: 100%;
}

#basic_info > table > tbody > tr > td {
	vertical-align: top;
	white-space: pre-line;
}

#basic_info > table > tbody > tr:not(:last-child) > td {
	padding-bottom: 5px;
}

#basic_info > table > tbody > tr > td:nth-child(odd) {
	width: 10%;
	text-align: right;
	padding-right: 1em;
}

#basic_info > table > tbody > tr > td:nth-child(even) {
	width: 15%;
	text-align: left;
	padding-right: 1em;
}

#detail_info {
	display: flex;
	flex-direction: column;
	align-items: center;
}

#detail_info > .player_bar {
	flex: 0 0 auto;
	width: 100%;
}

#detail_info > .text_title {
	flex: 0 0 auto;
	width: 100%;
	z-index: 2;
}

#detail_info > .text_title > table {
	width: 100%;
	border-collapse: collapse;
}

#detail_info > .text_title > table > tbody {
	width: 100%;
}

#detail_info > .text_title > table > tbody > tr > td {
	height: 2em;
	text-align: center;
	vertical-align: middle;
	background-color: #F4F4F4;
	border: 1px solid #DCDCDC;
}

#detail_info > .text_title > table > tbody > tr > td:nth-child(1) {
	width: 2em;
}

#detail_info > .text_title > table > tbody > tr > td:nth-child(2) {
	width: 8em;
}

#detail_info > .text_content {
	flex: 1 1 auto;
	width: 100%;
	background-color: #FFFFFF;
	margin-top: -1px;
	overflow-y: auto;
	z-index: 1;
}

#detail_info > .text_content > table {
	width: 100%;
	border-collapse: collapse;
}

#detail_info > .text_content > table > tbody {
	width: 100%;
}

#detail_info > .text_content > table > tbody > tr > td {
	vertical-align: middle;
	border: 1px dotted #DCDCDC;
	padding-top: 5px;
	padding-bottom: 5px;
	white-space: pre-line;
}

#detail_info > .text_content > table > tbody > tr > td:nth-child(1) {
	width: 2em;
	text-align: center;
	background-color: #F4F4F4;
}

#detail_info > .text_content > table > tbody > tr > td:nth-child(2) {
	width: 8em;
	text-align: center;
}

#detail_info > .text_content > table > tbody > tr > td:nth-child(3) {
	text-align: left;
	padding-left: 5px;
}

#detail_info > .check_btn {
	flex: 0 0 auto;
	width: 100px;
	margin: 5px 0;
}
</style>

<%-- <input type="hidden" name="judgeId" id="judgeId" value="${judgeId }" />
<input type="hidden" name="xmlPath" id="xmlPath" value="${session.xmlPath }" /> --%>

<div id="basic_info" >
	<form  method="POST" id="check_form">	
	<input type="hidden" name="sessionId" id="sessionId" value="${session.sessionId }" />
	<input type="hidden" name="checkAccounts" id="checkAccounts" value="${checkAccounts }" />
	<input type="hidden" name="filePath" id="filePath" value="${session.filePath }" />
	<input type="hidden" name="fileName" id="fileName" value="${session.fileName }" />
	<table >
		<tbody>
			<tr>
				<td>人员是否存在问题:</td>
				<td>
					<select name="personIsProblem"  style="width:150px;" >
                         <option ${session.personIsProblem eq '是'?'selected':''} value="是">是</option>
                         <option ${session.personIsProblem eq '否'?'selected':''} value="否">否</option>
					</select>
				</td>
				<td>转写是否正确:</td>
				<td>
					<select name="transferIstrue"  style="width:150px;" >
                         <option ${session.transferIstrue eq '是'?'selected':''} value="是">是</option>
                         <option ${session.transferIstrue eq '否'?'selected':''} value="否">否</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>问题描述:</td>
				<td colspan="3">
					<input name="problemDescribe" type="text" style="width:600px;" value="${session.problemDescribe}"/>
				</td>
			</tr>
			<tr>
				<td>正确转写内容:</td>
				<td colspan="3">
					<input name="trueTransferContent" type="text" style="width:600px;" value="${session.trueTransferContent}"/>
				</td>
			</tr>
			<tr>
				<td>评语:</td>
				<td colspan="3">
					<textarea id="remark" name="remark"   style="width:600px; height:50px;">${session.remark}</textarea>
				</td>
			</tr>
			<tr>
				<td>复核工号:</td>
				<td>
					${session.checkAccounts}
				</td>
				<td></td>
				<td align="right">
					<button class="bda-btn-submit">提交</button>
					<!-- <a href="javascript:void(0)" class="bda-btn-submit" plain="false" data-options="iconCls:'icon-search'">提交</a> -->
				</td>
			</tr>
		</tbody>
	</table>
	</form>
</div>
<div id="detail_info" title="会话详细">
	<div class="player_bar"></div>
	<div class="text_title">
		<table>
			<tbody>
				<tr>
					<td></td>
					<td>问答类型</td>
					<td>会话内容</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="text_content">
		<table>
			<tbody>
			</tbody>
		</table>
	</div>
	<%-- <div id="recheckBtn" class="check_btn">
		<shiro:hasPermission name="Opr:recheck:secondjudge">
			<c:set var="per" value="${1}"/>
		</shiro:hasPermission>
		<shiro:hasPermission name="Opr:recheck:judge">
			<c:set var="per" value="${1}"/>
		</shiro:hasPermission>
		
		<c:if test="${per > 0}">
		   <a href="javascript:void(0)" class="easyui-linkbutton bda-btn-recheck" data-options="iconCls:'icon-save'">核查</a>  
		</c:if>
	</div> --%>
</div>
<div id="recheck_dialog"></div>
	
<script>
	$(function(){
		seajs.use([ 'module/risk/hotlineincomplaint.js' ], function(hotlinetaboo) {
			hotlinetaboo.detailDialog();
		});
		/* seajs.use([ 'module/risk/recheck.js' ], function(recheck) {
			recheck.recheck_init("hotlineintaboo", 0);
		}); */
	});
</script>
