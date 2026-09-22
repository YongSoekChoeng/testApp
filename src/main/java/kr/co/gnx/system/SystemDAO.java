package kr.co.gnx.system;

import java.util.Map;
import org.springframework.stereotype.Repository;

import kr.co.gnx.base.BaseDAO;

@Repository(value="SystemDAO")
public class SystemDAO extends BaseDAO{
	
	private String SQL_PREFIX = "";
	
	public Map<String,String> name() {
		getSqlSession().selectOne(SQL_PREFIX+"");
		return null;
	}
}
