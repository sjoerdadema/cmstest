package nl.kristalsoftware.kristalcms.utils.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public interface BeanReflectionUtils {

	List<Field> getNonStaticFields(Class<?> c);

	Map<String, Field> createFieldsMap(List<Field> fieldList);

	<T,A extends Annotation> List<Field> getAnnotatedFields(T bean, Class<A> annotationClass);

	<V,T> V getFieldValue(Field self, T bean);
	
	<V,T> V getFieldValueWithGetter(Field field, T bean);
	
	<T> void setFieldValueWithSetter(Field field, String val, T bean);

}
