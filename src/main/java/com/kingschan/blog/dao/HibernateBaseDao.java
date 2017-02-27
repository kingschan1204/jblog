package com.kingschan.blog.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
/**
 *
*
* 类名称：BaseDao
* 类描述：
* 创建人：陈国祥   (kingschan)
* 创建时间：2015-5-19 下午4:23:28
* 修改人：Administrator
* 修改时间：2015-5-19 下午4:23:28
* 修改备注：
* @version V1.0
*
 */
@Repository("HibernateBaseDao")
@SuppressWarnings("unchecked")
public class HibernateBaseDao  {
	public Logger log =  LoggerFactory.getLogger(HibernateBaseDao.class);
	private static int  BATCH_SIZE=50;

	@Autowired
	private SessionFactory sessionfactory;

	public SessionFactory getSessionfactory() {
		return sessionfactory;
	}


	public void setSessionfactory(SessionFactory sessionfactory) {
	    Map<String, ClassMetadata> map = sessionfactory.getAllClassMetadata();
        Iterator<String> keys = map.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            ClassMetadata cmd =map.get(key);
            System.out.println(String.format("主键字段%s,主键类型%s", cmd.getIdentifierPropertyName(),
                cmd.getIdentifierType().getName()
                ));
            System.out.println(cmd.getMappedClass().getSimpleName());// 对到映射的类
//          System.out.println(cmd.getEntityName());//对应实体完整类名
          //  System.out.println(cmd.toString());
        }
		this.sessionfactory = sessionfactory;
		log.info("注入sessionfactory");
	}

	public Session getSession() {
	    Session session = null;
        try
        {
            session = sessionfactory.getCurrentSession();
//            log.info("只有在事务管理下，才能通过 getCurrentSession() 得到 session ，如果显示这个信息，则说明事务管理工作起作用了。");
        }
        catch(HibernateException ex)
        {
            session = sessionfactory.openSession();
            log.info("*************************************************************");
            log.info("*                                                           *");
            log.error("*  通过  openSession() 得到。事务管理没有起作用，需要检查配置。  *"+ex);
            log.info("*                                                           *");
            log.info("*************************************************************");
        }
        return session;
	}
	/**
	 * 得到全文检索会话对象
	 * @return
	 */
	public FullTextSession getFullTextSession(){
	    return Search.getFullTextSession(getSession());
	}
	 /**
     * hibernate search 全文检索分页查询
     * @param keyword  关键字
     * @param clazz 类型
     * @param pageindex 页码
     * @param limit 显示条数
     * @param cacheable 是否缓存
     * @param fields 字段名
     * @return
     */
    public Pagination Paginations(String keyword, Class<?> clazz, int pageindex, int limit, boolean cacheable, String...fields) {
        Pagination page = new Pagination();
        List<?> lis =null;

        QueryBuilder qb = getFullTextSession().getSearchFactory()
        .buildQueryBuilder().forEntity(clazz).get();
        org.apache.lucene.search.Query query = qb
        .keyword().onFields(fields).matching(keyword)
        .createQuery();
        // wrap Lucene query in a org.hibernate.Query
        FullTextQuery fullTextQuery = getFullTextSession().createFullTextQuery(query, clazz);
        fullTextQuery.setCacheable(cacheable);
        fullTextQuery.setFirstResult((pageindex-1)*limit);
        fullTextQuery.setMaxResults(limit);
        lis =fullTextQuery.list();
        //builder Pagination  对象
        page.setData(lis);
        page.setLimit(limit);
        page.setPageindex(pageindex);
        Integer total=fullTextQuery.getResultSize();
        page.setTotal(total);
        page.setMaxPage((total-1)/limit+1);
        return page;
    }
	/**
	 * 保存一个对象
	 * @param obj
	 */
	public void save(Object obj) {
		Session s = getSession();
		s.save(obj);
	}
	/**
	 * 保存多个对象
	 * @param lis
	 */
	public void saveList(List<?> lis){
		Session session = getSession();
		session.saveOrUpdate(lis);
		/*for(int a=0;a<lis.size();a++){
			 session.save(lis.get(a));
			 if (a % BATCH_SIZE == 0) {
               session.flush();
               session.clear();
           }
		 }*/
	}
	/**
	 * 根据hql返回一个list  如果有参数刚hql用?来表示条件
	 * @param hql
	 * @param cacheable 是否开启缓存
	 * @param mapformat 是否把结果格式化成list[map]
	 * @param args 从1开始
	 * @return
	 */
	public List<?> queryForListByHql(String hql,boolean cacheable,boolean mapformat,Object...args) {
		List<?> lis =null;
		Query q =createHqlQuery(hql, args);
		q.setCacheable(cacheable);
		if (mapformat) {
			q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		}
		lis = q.list();
		return lis;
	}
    /**
     * 根据hql返回一个list  如果有参数刚hql用?来表示条件
     * @param hql
     * @param cacheable 是否开启缓存
     * @param mapformat 是否把结果格式化成list[map]
     * @param map 参数
     * @return
     */
    public List<?> queryForListByHqlMapStyle(String hql,boolean cacheable,boolean mapformat,Map<String,Object> map) {
        List<?> lis =null;
        Query q =createHqlQuery(hql, map);
        q.setCacheable(cacheable);
        if (mapformat) {
            q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        }
        lis = q.list();
        return lis;
    }
	/**
	 * sql查询
	 * @param sql
	 * @param cacheable 是否开启缓存
	 * @param args
	 * @return
	 */
	public List<Object[]> queryForListBySql(String sql,boolean cacheable,Object...args) {
		List<?> lis =null;
		Query q = createSqlQuery(sql, args);
		q.setCacheable(cacheable);
		lis = q.list();
		return (List<Object[]>) lis;
	}
	  /**
     * 执行sql语句返回list【map【String,object】】
     * @param sql
     * @param objects
     * @return
     */
    public List<?> queryForListMapBySql(String sql ,boolean cacheable,Object...objects){
        Query q =createSqlQuery(sql, objects);
        q.setCacheable(cacheable);
        q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return q.list();
    }
    /**
     * 执行sql语句返回list【map【String,object】】
     * @param sql
     * @param cacheable
     * @param map
     * @return
     */
    public List<?> queryForListMapBySql2(String sql ,boolean cacheable,Map<String, Object> map){
        Query q =createSqlQuery(sql, map);
        q.setCacheable(cacheable);
        q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return  q.list();
    }
	/**
	 * hql单一值查值
	 * @param hql
	 * @param cacheable 查询缓存
	 * @param args
	 * @return
	 */
    public Object uniqueQueryByHql(String hql,boolean cacheable, Object...args) {
        Query q = createHqlQuery(hql, args);
        q.setCacheable(cacheable);
        return q.uniqueResult();
    }

    /**
     * sql 单一值查值
     * @param hql
     * @param cacheable
     * @param params
     * @return
     */
    public Object uniqueQueryByHQL(String hql,boolean cacheable, Map<String, Object> params) {
        Query q = createHqlQuery(hql, params);
        q.setCacheable(cacheable);
        return q.uniqueResult();
    }

    /**
     * sql 单一值查值
     * @param sql
     * @param cacheable
     * @param params
     * @return
     */
    public Object uniqueQueryBySql(String sql,boolean cacheable, Map<String, Object> params) {
        Query q = createSqlQuery(sql, params);
        q.setCacheable(cacheable);
        return q.uniqueResult();
    }

    /***
     * sql 单一值查值
     * @param sql
     * @param args
     * @return
     */
    public Object uniqueQueryBySql(String sql,boolean cacheable,Object...args) {
        Query q = createSqlQuery(sql, args);
        q.setCacheable(cacheable);
        return q.uniqueResult();
    }
    /**
     * 本地命名查询返回List<Map>
     * @return
     * @throws Exception
     */
    public List<?> queryForListByNameQuery(String key,boolean cacheable,Object...args)throws Exception{
        List<Map<String, Object>> lis = null;
        Session s = getSession();
        Query q = s.getNamedQuery(key);
        if (null!=args&&args.length>0) {
            for (int i = 0; i < args.length; i++) {
                q.setParameter(i, args[i]);
            }
        }
        q.setCacheable(cacheable);
        q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        lis = q.list();
        return lis;
    }

	/**
	 * 分页查询
	 * @param hql
	 * @param pageindex
	 * @param limit
	 * @param args
	 * @return
	 */
	public List<?> PaginationByHql(String hql,int pageindex,int limit,boolean cacheable,Object...args) {
		List<?> lis =null;
		Query q =createHqlQuery(hql, args);
		q.setCacheable(cacheable);
		q.setFirstResult((pageindex-1)*limit);
		q.setMaxResults(limit);
		lis = q.list();

		return lis;
	}
	/**
	 * 根据sql查指定条  不统计条数
	 * @param sql
	 * @param pageindex
	 * @param limit
	 * @param cacheable
	 * @param args
	 * @return
	 */
	public List<Map<String, Object>> PaginationBySQL(String sql,int pageindex,int limit,boolean cacheable,Map<String, Object> args) {
		List<Map<String, Object>> lis =null;
        Query q = createSqlQuery(sql, args);
        q.setCacheable(cacheable);
        q.setFirstResult((pageindex-1)*limit);
        q.setMaxResults(limit);
        q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        lis = q.list();
        return lis;
    }
	/**
	 * 分页
	 * @param hql
	 * @param pageindex
	 * @param limit
	 * @param args
	 * @return
	 */
	public List<?> PaginationByHql(String hql,int pageindex,int limit,boolean cacheable,Map<String, Object> args) {
        List<?> lis =null;
        Query q = createHqlQuery(hql, args);
        q.setCacheable(cacheable);
        q.setFirstResult((pageindex-1)*limit);
        q.setMaxResults(limit);
        lis = q.list();
        return lis;
    }
	/**
     * 分页统计
     * @param hql
     * @param pageindex
     * @param limit
     * @param cacheable
     * @param args
     * @return
     */
	public Pagination PaginationsByHQLMapParams(String hql,int pageindex,int limit,boolean cacheable,Map<String, Object> args ) {
	    Pagination page = new Pagination();
        List<?> lis =null;
        Query q =createHqlQuery(hql, args);
        q.setCacheable(cacheable);
        q.setFirstResult((pageindex-1)*limit);
        q.setMaxResults(limit);
        lis = q.list();

        page.setData(lis);
        page.setLimit(limit);
        page.setPageindex(pageindex);
        String total_hql=getRowCountHql(hql);
        Integer total=Integer.valueOf(uniqueQueryByHQL(total_hql, cacheable, args).toString());
        page.setTotal(total);
        page.setMaxPage((total-1)/limit+1);
        return page;
    }
	/**
	 * 
	 * @param hql
	 * @param pageindex
	 * @param limit
	 * @param cacheable
	 * @param args
	 * @return
	 */
	public Pagination PaginationsByHQLArrayParams(String hql,int pageindex,int limit,boolean cacheable,Object ...args ) {
	    Pagination page = new Pagination();
        List<?> lis =null;
        Query q =createHqlQueryByArray(hql, args);
        q.setCacheable(cacheable);
        q.setFirstResult((pageindex-1)*limit);
        q.setMaxResults(limit);
        lis = q.list();

        page.setData(lis);
        page.setLimit(limit);
        page.setPageindex(pageindex);
        String total_hql=getRowCountHql(hql);
        Integer total=Integer.valueOf(uniqueQueryByHql(total_hql, cacheable, args).toString());
        page.setTotal(total);
        page.setMaxPage((total-1)/limit+1);
        return page;
    }
	
	public Pagination PaginationsBySQL(String sql,int pageindex,int limit,boolean cacheable,Object... args) {
	    Pagination page = new Pagination();
        List<?> lis =null;
        Query q = createSqlQuery(sql, args);
        q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        q.setCacheable(cacheable);
        q.setFirstResult((pageindex-1)*limit);
        q.setMaxResults(limit);
        lis = q.list();

        page.setData(lis);
        page.setLimit(limit);
        page.setPageindex(pageindex);
        String total_hql=String.format("select count(1) from (%s) as xxxx", sql);
        Integer total=Integer.valueOf(uniqueQueryBySql(total_hql, cacheable, args).toString());
        page.setTotal(total);
        page.setMaxPage((total-1)/limit+1);
        return page;
    }
	/**
	 * sql分布
	 * @param sql
	 * @param pageindex
	 * @param limit
	 * @param cacheable
	 * @param args
	 * @return
	 */
	public Pagination PaginationsBySQL(String sql,int pageindex,int limit,boolean cacheable,Map<String, Object> args) {
	    Pagination page = new Pagination();
        List<?> lis =null;
        Query q = createSqlQuery(sql, args);
        q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        q.setCacheable(cacheable);
        q.setFirstResult((pageindex-1)*limit);
        q.setMaxResults(limit);
        lis = q.list();

        page.setData(lis);
        page.setLimit(limit);
        page.setPageindex(pageindex);
        String total_hql=String.format("select count(1) from (%s) as xxxx", sql);
        Integer total=Integer.valueOf(uniqueQueryBySql(total_hql, cacheable, args).toString());
        page.setTotal(total);
        page.setMaxPage((total-1)/limit+1);
        return page;
    }
	
	
	public static final String ROW_COUNT = "select count(1) ";
	public static final String FROM = "from";
	public static final String HQL_FETCH = "fetch";
	public static final String ORDER_BY = "order";

	private  String wrapProjection(String projection) {
        if (projection.indexOf("select") == -1) {
            return ROW_COUNT;
        } else {
           // return projection.replace("select", "select count(") + ") ";
            return " select COUNT(*) ";
        }
    }

    public  String getRowCountHql(String hql) {
            int fromIndex = hql.toLowerCase().indexOf(FROM);
            String projectionHql = hql.substring(0, fromIndex);

            hql = hql.substring(fromIndex);
            String rowCountHql = hql.replace(HQL_FETCH, "");

            int index = rowCountHql.indexOf(ORDER_BY);
            if (index > 0) {
                rowCountHql = rowCountHql.substring(0, index);
            }
            return wrapProjection(projectionHql) + rowCountHql;
        }

    

	
	/**
	 * 修改一个实体
	 * @param obj
	 */
	public void update(Object obj) {
		getSession().update(obj);
	}
	/**
	 * 删除一个实体
	 * @param obj
	 */
	public void delete(Object obj) {
		getSession().delete(obj);
	}

    /**
     * 删除多个实体
     * @param lis
     */
	public void deleteList(List<?> lis) {
		Session session = getSession();
		for(int a=0;a<lis.size();a++){
			 session.delete(lis.get(a));
			 if (a % BATCH_SIZE == 0) {
               session.flush();
               session.clear();
           }
		}
	}

	/**
	 * 根据主键返回一个实体
	 * @param clazz
	 * @param id
	 * @return
	 */
	public Object get(Class<?> clazz,Object id) {
		return getSession().get(clazz, (Serializable) id);
	}

    /**
     * 执行HQL
     * @param hql
     * @param args
     * @return
     */
	public int executeHQL(String hql,Object...args) {
		Query q =createHqlQuery(hql, args);
		return q.executeUpdate();
	}
	/**
	 * 执行HQL
	 * @param hql
	 * @param map
	 * @return
	 */
    public int executeHQL(String hql,Map<String, Object> map) {
        Query q = createHqlQuery(hql, map);
        return q.executeUpdate();
    }

    /**
     * 执行SQL
     * @param sql
     * @param args
     * @return
     */
	public int executeSQL(String sql,Object...args) {
		Query q = createSqlQuery(sql, args);
		return q.executeUpdate();
	}

	/**
     * 根据sql查询返回一个map
     * @param sql
     * @param map
     * @param keyname
     * @param valuename
     * @return
     */
    public Map<String, ?> queryForSingleMap(String sql,Map<String, Object> map,String keyname,String valuename){
        Query q = createSqlQuery(sql, map);
        q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> lis = q.list();
        Map<String, Object> fmap = new HashMap<String, Object>();
        if (null!=lis&&lis.size()>0) {
            for (Map<String, Object> map2 : lis) {
                fmap.put(map2.get(keyname).toString(), map2.get(valuename));
            }
        }
        return fmap;
    }
    /**
     * 创建hql 查询对象
     * @param hql
     * @param map
     * @return
     */
    public Query createHqlQuery(String hql,Map<String, Object> map){
    	Query q = getSession().createQuery(hql);
        if (null!=map) {
            Iterator<String> itera = map.keySet().iterator();
            while (itera.hasNext()) {
                String key =itera.next();
                Object value =map.get(key);
                if (value.getClass().isArray()) {
                    Object[] array=(Object[]) value;
                    q.setParameterList(key,  array);
                }
                else{
                    q.setParameter(key, value);
                }
            }
        }
        return q;
    }
    /**
     * 
     * @param hql
     * @param args
     * @return
     */
    public Query createHqlQueryByArray(String hql,Object ...args){
    	Query q = getSession().createQuery(hql);
		if (null!=args&&args.length>0) {
			for (int i = 0; i < args.length; i++) {
				q.setParameter(i, args[i]);
			}
		}
        return q;
    }
    /**
     * 创建sql查询对象
     * @param sql
     * @param map
     * @return
     */
    public Query createSqlQuery(String sql,Map<String, Object> map){
    	Query q = getSession().createSQLQuery(sql);
        if (null!=map) {
            Iterator<String> itera = map.keySet().iterator();
            while (itera.hasNext()) {
                String key =itera.next();
                Object value =map.get(key);
                if (value.getClass().isArray()) {
                    Object[] array=(Object[]) value;
                    q.setParameterList(key,  array);
                }
                else{
                    q.setParameter(key, value);
                }
            }
        }
        return q;
    }
    /**
     * 创建sql query
     * @param sql
     * @param args
     * @return
     */
    public Query createSqlQuery(String sql,Object ...args){
    	Query q = getSession().createSQLQuery(sql);
		if (null!=args&&args.length>0) {
			for (int i = 0; i < args.length; i++) {
				q.setParameter(i, args[i]);
			}
		}
        return q;
    }

    /**
     * 创建HQL query
     * @param hql
     * @param args
     * @return
     */
    public Query createHqlQuery(String hql,Object ...args){
    	Query q = getSession().createQuery(hql);
		if (null!=args&&args.length>0) {
			for (int i = 0; i < args.length; i++) {
				q.setParameter(i, args[i]);
			}
		}
        return q;
    }
}
