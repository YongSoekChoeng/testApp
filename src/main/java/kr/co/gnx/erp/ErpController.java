package kr.co.gnx.erp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.gnx.base.BaseController;
import kr.co.gnx.comm.util.session.SessionUtil;
import kr.co.gnx.security.model.User;
import net.sf.json.JSONObject;

@Controller(value="ErpController")
public class ErpController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * ERP 권한목록 조회
	 * @author KIMDONGUK
	 * @since 2020-11-22
	 * @param HttpServletRequest, HttpServletResponse, ErpVO
	 * @return ModelAndView
	 */
	@RequestMapping(value="/erp/getErpRoleList.ajax")
	public ModelAndView getErpRoleList(HttpServletRequest request, HttpServletResponse response, ErpVO erpVO) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		User UserSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		erpVO.setMb_id(UserSession.getMb_id());
		erpVO.setIn_emp_cd(UserSession.getEmp_cd());
		mv.addObject("results", getErpService().getErpRoleList(erpVO));
		
		return mv;
	}
	
	/**
	 * ERP 인사목록 조회(권한사원 변경용)
	 * @author KIMDONGUK
	 * @since 2020-11-24
	 * @param HttpServletRequest, HttpServletResponse, ErpVO
	 * @return ModelAndView
	 */
	@RequestMapping(value="/erp/getEmpSchList.ajax")
	public ModelAndView getEmpSchList(HttpServletRequest request, HttpServletResponse response, ErpVO erpVO) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		User UserSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		
		ErpVO erpVO2 = new ErpVO();
		
		if(erpVO.getJson_string() == null) {
			erpVO2 = erpVO;
		} else {
			JSONObject paramJObj = JSONObject.fromObject(erpVO.getJson_string());
			erpVO2 = (ErpVO) JSONObject.toBean(paramJObj, ErpVO.class);
		}
		
		erpVO2.setMb_id(UserSession.getMb_id());
		erpVO2.setIn_emp_cd(UserSession.getEmp_cd());
		
		mv.addObject("results", getErpService().getEmpSchList(erpVO2));
				
		return mv;
	}
}
