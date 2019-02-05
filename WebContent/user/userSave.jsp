<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
function checkForm(){
	var nickName=document.getElementById("nickName").value;
	if(nickName=="" || nickName==null){
		document.getElementById("nickName").innerHTML="昵称不能为空！";
		return false;
	}
	return true;
}
</script>
<div class="data_list">
	<div class="data_list_title">
		<img src="${pageContext.request.contextPath }/images/user_edit_icon.png"/>
		用户信息
	</div>
	<div class="row-fluid" style="padding-top:20px;">
		<div class="span4">
			<img src="${currentUser.imageName }"/>
		</div>
		<div class="span8">
			<form action="user?action=save" method="post" enctype="multipart/form-data" onsubmit="return checkForm()">
				<table style="with:100%;">
					<tr>
						<td style="width:20%;">头像路径：</td>
						<td><input type="file" id="imagePath" name="imagePath"/></td>
					</tr>
					<tr>
						<td>昵称：</td>
						<td><input type="text" name="nickName" id="nickName" value="${currentUser.nickName }" style="margin-top:5px;height:30px;"/></td>
					</tr>
					<tr>
						<td valign="top">个人心情：</td>
						<td><textarea rows="10" cols="" style="width:100%;" name="mood" id="mood" >${currentUser.mood }</textarea></td>
					</tr>
					<tr>
						<td><button class="btn btn-primary" type="submit">保存</button></td>
						<td><button class="btn btn-primary" type="button" onclick="javascript:history.back()">返回</button></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>