
package com.mabasasi.reflectionpane.matcher;

import com.mabasasi.reflectionpane.field.FloatForm;
import com.mabasasi.reflectionpane.field.FormInterface;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * java.lang.Float の振り分けクラス.
 * @author mabasasi
 */
public class FloatMatcher implements FieldMatcher {
    
    @Override
    public boolean matchForm(Type type, boolean isArray, Type arrayType, boolean isGenerics, Type[] actualTypes, Field field) {
        return (type == float.class) && (isArray == false);
    }

    @Override
    public FormInterface createForm(Object object, Field field) throws IllegalArgumentException, IllegalAccessException {
        return new FloatForm(object, field);
    }
    
}
