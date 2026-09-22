package kr.co.gnx.security;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.gnx.security.model.Role;
import kr.co.gnx.security.model.User;

@Service("userService")
public class UserService{
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	StandardPasswordEncoder standardPasswordEncoder;
	

	public User getUserView(Map<String, String> map){
		logger.debug("UserService.getUserView");
		User user = null;
		try {
			user = userDAO.SelectUserView(map);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public List<Role> getRolesHierarchyList(Map<String,String> map) {
		logger.debug("UserService.getRolesHierarchyList");
		@SuppressWarnings("rawtypes")
		List list = null;
		list = userDAO.SelectRolesHierarchyList(map);
		return list;
	}
	
	public Integer getRoleCheck(Map<String,String> map) {
		logger.debug("UserService.getRoleCheck");
		int integer = 0;
		try {
			integer = userDAO.SelectRoleCheck(map);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return integer;
	}
	
	
	/**
	 * 설계사 정보 단건 조회 - ERP에서 조회
	 * @since 2020-11-22
	 * @author KIMDONGUK
	 * @param Map<String, String>
	 * @return User
	 */
	public User selectErpUserView(Map<String, String> map) {
		logger.debug("UserService.selectErpUserView");
		
		return userDAO.selectErpUserView(map);
	}
	
	/**
	 * 권한 하위계층조회 - ERP에서 조회
	 * @since 2020-11-22
	 * @author KIMDONGUK
	 * @param Map<String, String>
	 * @return User
	 */
	public List<Role> selectErpRolesHierarchyList(Map<String, String> map) {
		logger.debug("UserService.selectErpRolesHierarchyList");
		
		return userDAO.selectErpRolesHierarchyList(map);
	}
}
