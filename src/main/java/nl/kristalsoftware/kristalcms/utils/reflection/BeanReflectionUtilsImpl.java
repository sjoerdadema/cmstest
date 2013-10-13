package nl.kristalsoftware.kristalcms.utils.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.pmw.tinylog.Logger;

public class BeanReflectionUtilsImpl implements BeanReflectionUtils {

	public BeanReflectionUtilsImpl() {}
	
	public List<Field> getNonStaticFields(Class<?> c) {
		Field[] fieldArr = c.getDeclaredFields();
		List<Field> list = new ArrayList<Field>();
		for (Field f : fieldArr) {
			if (!Modifier.isStatic(f.getModifiers())) {
				list.add(f);
				Logger.debug(f.toString());
			}
		}
		return list;
	}

	public Map<String, Field> createFieldsMap(List<Field> fieldList) {
		Map<String,Field> fieldMap = new HashMap<String,Field>();
		Iterator<Field> iter = fieldList.iterator();
		while (iter.hasNext()) {
			Field f = iter.next();
			fieldMap.put(f.getName(), f);
			Logger.debug(f.toString());
		}
		return fieldMap;
	}

	@SuppressWarnings("unchecked")
	public <V,T> V getFieldValueWithGetter(Field field, T bean) {
		V var = null;
		Class<?> c = bean.getClass();
		String fieldName = field.getName();
		Logger.debug(fieldName);
		Method method;
		try {
			method = c.getMethod("get" + StringUtils.capitalize(fieldName));
			Logger.debug(method.toString());
			var = (V) method.invoke(bean);
		} catch (Exception e) {
			Logger.error(e.getMessage());
		}
		return var;
	}
	
	public <T> void setFieldValueWithSetter(Field field, String val, T bean) {
		Class<?> c = bean.getClass();
		String fieldName = field.getName();
		Logger.debug(fieldName);
		Method method;
		try {
			//Class<?> p = Class.forName("java.lang.String");
			Class<?>[] ptypes = new Class<?>[1];
			ptypes[0] = val.getClass();
			method = c.getMethod("set" + StringUtils.capitalize(fieldName), ptypes);
			Logger.debug(method.toString());
			method.invoke(bean, val);
		} catch (Exception e) {
			e.printStackTrace();
			Logger.error(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public <V,T> V getFieldValue(Field self, T bean) {
		Class<?> c = bean.getClass();
		int mod = c.getModifiers();
		if (!Modifier.isPublic(mod)) {
			self.setAccessible(true);
		}
		V var = null;
		try {
			var = (V) self.get(bean);
		} catch (Exception e) {
			Logger.error(e.toString());
		}
		return var;
	}

	public <T,A extends Annotation> List<Field> getAnnotatedFields(T bean, Class<A> annotationClass) {
		List<Field> annotatedFields = new ArrayList<Field>();
		Class<?> c = bean.getClass();
		Field[] fieldArr = c.getDeclaredFields();
		for (Field f : fieldArr) {
			if (f.isAnnotationPresent(annotationClass)) {
				annotatedFields.add(f);
			}
		}
		return annotatedFields;
	}

}
