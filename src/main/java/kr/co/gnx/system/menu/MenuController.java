package kr.co.gnx.system.menu;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.gnx.comm.util.session.SessionUtil;
import kr.co.gnx.comm.util.session.SessionVO;

/**
 * 메뉴 조회
 * index.jsp가 화면을 열 때 이 URL로 메뉴 목록을 받아서 상단/좌측 메뉴를 그린다.
 */
@Controller(value="MenuController")
public class MenuController {
	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

	@Autowired
	private MenuService menuService;

	/**
	 * @brief 로그인한 회사(mb_id)의 메뉴 목록 조회
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView (results: 메뉴 목록 - 부모 메뉴가 항상 자식보다 먼저 나온다)
	 */
	@RequestMapping(value = "/menu/getMenuList.ajax")
	public ModelAndView getMenuList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");

		// 로그인 전이면 빈 메뉴를 준다(화면은 어차피 로그인으로 돌아간다)
		SessionVO sessionVO = SessionUtil.getSessionVO(request.getSession());
		if(sessionVO == null || sessionVO.getUser() == null) {
			logger.debug("getMenuList.ajax - 세션 없음, 빈 메뉴 반환");
			mv.addObject("results", new ArrayList<Object>());
			return mv;
		}

		MenuVO menuVO = new MenuVO();
		menuVO.setMb_id(sessionVO.getUser().getMb_id());

		mv.addObject("results", menuService.getMenuList(menuVO));
		return mv;
	}
}
