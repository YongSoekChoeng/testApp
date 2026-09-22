<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/resources/common/jstl-tld.jsp"%>
<!--
#########################################################################################
	작성자 : lakhyun.kim
	최초작성일자 : 2019. 04. 19
	화면 설명 : SMS인증 팝업
######################################################################################### 
 -->
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Pragma" content="no-cache">
<link href="/resources/kendoui/styles/kendo.common.min.css" rel="stylesheet" />
<link href="/resources/kendoui/styles/kendo.bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="/resources/css/font-awesome.min.css" />
<link rel="stylesheet" href="/resources/css/search.css" />
<link rel="stylesheet" href="/resources/css/common.css" />
<script type="text/javascript" src="/resources/js/jquery/jquery-1.11.3.js"></script>
<script type="text/javascript" src="/resources/js/common/genexon.js"></script>
<%@ include file="../../sitemesh/layoutPopstyle.jsp"%>
<script type="text/javascript">
$(document).ready(function() {

	// 인사정보에 핸드폰번호가 없을때
	if ("${LoginVO.hpno}" == "" || "${LoginVO.hpno}" == null || "${LoginVO.hpno}" == undefined) {
		genexon.alert("error", "결과", "핸드폰번호가 없습니다. 관리자에게 문의해주세요");
		parent.genexon.PopWindowClose(parent.genexon.openwindowID);
	}

	timeExpiration();
});
var sMinute; // 시간 지정 분
var sSecond; // 초단위로 환산
var sTimerchecker = null;

// 시간 연장
function timeExpiration() {
	clearTimeout(sTimerchecker);
	sMinute = 3;
	sSecond = sMinute * 60;
	initTimer();
}

initTimer = function() {
	rMinute = parseInt(sSecond / 60);
	rSecond = sSecond % 60;

	if (sSecond > 0) {
		loginTimer.innerHTML = "남은시간 : " + rMinute + "분 "
				+ genexon.lpad(rSecond, 2, "0") + "초 ";
		sSecond--;
		sTimerchecker = setTimeout("initTimer()", 1000); // 1초 간격으로 체크
	}

	if (rMinute == 0 && rSecond <= 0) {
		deleteAuthNum();
	}
}

// 재 전송
function reSendSMS() {
	window.location.reload();
}

//인증번호 제거
function deleteAuthNum() {

	$.ajax({
		url : "/comm/deleteAuthNum.ajax",
		type : "POST",
		dataType : "json",
		data : {
			mb_id : $("#mb_id").val(),
			emp_cd : $("#emp_cd").val()
		},
		success : function(result) {
			clearTimeout(sTimerchecker);
			genexon.alert("error", "결과", "유효시간이 만료되었습니다.");
			parent.genexon.PopWindowClose(parent.genexon.openwindowID);
		},
		error : function(error) {
			genexon.alert("error", "결과", "입력중 에러가 발생했습니다.");
		}
	});
}

//SMS 인증번호 체크
function authNumCheck() {

	// 유효성검사
	if ($("#auth_num").val() == null || $("#auth_num").val() == "") {
		genexon.alert("error", "결과", "인증번호를 입력해주세요.");
		return;
	}

	$.ajax({
		url : "/comm/getAuthNumCheck.ajax",
		type : "POST",
		dataType : "json",
		data : {
			mb_id : $("#mb_id").val(),
			emp_cd : $("#emp_cd").val(),
			auth_num : $("#auth_num").val()
		},
		success : function(result) {
			if (result.result == "success") {
				genexon.alert("success", "인증", "인증되었습니다.");
				parent.doLogin();
				parent.genexon.PopWindowClose(parent.genexon.openwindowID);
			} else {
				genexon.alert("error", "결과", "인증번호를 확인해주세요.");
			}
		},
		error : function(error) {
			genexon.alert("error", "결과", "입력중 에러가 발생했습니다.");
		}
	});
}
</script>
</head>
<body>
	<input type="hidden" name="mb_id" id="mb_id" value="${LoginVO.mb_id}">
	<input type="hidden" name="emp_cd" id="emp_cd" value="${LoginVO.emp_cd}">
	<div class="content" style="min-width: 0;">
		<form id="myForm" name="myForm" method="post">
			<table class="ta_sample2">
				<colgroup>
					<col width="25%" />
					<col width="75%" />
				</colgroup>
				<tr>
					<th>전화번호</th>
					<td>
						${LoginVO.hpno}
					</td>
				</tr>
				<tr class="tr_size_55">
					<th>인증번호</th>
					<td>
						<input type="text" class="k-textbox" id="auth_num" name="auth_num" style="width: 110px;" maxlength="6">
						<div class="bt_glay_S" onclick="javascript:reSendSMS();">재전송</div>
					</td>
				</tr>
			</table>
			<hr style="display: block; height: 5px;">
			<div style="color: red;">※ 전화번호가 일치하지 않으면 관리자에게 문의해주세요</div>
			<hr style="display: block; height: 5px;">
			<div style="text-align: center; font-size: 14px;">
				<span id="loginTimer" style="font-size: 14px;"></span>
				<span onclick="javascript:timeExpiration();" style="cursor: pointer;">[시간연장]</span>
			</div>

			<div class="bt_right" style="margin-right: 10px">
				<div class="kbtn k-primary k-button" onclick="javascript:authNumCheck();">확인</div>
			</div>
		</form>
	</div>
</body>
</html>