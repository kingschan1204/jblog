package com.kingschan.blog.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Repository;

import com.kingschan.blog.dao.HibernateBaseDao;
import com.kingschan.blog.dao.UserDao;
import com.kingschan.blog.po.User;
import com.kingschan.blog.util.SqlUtil;
@Repository("UserDaoImpl")
@SuppressWarnings("unchecked")
public class UserDaoImpl extends HibernateBaseDao implements UserDao {

    @Override
    public User getUser(String keyword) throws Exception {
        if (keyword.matches("\\w{32}")) {
            return (User) get(User.class,keyword);
        }else{
            String hql="from User u where u.userName=? ";
			List<User> lis= (List<User>) queryForListByHql(hql, false,false,keyword);
            return (null==lis||lis.size()==0)?null:lis.get(0);
        }
    }

    @Override
    public void saveUser(User user) throws Exception {
        if (user.getId().matches("\\w{32}")) {
            update(user);
        }else{
            save(user);
        }
    }

	@Override
	public int modifyPsw(String password, String userid) throws Exception {
		String hql="update User u set u.userPsw=? where u.id=?";
		return executeHQL(hql, password,userid);
	}

	
	@Override
	public List<User> getUsersByEmail(String email) throws Exception {
		String hql=" from User u where u.userEmail=?";
		return (List<User>) queryForListByHql(hql, false,false, email);
	}

	@Override
	public User existsSinaUser(long uid) throws Exception {
		String hql=" from User where sinaUid=?";
		List<User> lis= (List<User>) queryForListByHql(hql, false, false, uid);
		return null!=lis&&lis.size()>0?lis.get(0):null;
	}

	@Override
	public List<User> getUsersByUserNames(String[] userNamses) throws Exception {
		String hql =" from User where userName in (:usernames)";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("usernames",userNamses);
		return (List<User>)queryForListByHqlMapStyle(hql,true,false,map);
	}

	@Override
	public Map<String, Object> getUserInfoCard(String username)
			throws Exception {
		String sql=SqlUtil.getSql("report","user_info_card");
		List<Map<String, Object>> rows= (List<Map<String, Object>>) queryForListMapBySql(sql, false, username);
		if (null!=rows&&rows.size()>0) {
			return rows.get(0);
		}
		return null;
	}

}
