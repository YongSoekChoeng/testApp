package kr.co.gnx.security;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import kr.co.gnx.base.BaseDAO;
import kr.co.gnx.security.model.Role;
import kr.co.gnx.security.model.User;



@Repository("UserDAO")
public class UserDAO extends BaseDAO{
	private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);
	
	public User SelectUserView(Map<String, String> map) {
		logger.debug("UserDAO.SelectUserView");
		
		return getSqlSession().selectOne(getSpringsecuritymapper()+"SelectUserView", map);
	}
	
	public List<Role> SelectRolesHierarchyList(Map<String, String> map) {
		logger.debug("UserDAO.SelectRolesHierarchyList");
		return getSqlSession().selectList(getSpringsecuritymapper()+"SelectRolesHierarchyList", map);
	}
	
	public Integer SelectRoleCheck(Map<String, String> map) {
		logger.debug("UserDAO.SelectRoleCheck");
		return getSqlSession().selectOne(getSpringsecuritymapper()+"SelectRoleCheck", map);
	}
	
	/**
	 * 설계사 정보 단건 조회 - ERP에서 조회
	 * @since 2020-11-22
	 * @author KIMDONGUK
	 * @param Map<String, String>
	 * @return User
	 */
	public User selectErpUserView(Map<String, String> map) {
		logger.debug("UserDAO.selectErpUserView");
		
		return getSqlSessionAnybiz().selectOne(getSpringsecuritymapper() + "selectErpUserView", map);
	}
	
	/**
	 * 권한 하위계층조회 - ERP에서 조회
	 * @since 2020-11-22
	 * @author KIMDONGUK
	 * @param Map<String, String>
	 * @return User
	 */
	public List<Role> selectErpRolesHierarchyList(Map<String, String> map) {
		logger.debug("UserDAO.selectErpRolesHierarchyList");
		
		return getSqlSessionAnybiz().selectList(getSpringsecuritymapper() + "selectErpRolesHierarchyList", map);
	}
}