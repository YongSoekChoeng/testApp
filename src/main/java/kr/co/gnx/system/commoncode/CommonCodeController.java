package kr.co.gnx.system.commoncode;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.gnx.base.BaseController;
import kr.co.gnx.comm.util.CommUtil;
import kr.co.gnx.comm.util.session.SessionUtil;
import kr.co.gnx.security.model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller(value="CommonCodeController")
public class CommonCodeController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(CommonCodeController.class);
	/**
	 * @brief 공통코드 조회
	 * @param request
	 * @param response
	 * @param CommonCodeVO
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/commoncode/getCommonCode.ajax")
	public ModelAndView getCommonCode(HttpServletRequest request, HttpServletResponse response,CommonCodeVO commonCodeVO) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		User UserSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		commonCodeVO.setMb_id(UserSession.getMb_id());
		
		mv.addObject("results", getCommonCodeService().getCommonCodeList(commonCodeVO));
		return mv;
	}
	
	/**
	 * @brief 보증보험, 기타담보 보증목적구분
	 * @param request
	 * @param response
	 * @param CommonCodeVO
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/commoncode/getAllBondGbn.ajax")
	public ModelAndView getAllBondGbn(HttpServletRequest request, HttpServletResponse response,CommonCodeVO commonCodeVO) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		User UserSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		commonCodeVO.setMb_id(UserSession.getMb_id());
		commonCodeVO.setIn_emp_cd(UserSession.getEmp_cd());
		mv.addObject("results", getCommonCodeService().getAllBondGbn(commonCodeVO));
		return mv;
	}
	
	/**
	 * @brief 보증보험, 기타담보 보증종류
	 * @param request
	 * @param response
	 * @param CommonCodeVO
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/commoncode/getAllBondType.ajax")
	public ModelAndView getAllBondType(HttpServletRequest request, HttpServletResponse response,CommonCodeVO commonCodeVO) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		User UserSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		commonCodeVO.setMb_id(UserSession.getMb_id());
		commonCodeVO.setIn_emp_cd(UserSession.getEmp_cd());
		mv.addObject("results", getCommonCodeService().getAllBondType(commonCodeVO));
		return mv;
	}
	
		
	/**
	 * @brief 공통코드 화면 이동
	 * @author KIMDONGUK
	 * @since 2019-04-22
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/commoncode/commoncode.go")
	public ModelAndView commoncode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/system/commoncode/commoncode");
		
		return mv;
	}
	
	/**
	 * @brief 공통코드 그룹 리스트 조회
	 * @author KIMDONGUK
	 * @since 2019-04-02
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @param CommonCodeVO
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/commoncode/getCommonCodeGroup.ajax")
	public ModelAndView getCommonCodeGroup(HttpServletRequest request, HttpServletResponse response, CommonCodeVO commonCodeVO) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		User userSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		getCommonCodeService().setUserSession(userSession);
		
		if(CommUtil.isNotEmpty(commonCodeVO.getJson_string())) {
			JSONObject paramJObj = JSONObject.fromObject(commonCodeVO.getJson_string());
			commonCodeVO = (CommonCodeVO) JSONObject.toBean(paramJObj, CommonCodeVO.class);
		}
		
		mv.addObject("results", getCommonCodeService().getCommonCodeGroupList(commonCodeVO));
		
		return mv;
	}
	
	/**
	 * @brief 공통코드그룹 입력
	 * @author KIMDONGUK
	 * @since 2019-04-22
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @param JSONObject
	 * @return ModelAndView
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/commoncode/insertCommonCodeGroup.ajax")
	public ModelAndView insertCommonCodeGroup(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject paramJObj) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		//사용자 세션 정보 세팅
		User userSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		getCommonCodeService().setUserSession(userSession);
		
		List<CommonCodeVO> models = (ArrayList<CommonCodeVO>) JSONArray.toCollection(paramJObj.getJSONArray("models"), CommonCodeVO.class);
		
		//공통코드그룹 입력
		getCommonCodeService().insertCommonCodeGroup(models);
		
		return mv;
	}
	
	/**
	 * @brief 공통코드그룹 수정
	 * @author KIMDONGUK
	 * @since 2019-04-22
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @param JSONObject
	 * @return ModelAndView
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/commoncode/updateCommonCodeGroup.ajax")
	public ModelAndView updateCommonCodeGroup(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject paramJObj) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		//사용자 세션 정보 세팅
		User userSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		getCommonCodeService().setUserSession(userSession);
		
		List<CommonCodeVO> models = (ArrayList<CommonCodeVO>) JSONArray.toCollection(paramJObj.getJSONArray("models"), CommonCodeVO.class);
		
		//공통코드그룹 입력
		getCommonCodeService().updateCommonCodeGroup(models);
		
		return mv;
	}
	
	/**
	 * @brief 공통코드그룹 삭제
	 * @author KIMDONGUK
	 * @since 2019-04-22
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @param JSONObject
	 * @return ModelAndView
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/commoncode/deleteCommonCodeGroup.ajax")
	public ModelAndView deleteCommonCodeGroup(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject paramJObj) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		//사용자 세션 정보 세팅
		User userSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		getCommonCodeService().setUserSession(userSession);
		
		List<CommonCodeVO> models = (ArrayList<CommonCodeVO>) JSONArray.toCollection(paramJObj.getJSONArray("models"), CommonCodeVO.class);
		
		//공통코드그룹 입력
		getCommonCodeService().deleteCommonCodeGroup(models);
		
		return mv;
	}
	
	/**
	 * @brief 공통코드 입력
	 * @author KIMDONGUK
	 * @since 2019-04-22
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @param JSONObject
	 * @return ModelAndView
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/commoncode/insertCommonCode.ajax")
	public ModelAndView insertCommonCode(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject paramJObj) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		//사용자 세션 정보 세팅
		User userSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		getCommonCodeService().setUserSession(userSession);
		
		List<CommonCodeVO> models = (ArrayList<CommonCodeVO>) JSONArray.toCollection(paramJObj.getJSONArray("models"), CommonCodeVO.class);
		
		//공통코드그룹 입력
		getCommonCodeService().insertCommonCode(models);
		
		return mv;
	}
	
	/**
	 * @brief 공통코드 수정
	 * @author KIMDONGUK
	 * @since 2019-04-22
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @param JSONObject
	 * @return ModelAndView
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/commoncode/updateCommonCode.ajax")
	public ModelAndView updateCommonCode(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject paramJObj) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		//사용자 세션 정보 세팅
		User userSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		getCommonCodeService().setUserSession(userSession);
		
		List<CommonCodeVO> models = (ArrayList<CommonCodeVO>) JSONArray.toCollection(paramJObj.getJSONArray("models"), CommonCodeVO.class);
		
		//공통코드그룹 입력
		getCommonCodeService().updateCommonCode(models);
		
		return mv;
	}
	
	/**
	 * @brief 공통코드 삭제
	 * @author KIMDONGUK
	 * @since 2019-04-22
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @param JSONObject
	 * @return ModelAndView
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/commoncode/deleteCommonCode.ajax")
	public ModelAndView deleteCommonCode(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject paramJObj) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		//사용자 세션 정보 세팅
		User userSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		getCommonCodeService().setUserSession(userSession);
		
		List<CommonCodeVO> models = (ArrayList<CommonCodeVO>) JSONArray.toCollection(paramJObj.getJSONArray("models"), CommonCodeVO.class);
		
		//공통코드그룹 입력
		getCommonCodeService().deleteCommonCode(models);
		
		return mv;
	}

	/**
	 * @brief 상품구분2 조회
	 * @param request
	 * @param response
	 * @param CommonCodeVO
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/commoncode/getProductgroupKind2.ajax")
	public ModelAndView getProductgroupKind2(HttpServletRequest request, HttpServletResponse response,CommonCodeVO commonCodeVO) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		User UserSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		commonCodeVO.setMb_id(UserSession.getMb_id());

		mv.addObject("results", getCommonCodeService().getProductgroupKind2(commonCodeVO));
		return mv;
	}
	
}
