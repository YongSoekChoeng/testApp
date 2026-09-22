<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
	<!-- <script type="text/javascript" src="/resources/js/jquery/jquery-3.3.1.min.js"></script> -->
	<script type="text/javascript" src="/resources/js/jquery/jquery-1.11.3.js"></script>
	<script type="text/javascript" src="/resources/js/jquery/jquery.cookie.js"></script>
	<script type="text/javascript" src="/resources/kendoui/js/kendo.all.min.js"></script>
	<script type="text/javascript" src="/resources/js/common/genexon.js"></script>
	<meta charset="utf-8">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="theme-color" content="#3a7bfe"/>
	<meta http-equiv="Cache-Control" content="max-age=86400">
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	<meta name="viewport" content="user-scalable=no, viewport-fit=cover, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width">
	<link rel="stylesheet" href="/resources/css/common.css" />
	<link href="/resources/kendoui/styles/kendo.common.min.css" rel="stylesheet" />
	<link href="/resources/kendoui/styles/kendo.bootstrap.min.css" rel="stylesheet" />
	
	<title>테스트 앱 (testApp)</title>
	
<style>
body, html {
    height: 100%;
    margin: 0;
}

.bg {
    /* The image used */
    background-image: url("/resources/images/common/login_bg01.jpg");


    /* Center and scale the image nicely */
    background-position: center;
    background-repeat: no-repeat;
    background-size: cover;


    /* Set rules to fill background */
	min-height: 100%;
	min-width: 1024px;
			
	/* Set up proportionate scaling */
	width: 100%;
	height: auto;
	
	/* Set up positioning */
	position: fixed;
	top: 0;
	left: 0;
}
</style>

	<script>
	$(document).ready(function(){		
		cookieControll();
		var inboxs = $("#login li input");
		//setPlaceHolder();

		inboxs.keyup(function(e){
			setPlaceHolder();
		});
	    inboxs.blur(function(e){
	        setPlaceHolder();
	    });
	    inboxs.focus(function(e){
	        setPlaceHolder();
	    });
		
		//$("#mb_id").focus();
	});
	var getAuthModalopen = "denied";
	
	function authCheck() {
		
		var id = $("#login_id");
		var pw = $("#login_pw");
		
		if(getAuthModalopen == "access"){
			return false;
		}
		
		if(id.val().length == 0){
			alert("아이디를 입력해주세요");
			id.focus();
			return false;
		}
		
		if(pw.val().length == 0){
			alert("비밀번호를 입력해주세요");
			pw.focus();
			return false;
		}

		$.ajax({
			type : "post",
			async : false,
			url : "/comm/getAuthCheck.ajax",
	        data   : { mb_id : $("#mb_id").val(), login_id : id.val(), login_pw : pw.val() },
			dataType : "JSON",
			success : function(data) {
				if(data != null) {
					if(data.SPRING_SECURITY_LAST_EXCEPTION != null) {
						if(data.memberView != null && data.memberView != "" && data.memberView != undefined) {
							doLogin();
						} else {
							$(".lotxt_1").remove();
							$(".bg").prepend("<p class='lotxt_1'>" + data.SPRING_SECURITY_LAST_EXCEPTION + "</p>");	
						}
					} else {
						// 2 factor 인증
						if(data.results.login_autr_type != null && data.results.login_autr_type != "" && data.results.login_autr_type != undefined) {
							if(data.results.login_autr_type == "SMS_Y") {
								genexon.PopWindowOpen({ pID    : "smsAuthPop"
							        , pTitle : "SMS인증"
							        , pURL   : "/comm/smsAuthPop.pop"
							        , data   : {
							        	mb_id : data.results.mb_id,
							        	mb_nm : data.results.mb_nm,
										emp_cd : data.results.emp_cd,
										hpno : data.results.hpno
									}
							        , pWidth : 450
							        , pHeight: 200
							        , body : $("body")
							        , pModal : true
							        , resizable : true 
							      });
							} else {
								doLogin();
							}
						}else{
							doLogin();
						}
					}
				}
			},
			error: function(request, status, error){
				genexon.alert("error", "결과", "입력중 에러가 발생했습니다.");
			}
		});
	}

	// 비밀번호 초기화 팝업
	function pwdIntznCheckPop() {
		
	}
	
	function doLogin() {
		var id = $("#login_id");
		var pw = $("#login_pw");
		
		if(id.val().length == 0){
			alert("아이디를 입력해주세요");
			id.focus();
			return false;
		}
		
		if(pw.val().length == 0){
			alert("비밀번호를 입력해주세요");
			pw.focus();
			return false;
		}
		
		formChk();
	}
	
	$(document).on("keydown", function(e){
		
		if(e.keyCode == 13){
			authCheck();
		}
	});
	
	// 세션이 끊긴경우 해당 페이지를 전체로 로딩
	if(parent && parent!=this) parent.location.href = parent.location.href;
	
	function formChk(){
		if($("#ch").is(":checked")){
			$.cookie("_SAVED_ID_KEY_",$("#login_id").val()+"-"+$("input[name='radio1']:checked").val(),{expires : 7});
		}else{
			$.removeCookie("_SAVED_ID_KEY_",null);
		}
		
		$("#loginForm").submit();		
	}
	
	// 쿠키 컨트롤
	function cookieControll(){
		var check = $.cookie("_SAVED_ID_KEY_"); 
	    if(check != undefined && check != 'null'){   	
	    	$("#ch").attr("checked",true);
	    	var key = check;
	    	key = key.split("-");
	    	var id_key = key[0];
	    	$("#login_id").val(id_key);
	    }
	}
	
	function checkCapsLock(e){
		var key = e.key;
		var keyCode = e.keyCode;
		var testReg = /[A-Z]/g;
		if(keyCode == 20){
			$("#caps").hide();
			return false;
		}
		
		if(testReg.test(key) && (keyCode >= 65 && keyCode <= 90)){
			$("#caps").show();
		}else{
			$("#caps").hide();
		}
	}
	
	function setPlaceHolder()
	{
		if($("#mb_id").val() == "")
	    {
	    	$("#mb_id").css("background-image","url(/resources/images/login/txt_code.png)");
	    	$("#mb_id").css("background-repeat","no-repeat");
	    	$("#mb_id").css("background-position","left");
	    }
	    else
	    {
	    	$("#mb_id").css("background-image","");
	    }
		if($("#login_id").val() == "")
	    {
	    	$("#login_id").css("background-image","url(/resources/images/login/txt_id.png)");
	    	$("#login_id").css("background-repeat","no-repeat");
	    	$("#login_id").css("background-position","left");
	    }
	    else
	    {
	        $("#login_id").css("background-image","");
	    }
	    if($("#login_pw").val() == "")
	    {
	    	$("#login_pw").css("background-image","url(/resources/images/login/txt_pw.png)");
	    	$("#login_pw").css("background-repeat","no-repeat");
	    	$("#login_pw").css("background-position","left");
	    }
	    else
	    {
	    	$("#login_pw").css("background-image","");
	    }
	}
	
	function searchuserid(type) {
		if(type=="1") {
			getAuthModalopen = "access";
			$("#srchUserPop").css("display","inline"); 
		} else if(type=="2") {
			getAuthModalopen = "access";
			$("#srchUserPop2").css("display","inline"); 
		}
	}
	</script>
</head>

<body>
	<form name="loginForm" id="loginForm" action="/login.do" method="POST">
		<div class="bg">
			<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
			    <p class="lotxt_1"><c:out value="${SPRING_SECURITY_LAST_EXCEPTION}"/></p>
			</c:if>
	
			<div id="login_img" class="login_1">
				<ul>
					<c:choose>
						<c:when test="${Domaininfo ne null && Domaininfo ne '' && Domaininfo.mb_id ne 'GNX'}">
							<li><input type="hidden" name="mb_id" id="mb_id" class="lo_input01" value="${Domaininfo.mb_id }"/></li>
						</c:when>
						<c:otherwise>
							<li>
								<input type="hidden" name="mb_id" id="mb_id" class="lo_input01" placeholder="회사코드" onKeyPress="if(event.keyCode==13) authCheck();" value="PRD">
							</li>
						</c:otherwise>
					
					</c:choose>
					
					<li><input type="text" name="login_id" id="login_id" class="lo_input01" placeholder="아이디" onKeyPress="if(event.keyCode==13) authCheck();"></li>
					<li><input type="password" name="login_pw" id="login_pw" class="lo_input01" placeholder="비밀번호" onkeydown="checkCapsLock(event)"></li>
					<li class="listy" style="display:inline-block; float:left; width:auto; box-sizing:border-box; text-align:left;">
						<label class="ch_container" style="display:inline-block;">
				          <input type="checkbox" id="ch"> 아이디저장
				          <span class="checkmark"></span>
				        </label>
				        <div id="caps" style="display: none;">
				        	<div id="capsLock" class="lock_m"><div class="lock">[Caps Lock]이 켜져있습니다</div></div>
						</div>
				    </li>
				    <c:choose>
						<c:when test="${Domaininfo ne null && Domaininfo ne '' && Domaininfo.mb_id ne 'GNX'}">
							<li style="width: 130px; display: inline-block; float: right;">
								<div class="getUserAuthInfo">
									<span onclick="javascript:searchuserid(1);">아이디</span> / <span onclick="javascript:searchuserid(2);">비밀번호찾기</span>
								</div>
							</li>
						</c:when>
					</c:choose>
					<li><button type="button" class="btlogin" onclick="javascript:authCheck();">로그인</button></li>
				</ul>
			</div>
	
			<div class="footer_login">
				<c:choose>
					<c:when test="${Domaininfo ne null && Domaininfo ne '' && Domaininfo.mb_id ne 'GNX'}">
						${Domaininfo.copy_right}
					</c:when>
					<c:otherwise>
						Copyright © 2022 GENEXON, All Rights Reserved.
					</c:otherwise>
				</c:choose>
			</div>
			<c:choose>
				<c:when test="${Domaininfo ne null && Domaininfo ne ''}">
					<div class="footer_login" style="font-size: 10pt; color: red;">
						※ 본 홈페이지는 크롬 브라우저에 최적화 되어 있습니다. <a href="https://www.google.com/intl/ko/chrome/" target="_blank">다운로드</a>
					</div>
				</c:when>
			</c:choose>
		</div>
	</form>
</body>
</html>