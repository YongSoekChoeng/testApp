package kr.co.gnx.system.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import kr.co.gnx.base.BaseController;
import kr.co.gnx.comm.excel.excelHandler2;
import kr.co.gnx.comm.util.CommUtil;
import kr.co.gnx.comm.util.session.SessionUtil;
import kr.co.gnx.config.Constants.UPLOADS;
import kr.co.gnx.security.model.User;
import kr.co.gnx.system.file.FileVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller(value="MemberController")
public class MemberController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	/**
	 * @Description  : 회원사관리 페이지 이동
	 * @author       : lakhyun.kim
	 * @since        : 2019. 05. 02
	 * @return       : ModelAndView
	 */
	@RequestMapping(value = "/system/member/member.go")
	public ModelAndView member(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView("/system/member/member");
		User UserSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		Map<String, String> tempmap = new HashMap<String, String>();
		tempmap.put("ROLE_ID", UserSession.getRole_id());
		mv.addObject("User", tempmap);
		return mv;
	}

	/**
	 * @Description  : 회원사관리 조회
	 * @author       : lakhyun.kim
	 * @since        : 2019. 05. 02
	 * @return       : ModelAndView
	 */
	@RequestMapping(value = "/system/member/getMemberList.ajax")
	public ModelAndView getMemberList(HttpServletRequest request, HttpServletResponse response, MemberVO memberVO) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		User UserSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		if(memberVO.getJson_string() == null) {
			
		}else {
			String Page  = memberVO.getPage();
			String pageSize = memberVO.getPageSize();
			JSONObject paramJObj = JSONObject.fromObject((memberVO).getJson_string());
			memberVO = (MemberVO) JSONObject.toBean(paramJObj, MemberVO.class);
			memberVO.setPage(Page);
			memberVO.setPageSize(pageSize);
		}
		memberVO.setMb_id(UserSession.getMb_id());
		mv.addObject("results", getMemberService().getMemberList(memberVO));
		return mv;
	}

	/**
	 * @Description  : 회원사관리 조회
	 * @author       : lakhyun.kim
	 * @since        : 2019. 05. 02
	 * @return       : ModelAndView
	 */
	@RequestMapping(value = "/system/member/getMemberListExcel.ajax")
	public void getMemberListExcel(HttpServletRequest request, HttpServletResponse response, MemberVO memberVO) throws Exception{
		User UserSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		memberVO.setMb_id(UserSession.getMb_id());
		
		excelHandler2 eh =  getMemberService().getMemberListExcel(memberVO);
		
		if (eh.getRowindex() == 0) {
			CommUtil.sendGenexonAlert(response, "info", "고객사관리 - 엑셀다운로드", "조회 데이타가 없습니다.");
		} else {
			String filename = "고객사관리_" + CommUtil.getCurrentDateTime() + ".xlsx";
			eh.sendResponse(response, filename);
		}
	}
	
	/**
	 * @Description  : 회원사관리 등록/수정 팝업
	 * @author       : lakhyun.kim
	 * @since        : 2019. 05. 02
	 * @return       : ModelAndView
	 */
	@RequestMapping(value = "/system/member/memberRegi.pop")
	public ModelAndView regiInscoPop(HttpServletRequest request, HttpServletResponse response, MemberVO memberVO) throws Exception{
		ModelAndView mv = new ModelAndView("/system/member/memberRegi");
		if (null != memberVO.getJson_string()) {
			JSONObject paramJObj = JSONObject.fromObject(memberVO.getJson_string());
			memberVO = (MemberVO) JSONObject.toBean(paramJObj,MemberVO.class);
		} else {
			
		}
		
		mv.addObject("MemberVO", memberVO);
		
		return mv;
	}
	
	/**
	 * @Description  : 회원사관리 등록/수정
	 * @author       : lakhyun.kim
	 * @since        : 2019. 05. 02
	 * @return       : ModelAndView
	 */
	@RequestMapping(value = "/system/member/insertMember.ajax")
	public ModelAndView insertMember(HttpServletRequest request, HttpServletResponse response, MemberVO memberVO) throws Exception{
		ModelAndView mav = new ModelAndView("jsonView"); 
		User UserSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		getMemberService().setUserSession(UserSession);
        mav.addObject("results", getMemberService().insertMember(memberVO));
        return mav;
	}
	
	
	/**
	 * @Description  : 회원사관리 등록/수정 전 사업자번호 중복 확인
	 * @author       : lakhyun.kim
	 * @since        : 2019. 05. 02
	 * @return       : ModelAndView
	 */
	@RequestMapping(value = "/system/member/getMemberTaxCheck.ajax")
	public ModelAndView getMemberTaxCheck(HttpServletRequest request, HttpServletResponse response, MemberVO memberVO) throws Exception{
		ModelAndView mav = new ModelAndView("jsonView"); 
		User UserSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		getMemberService().setUserSession(UserSession);
        mav.addObject("results", getMemberService().getMemberTaxCheck(memberVO));
        return mav;
	}
	

	/**
	 * @Description  : 회원사관리 삭제
	 * @author       : lakhyun.kim
	 * @since        : 2019. 05. 02
	 * @return       : ModelAndView
	 */
	@RequestMapping(value = "/system/member/deleteMember.ajax")
	public ModelAndView deleteMember(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject paramJObj) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		ArrayList<MemberVO> models = (ArrayList<MemberVO>) JSONArray.toCollection(paramJObj.getJSONArray("models"), MemberVO.class);
		MemberVO memberVO = new MemberVO();
		memberVO = models.get(0);
		User UserSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		getMemberService().setUserSession(UserSession);
		mv.addObject("results", getMemberService().deleteMember(memberVO));
		return mv;
	}

	/**
	 * @Description  : 회원사관리 이미지 수정
	 * @author       : lakhyun.kim
	 * @since        : 2019. 05. 02
	 * @return       : ModelAndView
	 */
	@RequestMapping(value = "/system/member/infoUrl.ajax")
	public ModelAndView infoUrl(MultipartHttpServletRequest request, MemberVO memberVO) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		User UserSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		java.util.Iterator<String> itr = request.getFileNames();
		if (itr.hasNext()) {
			MultipartFile file = request.getFile(itr.next());
			FileVO fileVO = new FileVO();
			fileVO.setIn_emp_cd(UserSession.getEmp_cd());
			fileVO.setMb_id(memberVO.getMb_id());
			UserSession.setMb_id(memberVO.getMb_id());	// 수정하는 회사코드로 셋팅
			getFileService().setUserSession(UserSession);
			fileVO = getFileService().imageSave(file, UPLOADS.UPIMAGE);
			mv.addObject("FileVO", fileVO);
		}
		return mv;
	}

	/**
	 * @Description  : 회원사관리 이미지 삭제
	 * @author       : lakhyun.kim
	 * @since        : 2019. 05. 02
	 * @return       : ModelAndView
	 */
	@RequestMapping(value = "/system/member/deleteUrl.ajax")
	public ModelAndView deleteUrl(HttpServletRequest request, HttpServletResponse response, FileVO fileVO) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		User UserSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		UserSession.setMb_id(fileVO.getMb_id());	// 수정하는 회사코드로 셋팅
		getFileService().setUserSession(UserSession);
		getFileService().deleteFile(fileVO);
		return mv;
	}
}
