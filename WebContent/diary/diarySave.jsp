<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="${pageContext.request.contextPath}/js/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
function submitCheck(){
	var title=document.getElementById("title").value;
	var content=CKEDITOR.instances.content.getData();
	var typeId=document.getElementById("typeId").value;
	if(title==null || title==""){
		document.getElementById("ERROR").innerHTML="标题不能为空";
	     return false;
	}
	if(content==null || content==""){
		document.getElementById("ERROR").innerHTML="内容不能为空";
	     return false;
	}
	if(typeId==null || typeId==""){
		document.getElementById("ERROR").innerHTML="类别不能为空";
	     return false;
	}
	return true;
}

</script>
<div class="data_list">
	<div class="data_list_title">
		<img src="${pageContext.request.contextPath }/images/diaryType_add_icon.png"/>
		写日记
	</div>
</div>
<form action="diary?action=save" method="post" onsubmit="return submitCheck()">
<div>
	<div class="title"><input type="text" id="title" name="title" value="${diary.title }" class="input-xlarge"  style="margin-top:5px;height:30px;"  placeholder="日志标题..."/></div>
	<div class="content"><textarea class="ckeditor" id="content" name="content">${diary.content}</textarea></div>
	<div class="diary_type">
		<select id="typeId" name="typeId">
			<option value="">请选择类别...</option>
			<c:forEach var="diaryTypeCount" items="${diaryTypeCountList }">
				<option	value="${diaryTypeCount.diaryTypeId }">${ diaryTypeCount.typeName}</option>
			</c:forEach>
		</select>
	</div>
	<div>
		<input type="submit" class="btn btn-primary" value="保存"/>
		<button class="btn btn-primary" type="button" onclick="javascript:history.back()">返回</button>
		<font id="ERROR" color="red">${ERROR}</font>
	</div>
</div>
</form>