package weibo4j.util;

import java.util.Iterator;
import java.util.Map;

import weibo4j.model.PostParameter;

public class ArrayUtils {

	public static PostParameter[] mapToArray(Map<String, String> map) {
		PostParameter[] parList = new PostParameter[map.size()];
		Iterator<String> iter = map.keySet().iterator();
		int i = 0;
		while (iter.hasNext()) {
			String key = iter.next();
			String value = map.get(key);
			parList[i++] = new PostParameter(key, value);
		}
		return parList;
	}
	
}
