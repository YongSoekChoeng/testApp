<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/resources/common/jstl-tld.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Pragma" content="no-cache">
<title>::::: testApp :::::</title>
<link href="/resources/kendoui/styles/kendo.common.min.css" rel="stylesheet" />
<link href="/resources/kendoui/styles/kendo.bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="/resources/css/font-awesome.min.css" />
<link rel="stylesheet" href="/resources/css/search.css" />
<link rel="stylesheet" href="/resources/css/common.css" />
<link rel="stylesheet" href="/resources/css/loading.css" />
<link rel="stylesheet" href="/resources/css/important.css" />
<link rel="stylesheet" href="/resources/css/tooltip.css" />
<link href="/resources/css/simple-line-icons.css" rel="stylesheet" />
<!-- <script type="text/javascript" src="/resources/js/jquery/jquery-3.3.1.min.js"></script> -->
<!-- <script type="text/javascript" src="/resources/js/jquery/jquery-1.11.3.js"></script> -->
<script type="text/javascript" src="/resources/js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="/resources/kendoui/js/kendo.all.min.js"></script>
<script type="text/javascript" src="/resources/kendoui/js/cultures/kendo.culture.ko-KR.min.js"></script>
<script type="text/javascript" src="/resources/js/common/genexon.js"></script>
<script type="text/javascript" src="/resources/js/common/layout.js"></script>

<script type="text/javascript" src="/resources/js/jquery/jquery.fileDownload.js"></script>
<script type="text/javascript" src="/resources/js/common/session.js"></script>
<script type="text/javascript" src="/resources/js/jquery/jquery.form-3.51.0.js"></script>
<script type="text/javascript" src="/resources/js/common/postcode.v2.js"></script>
<%-- <%@ include file="../views/comm/address.jsp" %> --%>
<%@ include file="../sitemesh/layoutstyle.jsp" %>

<script>
    window.MSPointerEvent = null;
    window.PointerEvent = null;
</script>
<script type="text/javascript">
$(document).ready(function(){
	var in_autr_type = "${menuRole.in_autr_type}";		//입력권한
	var up_autr_type = "${menuRole.up_autr_type}";		//수정권한
	var de_autr_type = "${menuRole.de_autr_type}";		//삭제권한
	var lo_autr_type = "${menuRole.lo_autr_type}";		//엑셀업로드 권한
	var do_autr_type = "${menuRole.do_autr_type}";		//엑셀다운로드 권한
	var erp_autr_type = "${menuRole.erp_autr_type}";	//ERP 적용 권한
	var cls_resource_id = "${menuRole.cls_resource_id}";	//마감 코드
	
	//입력
	if(in_autr_type != "Y") {
		$('.in_autr_type').attr("style", "display: none !important");
	}
	
	//수정
	if(up_autr_type != "Y") {
		$('.up_autr_type').attr("style", "display: none !important");
	}
		
	//삭제
	if(de_autr_type != "Y") {
		$('.de_autr_type').attr("style", "display: none !important");
	}
	
	//엑셀 업로드
	if(lo_autr_type != "Y") {
		$(".lo_autr_type").attr("style", "display: none !important");
	}
	
	//엑셀다운로드
	if(do_autr_type != "Y") {
		$(".do_autr_type").attr("style", "display: none !important");
	}
	
	//ERP 적용 권한
	if(erp_autr_type != "Y") {
		$(".erp_autr_type").attr("style", "display: none !important");
	}
	
	genexon.initKendoUI();
	resizeGrid();
	
	$(window).resize(function() {
		resizeGrid();
	});
	
	$("#init_background").fadeOut(1000);
	
	//검색 영역 내 input text 타입에서 엔터키 클릭시 srch 함수 실행
	$("div.search input[type=text]").on("keypress", function(e) {
		if(e.keyCode == 13) {
			srch();
		};
	});
});
</script>
    <sitemesh:write property='head'/>
</head>

<body style="overflow: auto;">
	<div id="init_background" style="width: 100%; height: 100%; position: fixed; z-index: 9999; background-color: white;"></div>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	
	<input type="hidden" id="menuRole" value="${menuRole}"/>
	<input type="hidden" id="in_autr_type" value="${menuRole.in_autr_type}"/>
	<input type="hidden" id="up_autr_type" value="${menuRole.up_autr_type}"/>
	<input type="hidden" id="de_autr_type" value="${menuRole.de_autr_type}"/>
	<input type="hidden" id="lo_autr_type" value="${menuRole.lo_autr_type}"/>
	<input type="hidden" id="do_autr_type" value="${menuRole.do_autr_type}"/>
	<input type="hidden" id="view_auth" value="${menuRole.view_auth}"/>
	<input type="hidden" id="cls_resource_id" value="${menuRole.cls_resource_id}"/>
	<input type="hidden" id="target_erp_data" value="${menuRole.target_erp_data}"/>
	
	<sitemesh:write property='body'/>
    <iframe title="" id="ifmDetail" name="ifmDetail" style="width: 100%;height: 0px;border: 0px;overflow:hidden;display: none;"></iframe>
    <form id="frmSubDetail" name="frmSubDetail" target="ifmDetail" style="display: none;"></form>

	<div id="sessionChkModal" style="display:none;">
		<h4>로그인 유지시간이 1분 남았습니다.<br>연장을 원하시면 유지버튼을 눌러주세요.</h4>
		<button class="kbtn" style="width: 150px; margin-top: 23px" onClick="javascript:sessionExt();">유지</button>
		<button class="kbtn" style="width: 150px; margin-top: 23px" onClick="javascript:sessionExp();">로그아웃</button>
	</div> <!-- 세션유지모달 -->
</body>
</html>