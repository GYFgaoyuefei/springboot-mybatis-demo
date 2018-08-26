package cn.edu.nuc.springbootmybatisdynamicmutilds.dao.mysql.ds2.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 * @author kevinliu
 *
 */
public class Ds2DynamicDataSource extends AbstractRoutingDataSource {
	private static Ds2DynamicDataSource instance;
	private static byte[] lock=new byte[0];
	private static Map<Object,Object> dataSourceMap=new HashMap<Object, Object>();
	
	@Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        dataSourceMap.putAll(targetDataSources);
        super.afterPropertiesSet();// 必须添加该句，否则新添加数据源无法识别到
    }

    public Map<Object, Object> getDataSourceMap() {
        return dataSourceMap;
    }
	
	@Override
	protected Object determineCurrentLookupKey() {
		String dbKey = Ds2DatabaseContextHolder.getDBKey();
		/*if (StringUtils.isBlank(dbKey)) {
			dbKey = "read";
		}*/
		return dbKey;
	}

	private Ds2DynamicDataSource() {}
	
	public static synchronized Ds2DynamicDataSource getInstance(){
        if(instance==null){
            synchronized (lock){
                if(instance==null){
                    instance=new Ds2DynamicDataSource();
                }
            }
        }
        return instance;
    }

}
