package kr.co.gnx.system.menu;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.co.gnx.base.BaseDAO;

@Repository(value="MenuDAO")
public class MenuDAO extends BaseDAO {

	/**
	 * @brief 메뉴 목록 조회
	 * @param MenuVO
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> selectMenuList(MenuVO menuVO) {
		return getSqlSession().selectList(getMenumapper() + "selectMenuList", menuVO);
	}
}
