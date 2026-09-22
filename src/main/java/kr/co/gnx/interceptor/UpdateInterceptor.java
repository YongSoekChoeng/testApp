package kr.co.gnx.interceptor;

import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Update Interceptor
 * @author kihon
 *
 */
@Intercepts
(
     {
          @Signature(type = StatementHandler.class, method = "update", args = {Statement.class})
         ,@Signature(type = StatementHandler.class, method = "query",  args = {Statement.class, ResultHandler.class}) 
     }
)
public class UpdateInterceptor implements Interceptor
{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    //@Override
    public Object intercept(Invocation invocation) throws Throwable
    {
        StatementHandler handler = (StatementHandler) invocation.getTarget();
        
        //쿼리문
        String sql = handler.getBoundSql().getSql();

        //매핑 자료
        String param = handler.getParameterHandler().getParameterObject() != null ? handler.getParameterHandler().getParameterObject().toString() : "";

        //System.out.println("--------------------------------");
        //System.out.println(sql);
        //System.out.println(param);
        //System.out.println("--------------------------------");
        //System.out.println(invocation.getMethod().getName());
        //System.out.println(invocation.getMethod().getAnnotations());
        //System.out.println("--------------------------------");
        
        logger.debug(sql);
        logger.debug(param);
       
        return invocation.proceed();
    }


    //@Override
    public Object plugin(Object target)
    {
        return Plugin.wrap(target, this);
    }


    //@Override
    public void setProperties(Properties properties)
    {
    }
}

