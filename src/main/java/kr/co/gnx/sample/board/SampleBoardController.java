package kr.co.gnx.sample.board;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * 샘플게시판 (테스트 메뉴용 CRUD 모듈)
 * 화면(JSP) → Controller → Service(트랜잭션) → DAO → MyBatis 매퍼 → MySQL 까지 한 바퀴 도는 걸
 * 확인하려고 만든 가장 단순한 모듈이다. 등록/수정/삭제는 공통코드관리 화면과 같은
 * Kendo 인라인 그리드 방식({"models":[...]} JSON 본문)을 쓴다.
 */
@Controller(value="SampleBoardController")
public class SampleBoardController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(SampleBoardController.class);

	@Autowired
	private SampleBoardService sampleBoardService;

	/**
	 * @brief 샘플게시판 화면 이동
	 */
	@RequestMapping(value = "/sample/board/sampleBoard.go")
	public ModelAndView sampleBoard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("/sample/board/sampleBoard");
	}

	/**
	 * @brief 목록 조회 (search_word: 제목/내용/작성자 검색어)
	 */
	@RequestMapping(value = "/sample/board/getSampleBoardList.ajax")
	public ModelAndView getSampleBoardList(HttpServletRequest request, HttpServletResponse response, SampleBoardVO vo) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");

		// 그리드는 검색조건을 json_string 하나로 묶어 보낸다
		if(CommUtil.isNotEmpty(vo.getJson_string())) {
			JSONObject paramJObj = JSONObject.fromObject(vo.getJson_string());
			vo = (SampleBoardVO) JSONObject.toBean(paramJObj, SampleBoardVO.class);
		}
		vo.setMb_id(getLoginUser(request).getMb_id());

		mv.addObject("results", sampleBoardService.getSampleBoardList(vo));
		return mv;
	}

	/**
	 * @brief 단건 조회 (seq 필수)
	 */
	@RequestMapping(value = "/sample/board/getSampleBoardView.ajax")
	public ModelAndView getSampleBoardView(HttpServletRequest request, HttpServletResponse response, SampleBoardVO vo) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		vo.setMb_id(getLoginUser(request).getMb_id());

		mv.addObject("result", sampleBoardService.getSampleBoardView(vo));
		return mv;
	}

	/**
	 * @brief 등록
	 */
	@RequestMapping(value = "/sample/board/insertSampleBoard.ajax")
	public ModelAndView insertSampleBoard(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject paramJObj) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		List<SampleBoardVO> models = toModels(request, paramJObj);

		mv.addObject("count", sampleBoardService.insertSampleBoard(models));
		return mv;
	}

	/**
	 * @brief 수정
	 */
	@RequestMapping(value = "/sample/board/updateSampleBoard.ajax")
	public ModelAndView updateSampleBoard(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject paramJObj) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		List<SampleBoardVO> models = toModels(request, paramJObj);

		mv.addObject("count", sampleBoardService.updateSampleBoard(models));
		return mv;
	}

	/**
	 * @brief 삭제
	 */
	@RequestMapping(value = "/sample/board/deleteSampleBoard.ajax")
	public ModelAndView deleteSampleBoard(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject paramJObj) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		List<SampleBoardVO> models = toModels(request, paramJObj);

		mv.addObject("count", sampleBoardService.deleteSampleBoard(models));
		return mv;
	}

	private User getLoginUser(HttpServletRequest request) {
		return SessionUtil.getSessionVO(request.getSession()).getUser();
	}

	/**
	 * {"models":[...]} 를 VO 목록으로 바꾸고, 회사코드/작업자 사번은 화면값 대신 로그인 세션 값으로 채운다.
	 */
	@SuppressWarnings("unchecked")
	private List<SampleBoardVO> toModels(HttpServletRequest request, JSONObject paramJObj) {
		User user = getLoginUser(request);
		List<SampleBoardVO> models = new ArrayList<SampleBoardVO>(
				(List<SampleBoardVO>) JSONArray.toCollection(paramJObj.getJSONArray("models"), SampleBoardVO.class));

		for(SampleBoardVO vo : models) {
			vo.setMb_id(user.getMb_id());
			vo.setIn_emp_cd(user.getEmp_cd());
			vo.setUp_emp_cd(user.getEmp_cd());
			if(CommUtil.isEmpty(vo.getWriter_nm())) {
				vo.setWriter_nm(user.getEmp_nm());
			}
		}
		logger.debug("sample board models : " + models.size());
		return models;
	}
}
