package kr.co.gnx.system.menu;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.gnx.base.BaseService;

@Service
public class MenuService extends BaseService {

	@Autowired
	private MenuDAO menuDAO;

	/**
	 * @brief 메뉴 목록 조회
	 * @param MenuVO (mb_id 필수)
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getMenuList(MenuVO menuVO) {
		return menuDAO.selectMenuList(menuVO);
	}
}
