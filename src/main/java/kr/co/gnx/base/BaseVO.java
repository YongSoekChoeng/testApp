package kr.co.gnx.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import kr.co.gnx.comm.util.BaseMap;
import kr.co.gnx.comm.util.PageVO;
import kr.co.gnx.comm.util.propertiesVO;
import kr.co.gnx.system.file.FileVO;

public abstract class BaseVO{
	@ApiModelProperty(value="회원사ID",allowEmptyValue=false,hidden=false)
	private String mb_id;
	@ApiModelProperty(value="ID",allowEmptyValue=false,hidden=false)
	private String user_id;
	@ApiModelProperty(value="시퀀스",allowEmptyValue=false,hidden=false)
	private String seq;
	private String group_seq;
	@ApiModelProperty(value="자식시퀀스",allowEmptyValue=false,hidden=false)
	private String ref_seq;
	
	private String temp_seq;
	
	private String prod_seq;
	
	private String ref_group_seq;
	
	@ApiModelProperty(value="프로젝트종류",allowEmptyValue=false,hidden=false)
	private String project_type;
	@ApiModelProperty(value="토큰",allowEmptyValue=false,hidden=true)
	private String token;
	@ApiModelProperty(value="회사명",allowEmptyValue=false,hidden=true)
	private String mb_nm;
	@ApiModelProperty(value="사원코드",allowEmptyValue=false,hidden=true)
	private String emp_cd;
	@ApiModelProperty(value="사원명",allowEmptyValue=false,hidden=true)
	private String emp_nm;
	@ApiModelProperty(value="조직코드",allowEmptyValue=false,hidden=true)
	private String scd;
	@ApiModelProperty(value="조직명",allowEmptyValue=false,hidden=true)
	private String snm;
	
	private String insco_cd;
	private String insco_nm;
	private String insco_type;
    private String insco_type_nm;
    
	@ApiModelProperty(value="재직구분",allowEmptyValue=false,hidden=true)
	private String empsta;
	@ApiModelProperty(value="재직구분명",allowEmptyValue=false,hidden=true)
	private String empsta_nm;
	@ApiModelProperty(value="직급",allowEmptyValue=false,hidden=true)
	private String jikgub;
	@ApiModelProperty(value="직책",allowEmptyValue=false,hidden=true)
	private String jikchk;
	@ApiModelProperty(value="권한코드",allowEmptyValue=false,hidden=true)
	private String role_id;
	@ApiModelProperty(value="롤이름",allowEmptyValue=false,hidden=true)
	private String role_nm;
	@ApiModelProperty(value="포함권한",allowEmptyValue=false,hidden=true)
	private List<String> role_ids;
	@ApiModelProperty(value="등록일자",allowEmptyValue=false,hidden=true)
	private String in_dtm;
	@ApiModelProperty(value="등록자",allowEmptyValue=false,hidden=true)
	private String in_emp_cd;
	@ApiModelProperty(value="수정일자",allowEmptyValue=false,hidden=true)
	private String up_dtm;
	@ApiModelProperty(value="수정자",allowEmptyValue=false,hidden=true)
	private String up_emp_cd;
	@ApiModelProperty(value="정렬순서",allowEmptyValue=false,hidden=true)
	private String sort_no;
	@ApiModelProperty(value="세션시간",allowEmptyValue=false,hidden=true)
	private String session_time;
	@ApiModelProperty(value="JSON변수",allowEmptyValue=false,hidden=true)
	private String json_string;
	
	private String[] value_arr;
	
	@ApiModelProperty(value="성공상태",allowEmptyValue=false,hidden=true)
	private String success;//성공여부 성공(S) 실패(F) 대기(W)
	@ApiModelProperty(value="메세지",allowEmptyValue=false,hidden=true)
	private String message;
	@ApiModelProperty(value="사용여부",allowEmptyValue=false,hidden=true)
	private String use_yn;
	private String use_yn_nm;
	
	@ApiModelProperty(value="",allowEmptyValue=false,hidden=true)
	private String demo_gubun;
	
	@ApiModelProperty(value="우편번호",allowEmptyValue=false,hidden=true)
	private String zipcd;
	@ApiModelProperty(value="주소1",allowEmptyValue=false,hidden=true)
	private String addr1;
	@ApiModelProperty(value="주소2",allowEmptyValue=false,hidden=true)
	private String addr2;
	@ApiModelProperty(value="휴대전화번호",allowEmptyValue=false,hidden=true)
	private String hpno;
	@ApiModelProperty(value="이메일",allowEmptyValue=false,hidden=true)
	private String email;
	@ApiModelProperty(value="성별",allowEmptyValue=false,hidden=true)
	private String gender;
	@ApiModelProperty(value="고객명",allowEmptyValue=false,hidden=true)
	private String customer_name;
	@ApiModelProperty(value="전화번호",allowEmptyValue=false,hidden=true)
	private String telno;
	
	
	private String menu_view_type;	// 입력, 수정, 리스트 타입
	private String input_method;	// 입력 방식
	
	/** 페이징 */
	private String page;
	
	
	@ApiModelProperty(value="페이징",allowEmptyValue=false,hidden=true)
	private PageVO pageVO = new PageVO();
	
	private String pageSize;
	
	@ApiModelProperty(value="페이징여부",allowEmptyValue=false,hidden=true)
	private String pageyn = "";
	
	@ApiModelProperty(value="프로퍼티",allowEmptyValue=false,hidden=true)
	private propertiesVO propertiesvo = new propertiesVO();
	
	@ApiModelProperty(value="첨부구분",allowEmptyValue=false,hidden=true)
	private String attach_gbn = "";
	
	@ApiModelProperty(value="첨부구분명",allowEmptyValue=false,hidden=true)
	private String attach_gbn_nm;
	
	@ApiModelProperty(value="파일 단건", allowEmptyValue=false, hidden=true)
	private MultipartFile multipartfile;
	
	@ApiModelProperty(value="파일 리스트", allowEmptyValue=false, hidden=true)
	private List<MultipartFile> multipartfileList;
	
	@ApiModelProperty(value="마감월(업적년월)", allowEmptyValue=false, hidden=true)
	private String com_ym;
	
	private String com_ym_text;
	
	@ApiModelProperty(value="엑셀파일경로", allowEmptyValue=false, hidden=true)
	private String excelpath;
	
	@ApiModelProperty(value="메뉴별 조직조회권한", allowEmptyValue=false, hidden=true)
	private String view_auth;
	@ApiModelProperty(value="조회권한", allowEmptyValue=false, hidden=true)
	private String se_autr_type;
	@ApiModelProperty(value="입력권한", allowEmptyValue=false, hidden=true)
	private String in_autr_type;
	@ApiModelProperty(value="수정권한", allowEmptyValue=false, hidden=true)
	private String up_autr_type;
	@ApiModelProperty(value="삭제권한", allowEmptyValue=false, hidden=true)
	private String de_autr_type;
	@ApiModelProperty(value="엑셀업로드권한", allowEmptyValue=false, hidden=true)
	private String lo_autr_type;
	@ApiModelProperty(value="엑셀다운로드권한", allowEmptyValue=false, hidden=true)
	private String do_autr_type;
	@ApiModelProperty(value="ERP 적용 권한", allowEmptyValue=false, hidden=true)
	private String erp_autr_type;
	@ApiModelProperty(value="로그인 인증 초기 설정", allowEmptyValue=false, hidden=true)
	private String login_autr_type;
	@ApiModelProperty(value="로그인 세션 조직", allowEmptyValue=false, hidden=true)
	private String user_scd;
	@ApiModelProperty(value="겸직구분", allowEmptyValue=false, hidden=true)
	private String concurrent_idx;
	@ApiModelProperty(value="겸직여부", allowEmptyValue=false, hidden=true)
	private String concurrent_gubun;
	@ApiModelProperty(value="URL", allowEmptyValue=false, hidden=true)
	private String resource_url;
	@ApiModelProperty(value="비고", allowEmptyValue=false, hidden=true)
	private String bigo;
	@ApiModelProperty(value="메모", allowEmptyValue=false, hidden=true)
	private String memo;
	@ApiModelProperty(value="PKID", allowEmptyValue=false, hidden=true)
	private String keyid;
	
	private String table_type;
	private String table_name;
	
	private String lv;//레벨
	private String isleaf;//자식이 있으면 0 없으면 1
	private int total;
	private String sort_column;
	private String sort_dtm;
	private int pageoffset;
	private String srch_work_scope;		//업무영역 구분 코드
	private String srch_table_comment;	//테이블 설명
	
	private int prc_return_code;		// 프로시저  리턴 코드
	private String prc_return_msg;		// 프로시저 리턴 메시지
	private int prc_return_cnt;			// 프로시저 결과 개수
	
	private ArrayList<FileVO> fileList;  /* 첨부파일 목록 */
	
	private String search_word;
	private String search_words[];
	private String search_arr[];
	
	private String srch_mb_id;
	private String srch_scd;
	private String srch_snm;
	private String srch_org_scd;//트리뷰 조직
	private String srch_jikchk;
	private String srch_jikgub;
	private String srch_role_id;
	private String srch_empsta;
	private String srch_jigubyn;
	private String srch_term;
	private String cd_vl;
    private String cd_vl_nm;
    private String srch_emp_value;
    private String srch_emp_value2;
    private String srch_hpno;
    private String srch_org_value;//조직코드/조직명/조직장
    private String srch_org_open_gbn;//운영구분(사용유무)
    private String srch_org_unit_gbn;//조직단위구분
    private String srch_com_ym;
    private String srch_dbattribute;
    private String srch_term_start_value;
    private String srch_term_start_value2;
    private String srch_term_end_value;
    private String srch_term_end_value2;
    private String srch_use_yn;
    private String srch_insco_type;
    private String srch_insco_cd;
    private String srch_fc_jigub_type_cd;
    private String srch_ovr_type_cd;
    private String srch_empnm;
    private String file_path;
    private String srch_resource_id;
    private List<String> srch_resource_arr;
    private List<HashMap<String, String>> srch_resource_list;
    private String srch_customer_name;
    private String srch_birthday;
    private String srch_telno;
    private String srch_claim_no;
    private String srch_state_id;
    private String srch_status_id;
    private List<String> srch_state_arr;
    private List<String> srch_status_arr;
    private String srch_jigub_type_cd;
    private String srch_car_jigub_type_cd;
    private String srch_gen_jigub_type_cd;
    private String srch_no_jigub_cd;
    private String srch_insco_emp_cd_gbn;
    private String srch_emp_sj_cd;
    private String srch_ddl_filter_value;		//DropDownList에서 serverFiltering 적용 시 사용 되는 검색변수명
    private String srch_turn_cnt_gbn;
    private String srch_duplicated_yn;			//중복여부 검색조건
    private String srch_excel_cd;				//입수데이터 구분 코드
    private String srch_suip_com_cd;			//수입수수료 코드
    private String srch_stat_com_cd;
    private String srch_std_com_cd;
    private String srch_prc_cont_status;
    private String srch_target_cont_status;
    private String srch_target_napmethod;
    private String srch_prc_napmethod;
    private String srch_suip_comm_gbn;
    private String srch_jigub_logic_cd;
    private String srch_inspol_no;
    private String srch_reduce_logic_cd;
    private String srch_ovr_use_yn;
    private String srch_evaluation_logic_cd;
    private String srch_obj_com;
    private String srch_suip_new_cont_err;
    private String srch_target_ovr_com_nm;
    private String srch_scd_head;
    private String srch_tax_type;
    private String srch_payroll_gbn;
    private String srch_other_fees_bigo;
    private String srch_dv_over_gubun; // 분할조직 구분
    private String srch_input_method;
    private String srch_policy_jigub_gbn;
    private String srch_cross_salse_yn;
    private String srch_re_emp_value;		//리크루팅자 사번/사원명
    private String srch_jigub_comm_cd;
    private String srch_intro_gbn;
    private String srch_prod_kind1;
	private String srch_prod_kind2;
	private String srch_add_bonus_yn;
	private String srch_request_state;
	private String srch_client_value;



	private String queryStr;// 최대 조직 경로 갯수만큼 컬럼을 동적으로 생성하기위해, 쿼리를 담는 변수
    
    private String prod_nm;
    
    private String bojang_content;
    private String bojang_detail;
    private String bosang_content;
    
    private String parentWindowID;
    private String callBackTarget;
    private String callBackTargetNumber;
    
    private String[] seq_arr;
    
    private String row_number;
    
	private String[] insco_cd_arr;
	    
    /**  **/
    private List<Map<String, String>> monthOrgPathViewList;
    private List<Map<String, Object>> inscoList_L;
    private List<Map<String, Object>> inscoList_N;
    private String orgUnitGbnText;
    private String[] orgUnitGbnArr;

	private String cls_resource_id;		//마감그룹코드
	    
	public String getSrch_jigubyn() {
		return srch_jigubyn;
	}

	public void setSrch_jigubyn(String srch_jigubyn) {
		this.srch_jigubyn = srch_jigubyn;
	}

	public String getVALUE() {
		return this.cd_vl;
	}

	public String getTEXT() {
		return this.cd_vl_nm;
	}
	
	public boolean getISLEAF_OUT() {
    	if("0".equals(this.isleaf)) 
    		return true;
    	else
    		return false;
	}

	public String getMb_id() {
		return mb_id;
	}

	public void setMb_id(String mb_id) {
		this.mb_id = mb_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	public String getGroup_seq() {
		return group_seq;
	}
	
	public void setGroup_seq(String group_seq) {
		this.group_seq = group_seq;
	}

	public String getRef_seq() {
		return ref_seq;
	}

	public void setRef_seq(String ref_seq) {
		this.ref_seq = ref_seq;
	}

	public String getTemp_seq() {
		return temp_seq;
	}

	public void setTemp_seq(String temp_seq) {
		this.temp_seq = temp_seq;
	}

	public String getRef_group_seq() {
		return ref_group_seq;
	}

	public void setRef_group_seq(String ref_group_seq) {
		this.ref_group_seq = ref_group_seq;
	}
	
	public String getProd_seq() {
		return prod_seq;
	}

	public void setProd_seq(String prod_seq) {
		this.prod_seq = prod_seq;
	}

	public String getProject_type() {
		return project_type;
	}

	public void setProject_type(String project_type) {
		this.project_type = project_type;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMb_nm() {
		return mb_nm;
	}

	public void setMb_nm(String mb_nm) {
		this.mb_nm = mb_nm;
	}

	public String getEmp_cd() {
		return emp_cd;
	}

	public void setEmp_cd(String emp_cd) {
		this.emp_cd = emp_cd;
	}

	public String getEmp_nm() {
		return emp_nm;
	}

	public void setEmp_nm(String emp_nm) {
		this.emp_nm = emp_nm;
	}

	public String getScd() {
		return scd;
	}

	public void setScd(String scd) {
		this.scd = scd;
	}

	public String getSnm() {
		return snm;
	}

	public void setSnm(String snm) {
		this.snm = snm;
	}

	public String getInsco_cd() {
		return insco_cd;
	}

	public void setInsco_cd(String insco_cd) {
		this.insco_cd = insco_cd;
	}

	public String getInsco_nm() {
		return insco_nm;
	}

	public void setInsco_nm(String insco_nm) {
		this.insco_nm = insco_nm;
	}

	public String getInsco_type() {
		return insco_type;
	}

	public void setInsco_type(String insco_type) {
		this.insco_type = insco_type;
	}

	public String getInsco_type_nm() {
		return insco_type_nm;
	}

	public void setInsco_type_nm(String insco_type_nm) {
		this.insco_type_nm = insco_type_nm;
	}

	public String getEmpsta() {
		return empsta;
	}

	public void setEmpsta(String empsta) {
		this.empsta = empsta;
	}

	public String getEmpsta_nm() {
		return empsta_nm;
	}

	public void setEmpsta_nm(String empsta_nm) {
		this.empsta_nm = empsta_nm;
	}

	public String getJikgub() {
		return jikgub;
	}

	public void setJikgub(String jikgub) {
		this.jikgub = jikgub;
	}

	public String getJikchk() {
		return jikchk;
	}

	public void setJikchk(String jikchk) {
		this.jikchk = jikchk;
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	public String getRole_nm() {
		return role_nm;
	}

	public void setRole_nm(String role_nm) {
		this.role_nm = role_nm;
	}

	public List<String> getRole_ids() {
		return role_ids;
	}

	public void setRole_ids(List<String> role_ids) {
		this.role_ids = role_ids;
	}

	public String getIn_dtm() {
		return in_dtm;
	}

	public void setIn_dtm(String in_dtm) {
		this.in_dtm = in_dtm;
	}

	public String getIn_emp_cd() {
		return in_emp_cd;
	}

	public void setIn_emp_cd(String in_emp_cd) {
		this.in_emp_cd = in_emp_cd;
	}

	public String getUp_dtm() {
		return up_dtm;
	}

	public void setUp_dtm(String up_dtm) {
		this.up_dtm = up_dtm;
	}

	public String getUp_emp_cd() {
		return up_emp_cd;
	}

	public void setUp_emp_cd(String up_emp_cd) {
		this.up_emp_cd = up_emp_cd;
	}

	public String getSort_no() {
		return sort_no;
	}

	public void setSort_no(String sort_no) {
		this.sort_no = sort_no;
	}

	public String getSession_time() {
		return session_time;
	}

	public void setSession_time(String session_time) {
		this.session_time = session_time;
	}

	public String getJson_string() {
		return json_string;
	}

	public void setJson_string(String json_string) {
		this.json_string = json_string;
	}

	public String[] getValue_arr() {
		return value_arr;
	}

	public void setValue_arr(String[] value_arr) {
		this.value_arr = value_arr;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUse_yn() {
		return use_yn;
	}

	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}

	public String getUse_yn_nm() {
		return use_yn_nm;
	}

	public void setUse_yn_nm(String use_yn_nm) {
		this.use_yn_nm = use_yn_nm;
	}

	public String getDemo_gubun() {
		return demo_gubun;
	}

	public void setDemo_gubun(String demo_gubun) {
		this.demo_gubun = demo_gubun;
	}

	public String getZipcd() {
		return zipcd;
	}

	public void setZipcd(String zipcd) {
		this.zipcd = zipcd;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	
	public String getHpno() {
		return hpno;
	}

	public void setHpno(String hpno) {
		this.hpno = hpno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMenu_view_type() {
		return menu_view_type;
	}

	public void setMenu_view_type(String menu_view_type) {
		this.menu_view_type = menu_view_type;
	}

	public String getInput_method() {
		return input_method;
	}

	public void setInput_method(String input_method) {
		this.input_method = input_method;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public PageVO getPageVO() {
		return pageVO;
	}

	public void setPageVO(PageVO pageVO) {
		this.pageVO = pageVO;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getPageyn() {
		return pageyn;
	}

	public void setPageyn(String pageyn) {
		this.pageyn = pageyn;
	}

	public propertiesVO getPropertiesvo() {
		return propertiesvo;
	}

	public void setPropertiesvo(propertiesVO propertiesvo) {
		this.propertiesvo = propertiesvo;
	}

	public String getAttach_gbn() {
		return attach_gbn;
	}

	public void setAttach_gbn(String attach_gbn) {
		this.attach_gbn = attach_gbn;
	}

	public String getAttach_gbn_nm() {
		return attach_gbn_nm;
	}

	public void setAttach_gbn_nm(String attach_gbn_nm) {
		this.attach_gbn_nm = attach_gbn_nm;
	}

	public MultipartFile getMultipartfile() {
		return multipartfile;
	}

	public void setMultipartfile(MultipartFile multipartfile) {
		this.multipartfile = multipartfile;
	}

	public List<MultipartFile> getMultipartfileList() {
		return multipartfileList;
	}

	public void setMultipartfileList(List<MultipartFile> multipartfileList) {
		this.multipartfileList = multipartfileList;
	}

	public String getCom_ym() {
		return com_ym;
	}

	public void setCom_ym(String com_ym) {
		this.com_ym = com_ym;
	}

	public String getCom_ym_text() {
		return com_ym_text;
	}

	public void setCom_ym_text(String com_ym_text) {
		this.com_ym_text = com_ym_text;
	}

	public String getExcelpath() {
		return excelpath;
	}

	public void setExcelpath(String excelpath) {
		this.excelpath = excelpath;
	}

	public String getView_auth() {
		return view_auth;
	}

	public void setView_auth(String view_auth) {
		this.view_auth = view_auth;
	}

	public String getSe_autr_type() {
		return se_autr_type;
	}

	public void setSe_autr_type(String se_autr_type) {
		this.se_autr_type = se_autr_type;
	}

	public String getIn_autr_type() {
		return in_autr_type;
	}

	public void setIn_autr_type(String in_autr_type) {
		this.in_autr_type = in_autr_type;
	}

	public String getUp_autr_type() {
		return up_autr_type;
	}

	public void setUp_autr_type(String up_autr_type) {
		this.up_autr_type = up_autr_type;
	}

	public String getDe_autr_type() {
		return de_autr_type;
	}

	public void setDe_autr_type(String de_autr_type) {
		this.de_autr_type = de_autr_type;
	}

	public String getLo_autr_type() {
		return lo_autr_type;
	}

	public void setLo_autr_type(String lo_autr_type) {
		this.lo_autr_type = lo_autr_type;
	}

	public String getDo_autr_type() {
		return do_autr_type;
	}

	public void setDo_autr_type(String do_autr_type) {
		this.do_autr_type = do_autr_type;
	}

	public String getErp_autr_type() {
		return erp_autr_type;
	}

	public void setErp_autr_type(String erp_autr_type) {
		this.erp_autr_type = erp_autr_type;
	}

	public String getLogin_autr_type() {
		return login_autr_type;
	}

	public void setLogin_autr_type(String login_autr_type) {
		this.login_autr_type = login_autr_type;
	}

	public String getUser_scd() {
		return user_scd;
	}

	public void setUser_scd(String user_scd) {
		this.user_scd = user_scd;
	}

	public String getConcurrent_idx() {
		return concurrent_idx;
	}

	public void setConcurrent_idx(String concurrent_idx) {
		this.concurrent_idx = concurrent_idx;
	}

	public String getConcurrent_gubun() {
		return concurrent_gubun;
	}

	public void setConcurrent_gubun(String concurrent_gubun) {
		this.concurrent_gubun = concurrent_gubun;
	}

	public String getResource_url() {
		return resource_url;
	}

	public void setResource_url(String resource_url) {
		this.resource_url = resource_url;
	}

	public String getBigo() {
		return bigo;
	}

	public void setBigo(String bigo) {
		this.bigo = bigo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getKeyid() {
		return keyid;
	}

	public void setKeyid(String keyid) {
		this.keyid = keyid;
	}

	public String getTable_type() {
		return table_type;
	}

	public void setTable_type(String table_type) {
		this.table_type = table_type;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getLv() {
		return lv;
	}

	public void setLv(String lv) {
		this.lv = lv;
	}

	public String getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getSort_column() {
		return sort_column;
	}

	public void setSort_column(String sort_column) {
		this.sort_column = sort_column;
	}
	
	public String getSort_dtm() {
		return sort_dtm;
	}

	public void setSort_dtm(String sort_dtm) {
		this.sort_dtm = sort_dtm;
	}

	public int getPageoffset() {
		return pageoffset;
	}

	public void setPageoffset(int pageoffset) {
		this.pageoffset = pageoffset;
	}

	public String getSrch_work_scope() {
		return srch_work_scope;
	}

	public void setSrch_work_scope(String srch_work_scope) {
		this.srch_work_scope = srch_work_scope;
	}

	public String getSrch_table_comment() {
		return srch_table_comment;
	}

	public void setSrch_table_comment(String srch_table_comment) {
		this.srch_table_comment = srch_table_comment;
	}

	public int getPrc_return_code() {
		return prc_return_code;
	}

	public void setPrc_return_code(int prc_return_code) {
		this.prc_return_code = prc_return_code;
	}

	public String getPrc_return_msg() {
		return prc_return_msg;
	}

	public void setPrc_return_msg(String prc_return_msg) {
		this.prc_return_msg = prc_return_msg;
	}

	public int getPrc_return_cnt() {
		return prc_return_cnt;
	}

	public void setPrc_return_cnt(int prc_return_cnt) {
		this.prc_return_cnt = prc_return_cnt;
	}

	public ArrayList<FileVO> getFileList() {
		return fileList;
	}

	public void setFileList(ArrayList<FileVO> fileList) {
		this.fileList = fileList;
	}

	public String getSearch_word() {
		return search_word;
	}

	public void setSearch_word(String search_word) {
		this.search_word = search_word;
	}

	public String[] getSearch_words() {
		return search_words;
	}

	public void setSearch_words(String[] search_words) {
		this.search_words = search_words;
	}

	public String[] getSearch_arr() {
		return search_arr;
	}

	public void setSearch_arr(String[] search_arr) {
		this.search_arr = search_arr;
	}

	public String getSrch_mb_id() {
		return srch_mb_id;
	}

	public void setSrch_mb_id(String srch_mb_id) {
		this.srch_mb_id = srch_mb_id;
	}

	public String getSrch_scd() {
		return srch_scd;
	}

	public void setSrch_scd(String srch_scd) {
		this.srch_scd = srch_scd;
	}

	public String getSrch_snm() {
		return srch_snm;
	}

	public void setSrch_snm(String srch_snm) {
		this.srch_snm = srch_snm;
	}

	public String getSrch_org_scd() {
		return srch_org_scd;
	}

	public void setSrch_org_scd(String srch_org_scd) {
		this.srch_org_scd = srch_org_scd;
	}

	public String getSrch_jikchk() {
		return srch_jikchk;
	}

	public void setSrch_jikchk(String srch_jikchk) {
		this.srch_jikchk = srch_jikchk;
	}

	public String getSrch_jikgub() {
		return srch_jikgub;
	}

	public void setSrch_jikgub(String srch_jikgub) {
		this.srch_jikgub = srch_jikgub;
	}

	public String getSrch_role_id() {
		return srch_role_id;
	}

	public void setSrch_role_id(String srch_role_id) {
		this.srch_role_id = srch_role_id;
	}

	public String getSrch_empsta() {
		return srch_empsta;
	}

	public void setSrch_empsta(String srch_empsta) {
		this.srch_empsta = srch_empsta;
	}

	public String getSrch_term() {
		return srch_term;
	}

	public void setSrch_term(String srch_term) {
		this.srch_term = srch_term;
	}

	public String getSrch_customer_name() {
		return srch_customer_name;
	}

	public void setSrch_customer_name(String srch_customer_name) {
		this.srch_customer_name = srch_customer_name;
	}
	
	public String getSrch_birthday() {
		return srch_birthday;
	}

	public void setSrch_birthday(String srch_birthday) {
		this.srch_birthday = srch_birthday;
	}

	public String getSrch_telno() {
		return srch_telno;
	}

	public void setSrch_telno(String srch_telno) {
		this.srch_telno = srch_telno;
	}

	public String getSrch_claim_no() {
		return srch_claim_no;
	}

	public void setSrch_claim_no(String srch_claim_no) {
		this.srch_claim_no = srch_claim_no;
	}

	public String getCd_vl() {
		return cd_vl;
	}

	public void setCd_vl(String cd_vl) {
		this.cd_vl = cd_vl;
	}

	public String getCd_vl_nm() {
		return cd_vl_nm;
	}

	public void setCd_vl_nm(String cd_vl_nm) {
		this.cd_vl_nm = cd_vl_nm;
	}

	public String getSrch_emp_value() {
		return srch_emp_value;
	}

	public void setSrch_emp_value(String srch_emp_value) {
		this.srch_emp_value = srch_emp_value;
	}
	
	public String getSrch_emp_value2() {
		return srch_emp_value2;
	}

	public void setSrch_emp_value2(String srch_emp_value2) {
		this.srch_emp_value2 = srch_emp_value2;
	}

	public String getSrch_hpno() {
		return srch_hpno;
	}

	public void setSrch_hpno(String srch_hpno) {
		this.srch_hpno = srch_hpno;
	}

	public String getSrch_org_value() {
		return srch_org_value;
	}

	public void setSrch_org_value(String srch_org_value) {
		this.srch_org_value = srch_org_value;
	}

	public String getSrch_org_open_gbn() {
		return srch_org_open_gbn;
	}

	public void setSrch_org_open_gbn(String srch_org_open_gbn) {
		this.srch_org_open_gbn = srch_org_open_gbn;
	}

	public String getSrch_org_unit_gbn() {
		return srch_org_unit_gbn;
	}

	public void setSrch_org_unit_gbn(String srch_org_unit_gbn) {
		this.srch_org_unit_gbn = srch_org_unit_gbn;
	}

	public String getSrch_com_ym() {
		return srch_com_ym;
	}

	public void setSrch_com_ym(String srch_com_ym) {
		this.srch_com_ym = srch_com_ym;
	}

	public String getSrch_dbattribute() {
		return srch_dbattribute;
	}

	public void setSrch_dbattribute(String srch_dbattribute) {
		this.srch_dbattribute = srch_dbattribute;
	}

	public String getSrch_term_start_value() {
		return srch_term_start_value;
	}

	public void setSrch_term_start_value(String srch_term_start_value) {
		this.srch_term_start_value = srch_term_start_value;
	}

	public String getSrch_term_start_value2() {
		return srch_term_start_value2;
	}

	public void setSrch_term_start_value2(String srch_term_start_value2) {
		this.srch_term_start_value2 = srch_term_start_value2;
	}

	public String getSrch_term_end_value() {
		return srch_term_end_value;
	}

	public void setSrch_term_end_value(String srch_term_end_value) {
		this.srch_term_end_value = srch_term_end_value;
	}

	public String getSrch_term_end_value2() {
		return srch_term_end_value2;
	}

	public void setSrch_term_end_value2(String srch_term_end_value2) {
		this.srch_term_end_value2 = srch_term_end_value2;
	}

	public String getSrch_use_yn() {
		return srch_use_yn;
	}

	public void setSrch_use_yn(String srch_use_yn) {
		this.srch_use_yn = srch_use_yn;
	}

	public String getSrch_insco_type() {
		return srch_insco_type;
	}

	public void setSrch_insco_type(String srch_insco_type) {
		this.srch_insco_type = srch_insco_type;
	}

	public String getSrch_insco_cd() {
		return srch_insco_cd;
	}

	public void setSrch_insco_cd(String srch_insco_cd) {
		this.srch_insco_cd = srch_insco_cd;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getSrch_resource_id() {
		return srch_resource_id;
	}

	public void setSrch_resource_id(String srch_resource_id) {
		this.srch_resource_id = srch_resource_id;
	}

	public List<String> getSrch_resource_arr() {
		return srch_resource_arr;
	}

	public void setSrch_resource_arr(List<String> srch_resource_arr) {
		this.srch_resource_arr = srch_resource_arr;
	}

	public List<HashMap<String, String>> getSrch_resource_list() {
		return srch_resource_list;
	}

	public void setSrch_resource_list(List<HashMap<String, String>> srch_resource_list) {
		this.srch_resource_list = srch_resource_list;
	}

	public String getSrch_state_id() {
		return srch_state_id;
	}

	public void setSrch_state_id(String srch_state_id) {
		this.srch_state_id = srch_state_id;
	}

	public List<String> getSrch_state_arr() {
		return srch_state_arr;
	}

	public void setSrch_state_arr(List<String> srch_state_arr) {
		this.srch_state_arr = srch_state_arr;
	}

	public String getSrch_status_id() {
		return srch_status_id;
	}

	public void setSrch_status_id(String srch_status_id) {
		this.srch_status_id = srch_status_id;
	}

	public List<String> getSrch_status_arr() {
		return srch_status_arr;
	}

	public void setSrch_status_arr(List<String> srch_status_arr) {
		this.srch_status_arr = srch_status_arr;
	}

	public String getSrch_jigub_type_cd() {
		return srch_jigub_type_cd;
	}

	public void setSrch_jigub_type_cd(String srch_jigub_type_cd) {
		this.srch_jigub_type_cd = srch_jigub_type_cd;
	}

	public String getSrch_no_jigub_cd() {
		return srch_no_jigub_cd;
	}

	public void setSrch_no_jigub_cd(String srch_no_jigub_cd) {
		this.srch_no_jigub_cd = srch_no_jigub_cd;
	}

	public String getSrch_insco_emp_cd_gbn() {
		return srch_insco_emp_cd_gbn;
	}

	public void setSrch_insco_emp_cd_gbn(String srch_insco_emp_cd_gbn) {
		this.srch_insco_emp_cd_gbn = srch_insco_emp_cd_gbn;
	}

	public String getSrch_emp_sj_cd() {
		return srch_emp_sj_cd;
	}

	public void setSrch_emp_sj_cd(String srch_emp_sj_cd) {
		this.srch_emp_sj_cd = srch_emp_sj_cd;
	}

	public String getSrch_ddl_filter_value() {
		return srch_ddl_filter_value;
	}

	public void setSrch_ddl_filter_value(String srch_ddl_filter_value) {
		this.srch_ddl_filter_value = srch_ddl_filter_value;
	}

	public String getSrch_turn_cnt_gbn() {
		return srch_turn_cnt_gbn;
	}

	public void setSrch_turn_cnt_gbn(String srch_turn_cnt_gbn) {
		this.srch_turn_cnt_gbn = srch_turn_cnt_gbn;
	}

	public String getSrch_duplicated_yn() {
		return srch_duplicated_yn;
	}

	public void setSrch_duplicated_yn(String srch_duplicated_yn) {
		this.srch_duplicated_yn = srch_duplicated_yn;
	}

	public String getSrch_excel_cd() {
		return srch_excel_cd;
	}

	public void setSrch_excel_cd(String srch_excel_cd) {
		this.srch_excel_cd = srch_excel_cd;
	}

	public String getSrch_suip_com_cd() {
		return srch_suip_com_cd;
	}

	public void setSrch_suip_com_cd(String srch_suip_com_cd) {
		this.srch_suip_com_cd = srch_suip_com_cd;
	}
	
	public String getSrch_stat_com_cd() {
		return srch_stat_com_cd;
	}

	public void setSrch_stat_com_cd(String srch_stat_com_cd) {
		this.srch_stat_com_cd = srch_stat_com_cd;
	}
	
	public String getSrch_std_com_cd() {
		return srch_std_com_cd;
	}

	public void setSrch_std_com_cd(String srch_std_com_cd) {
		this.srch_std_com_cd = srch_std_com_cd;
	}

	public String getSrch_prc_cont_status() {
		return srch_prc_cont_status;
	}

	public void setSrch_prc_cont_status(String srch_prc_cont_status) {
		this.srch_prc_cont_status = srch_prc_cont_status;
	}

	public String getSrch_target_cont_status() {
		return srch_target_cont_status;
	}

	public void setSrch_target_cont_status(String srch_target_cont_status) {
		this.srch_target_cont_status = srch_target_cont_status;
	}

	public String getSrch_target_napmethod() {
		return srch_target_napmethod;
	}

	public void setSrch_target_napmethod(String srch_target_napmethod) {
		this.srch_target_napmethod = srch_target_napmethod;
	}

	public String getSrch_prc_napmethod() {
		return srch_prc_napmethod;
	}

	public void setSrch_prc_napmethod(String srch_prc_napmethod) {
		this.srch_prc_napmethod = srch_prc_napmethod;
	}

	public String getSrch_suip_comm_gbn() {
		return srch_suip_comm_gbn;
	}

	public void setSrch_suip_comm_gbn(String srch_suip_comm_gbn) {
		this.srch_suip_comm_gbn = srch_suip_comm_gbn;
	}

	public String getSrch_jigub_logic_cd() {
		return srch_jigub_logic_cd;
	}

	public void setSrch_jigub_logic_cd(String srch_jigub_logic_cd) {
		this.srch_jigub_logic_cd = srch_jigub_logic_cd;
	}
	
	public String getSrch_inspol_no() {
		return srch_inspol_no;
	}

	public void setSrch_inspol_no(String srch_inspol_no) {
		this.srch_inspol_no = srch_inspol_no;
	}
	
	public String getSrch_reduce_logic_cd() {
		return srch_reduce_logic_cd;
	}

	public void setSrch_reduce_logic_cd(String srch_reduce_logic_cd) {
		this.srch_reduce_logic_cd = srch_reduce_logic_cd;
	}
	
	public String getSrch_ovr_use_yn() {
		return srch_ovr_use_yn;
	}

	public String getSrch_evaluation_logic_cd() {
		return srch_evaluation_logic_cd;
	}

	public void setSrch_evaluation_logic_cd(String srch_evaluation_logic_cd) {
		this.srch_evaluation_logic_cd = srch_evaluation_logic_cd;
	}

	public void setSrch_ovr_use_yn(String srch_ovr_use_yn) {
		this.srch_ovr_use_yn = srch_ovr_use_yn;
	}
	
	public String getSrch_obj_com() {
		return srch_obj_com;
	}

	public void setSrch_obj_com(String srch_obj_com) {
		this.srch_obj_com = srch_obj_com;
	}
	
	public String getSrch_suip_new_cont_err() {
		return srch_suip_new_cont_err;
	}

	public void setSrch_suip_new_cont_err(String srch_suip_new_cont_err) {
		this.srch_suip_new_cont_err = srch_suip_new_cont_err;
	}
	
	public String getSrch_target_ovr_com_nm() {
		return srch_target_ovr_com_nm;
	}

	public void setSrch_target_ovr_com_nm(String srch_target_ovr_com_nm) {
		this.srch_target_ovr_com_nm = srch_target_ovr_com_nm;
	}

	public String getSrch_scd_head() {
		return srch_scd_head;
	}

	public void setSrch_scd_head(String srch_scd_head) {
		this.srch_scd_head = srch_scd_head;
	}

	public String getSrch_tax_type() {
		return srch_tax_type;
	}

	public void setSrch_tax_type(String srch_tax_type) {
		this.srch_tax_type = srch_tax_type;
	}

	public String getSrch_payroll_gbn() {
		return srch_payroll_gbn;
	}

	public void setSrch_payroll_gbn(String srch_payroll_gbn) {
		this.srch_payroll_gbn = srch_payroll_gbn;
	}
	
	public String getSrch_other_fees_bigo() {
		return srch_other_fees_bigo;
	}

	public void setSrch_other_fees_bigo(String srch_other_fees_bigo) {
		this.srch_other_fees_bigo = srch_other_fees_bigo;
	}

	public String getQueryStr() {
		return queryStr;
	}

	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}

	public String getProd_nm() {
		return prod_nm;
	}

	public void setProd_nm(String prod_nm) {
		this.prod_nm = prod_nm;
	}

	public String getBojang_content() {
		return bojang_content;
	}

	public void setBojang_content(String bojang_content) {
		this.bojang_content = bojang_content;
	}

	public String getBojang_detail() {
		return bojang_detail;
	}

	public void setBojang_detail(String bojang_detail) {
		this.bojang_detail = bojang_detail;
	}

	public String getBosang_content() {
		return bosang_content;
	}

	public void setBosang_content(String bosang_content) {
		this.bosang_content = bosang_content;
	}

	public String getParentWindowID() {
		return parentWindowID;
	}

	public void setParentWindowID(String parentWindowID) {
		this.parentWindowID = parentWindowID;
	}

	public String getCallBackTarget() {
		return callBackTarget;
	}

	public void setCallBackTarget(String callBackTarget) {
		this.callBackTarget = callBackTarget;
	}

	public String getCallBackTargetNumber() {
		return callBackTargetNumber;
	}

	public void setCallBackTargetNumber(String callBackTargetNumber) {
		this.callBackTargetNumber = callBackTargetNumber;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String[] getSeq_arr() {
		return seq_arr;
	}

	public void setSeq_arr(String[] seq_arr) {
		this.seq_arr = seq_arr;
	}
	
	public String getRow_number() {
		return row_number;
	}

	public void setRow_number(String row_number) {
		this.row_number = row_number;
	}

	public String[] getInsco_cd_arr() {
		return insco_cd_arr;
	}

	public void setInsco_cd_arr(String[] insco_cd_arr) {
		this.insco_cd_arr = insco_cd_arr;
	}

	public List<Map<String, String>> getMonthOrgPathViewList() {
		return monthOrgPathViewList;
	}

	public void setMonthOrgPathViewList(List<Map<String, String>> monthOrgPathViewList) {
		this.monthOrgPathViewList = monthOrgPathViewList;
	}

	public List<Map<String, Object>> getInscoList_L() {
		return inscoList_L;
	}

	public void setInscoList_L(List<Map<String, Object>> inscoList_L) {
		this.inscoList_L = inscoList_L;
	}

	public List<Map<String, Object>> getInscoList_N() {
		return inscoList_N;
	}

	public void setInscoList_N(List<Map<String, Object>> inscoList_N) {
		this.inscoList_N = inscoList_N;
	}

	public String getOrgUnitGbnText() {
		return orgUnitGbnText;
	}

	public void setOrgUnitGbnText(String orgUnitGbnText) {
		this.orgUnitGbnArr = orgUnitGbnText != null ? orgUnitGbnText.split(",") : null;
		this.orgUnitGbnText = orgUnitGbnText;
	}

	public String[] getOrgUnitGbnArr() {
		return orgUnitGbnArr;
	}

	public void setOrgUnitGbnArr(String[] orgUnitGbnArr) {
		this.orgUnitGbnArr = orgUnitGbnArr;
	}

	public String getSrch_car_jigub_type_cd() {
		return srch_car_jigub_type_cd;
	}

	public void setSrch_car_jigub_type_cd(String srch_car_jigub_type_cd) {
		this.srch_car_jigub_type_cd = srch_car_jigub_type_cd;
	}

	public String getCls_resource_id() {
		return cls_resource_id;
	}

	public void setCls_resource_id(String cls_resource_id) {
		this.cls_resource_id = cls_resource_id;
	}

	public String getSrch_gen_jigub_type_cd() {
		return srch_gen_jigub_type_cd;
	}

	public void setSrch_gen_jigub_type_cd(String srch_gen_jigub_type_cd) {
		this.srch_gen_jigub_type_cd = srch_gen_jigub_type_cd;
	}

	public String getSrch_fc_jigub_type_cd() {
		return srch_fc_jigub_type_cd;
	}

	public void setSrch_fc_jigub_type_cd(String srch_fc_jigub_type_cd) {
		this.srch_fc_jigub_type_cd = srch_fc_jigub_type_cd;
	}

	public String getSrch_ovr_type_cd() {
		return srch_ovr_type_cd;
	}

	public void setSrch_ovr_type_cd(String srch_ovr_type_cd) {
		this.srch_ovr_type_cd = srch_ovr_type_cd;
	}

	public String getSrch_empnm() {
		return srch_empnm;
	}

	public void setSrch_empnm(String srch_empnm) {
		this.srch_empnm = srch_empnm;
	}

	public String getSrch_dv_over_gubun() {
		return srch_dv_over_gubun;
	}

	public void setSrch_dv_over_gubun(String srch_dv_over_gubun) {
		this.srch_dv_over_gubun = srch_dv_over_gubun;
	}

	public String getSrch_input_method() {
		return srch_input_method;
	}

	public void setSrch_input_method(String srch_input_method) {
		this.srch_input_method = srch_input_method;
	}

	public String getSrch_policy_jigub_gbn() {
		return srch_policy_jigub_gbn;
	}

	public void setSrch_policy_jigub_gbn(String srch_policy_jigub_gbn) {
		this.srch_policy_jigub_gbn = srch_policy_jigub_gbn;
	}

	public String getSrch_cross_salse_yn() {
		return srch_cross_salse_yn;
	}

	public void setSrch_cross_salse_yn(String srch_cross_salse_yn) {
		this.srch_cross_salse_yn = srch_cross_salse_yn;
	}

	public String getSrch_re_emp_value() {
		return srch_re_emp_value;
	}

	public void setSrch_re_emp_value(String srch_re_emp_value) {
		this.srch_re_emp_value = srch_re_emp_value;
	}
	
	public String getSrch_jigub_comm_cd() {
		return srch_jigub_comm_cd;
	}

	public void setSrch_jigub_comm_cd(String srch_jigub_comm_cd) {
		this.srch_jigub_comm_cd = srch_jigub_comm_cd;
	}

	public String getSrch_intro_gbn() {
		return srch_intro_gbn;
	}

	public void setSrch_intro_gbn(String srch_intro_gbn) {
		this.srch_intro_gbn = srch_intro_gbn;
	}

	public String getSrch_prod_kind1() {
		return srch_prod_kind1;
	}

	public void setSrch_prod_kind1(String srch_prod_kind1) {
		this.srch_prod_kind1 = srch_prod_kind1;
	}

	public String getSrch_prod_kind2() {
		return srch_prod_kind2;
	}

	public void setSrch_prod_kind2(String srch_prod_kind2) {
		this.srch_prod_kind2 = srch_prod_kind2;
	}

	public String getSrch_add_bonus_yn() {
		return srch_add_bonus_yn;
	}

	public void setSrch_add_bonus_yn(String srch_add_bonus_yn) {
		this.srch_add_bonus_yn = srch_add_bonus_yn;
	}
	public String getSrch_request_state() {
		return srch_request_state;
	}

	public void setSrch_request_state(String srch_request_state) {
		this.srch_request_state = srch_request_state;
	}
	
	public String getSrch_client_value() {
		return srch_client_value;
	}

	public void setSrch_client_value(String srch_client_value) {
		this.srch_client_value = srch_client_value;
	}
}
