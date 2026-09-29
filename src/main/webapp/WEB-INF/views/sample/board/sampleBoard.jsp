<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/resources/common/jstl-tld.jsp"%>
<!--
#########################################################################################
	화면 설명 : 샘플게시판 - 테스트 메뉴용 CRUD 화면
	            (조회 / 추가 / 수정 / 삭제가 서버 모듈까지 한 바퀴 도는지 확인하는 용도)
#########################################################################################
 -->
<html>
<head>
<script type="text/javascript">
/**
 * 조회
 */
function srch() {
	var grid = $("#grid").data("kendoGrid");
	grid.dataSource.read({
		json_string : genexon.getSearchParameterToJsonString()
	});
}
</script>
</head>
<body>
	<div class="search">
		<table class="se">
			<tr>
				<td><input type="text" class="k-textbox" name="search_word" id="search_word" placeholder="제목/내용/작성자" style="width: 600px" onKeyPress="if(event.keyCode==13)srch();" /></td>
				<td class="line">구분선</td>
				<td><button class="kbtn k-primary" onclick="javascript:srch();" id="srch" name="srch">검색</button></td>
			</tr>
		</table>
	</div>

	<hr style="display:block; height:20px;">

	<div id="grid" class="resizegrid"></div>

	<script type="text/javascript">
	/**
	 * 그리드 컬럼 정의
	 **/
	var gridpt = {
		toolbar: [{name:"create", text:"추가"}],
		columns: [
			{ field: "seq",       title: "번호",   width: 80, attributes: { style: "text-align:center;" } },
			{ field: "title",     title: "제목",   width: 250 },
			{ field: "content",   title: "내용" },
			{ field: "writer_nm", title: "작성자", width: 120 },
			{ field: "in_dtm",    title: "등록일시", width: 170 },
			{ field: "up_dtm",    title: "수정일시", width: 170 },
			{
				command: [
					{ name: "edit",    text: "수정" },
					{ name: "destroy", text: "삭제" }
				],
				title: "변경",
				width: 150
			}
		],
		editable: {
			mode: "inline",
			createAt: "top",
			confirmation: "선택하신 글을 삭제하시겠습니까?"
		}
	};

	/**
	 * 데이터 소스 (URL / 모델)
	 **/
	var ds = {
		transport: {
			read    : {url: "/sample/board/getSampleBoardList.ajax?${_csrf.parameterName}=${_csrf.token}" },
			update  : {url: "/sample/board/updateSampleBoard.ajax?${_csrf.parameterName}=${_csrf.token}" },
			destroy : {url: "/sample/board/deleteSampleBoard.ajax?${_csrf.parameterName}=${_csrf.token}" },
			create  : {url: "/sample/board/insertSampleBoard.ajax?${_csrf.parameterName}=${_csrf.token}" }
		},
		schema: {
			data : "results",
			model: {
				id: "seq",
				fields: {
					seq       : { editable: false },
					title     : { validation: {required: true, validationMessage: "필수항목"} },
					content   : {},
					writer_nm : {},
					in_dtm    : { editable: false },
					up_dtm    : { editable: false }
				}
			}
		},
		batch: true,
		requestEnd : function(e) {
			if (e.type != undefined && e.type != "read") {
				srch();
			}
		}
	};

	genexon.initKendoUI_grid_inlineEdit("#grid", gridpt, ds);
	srch();
	</script>
</body>
</html>
