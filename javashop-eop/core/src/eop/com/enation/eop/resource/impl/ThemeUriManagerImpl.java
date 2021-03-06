package com.enation.eop.resource.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.enation.eop.resource.IThemeUriManager;
import com.enation.eop.resource.model.ThemeUri;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.util.StringUtil;

/**
 *  theme uri 管理器
 * @author kingapex
 * @version v2.0
 * 2016年2月17日下午9:37:17
 * @since v6.0
 */
@Service("themeUriManagerImpl")
public class ThemeUriManagerImpl   implements IThemeUriManager {

	@Autowired
	private IDaoSupport<ThemeUri> daoSupport;
	
	/*
	 * (non-Javadoc)
	 * @see com.enation.eop.resource.IThemeUriManager#clean()
	 */
	@Override
	public void clean() {
		daoSupport.execute("truncate table es_themeuri");
	}

	/*
	 * (non-Javadoc)
	 * @see com.enation.eop.resource.IThemeUriManager#get(java.lang.Integer)
	 */
	@Override
	public ThemeUri get(Integer id) {
		return daoSupport.queryForObject("select * from es_themeuri where id=?", ThemeUri.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.enation.eop.resource.IThemeUriManager#edit(java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void edit(List<ThemeUri> uriList) {
		for (ThemeUri uri : uriList) {
			daoSupport.update("es_themeuri", uri, "id=" + uri.getId());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.enation.eop.resource.IThemeUriManager#list(java.util.Map)
	 */
	@Override
	public List<ThemeUri> list(Map map) {
		String sql = "select * from es_themeuri";
		if(map!=null){
			String keyword = (String) map.get("keyword");
			if(!StringUtil.isEmpty(keyword)){
				sql+=" where uri like '%"+keyword+"%'";
				sql+=" or path like '%"+keyword+"%'";
				sql+=" or pagename like '%"+keyword+"%'";
			}
		}
		return daoSupport.queryForList(sql, ThemeUri.class);
	}

	/*
	 * (non-Javadoc)
	 * @see com.enation.eop.resource.IThemeUriManager#getPath(java.lang.String)
	 */
	@Override
	public ThemeUri getPath(String uri) {
		List<ThemeUri> list = list(null);

		for (ThemeUri themeUri : list) {
			if (themeUri.getUri().equals(uri)) {
				return themeUri;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.enation.eop.resource.IThemeUriManager#add(com.enation.eop.resource.model.ThemeUri)
	 */
	@Override
	public void add(ThemeUri uri) {
		daoSupport.insert("es_themeuri", uri);
	}

	/*
	 * (non-Javadoc)
	 * @see com.enation.eop.resource.IThemeUriManager#delete(int)
	 */
	@Override
	public void delete(int id) {
		daoSupport.execute("delete from es_themeuri where id=? ", id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.enation.eop.resource.IThemeUriManager#edit(com.enation.eop.resource.model.ThemeUri)
	 */
	@Override
	public void edit(ThemeUri themeUri) {
		daoSupport.update("es_themeuri", themeUri,
				"id=" + themeUri.getId());
	}

}
