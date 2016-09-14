
package com.mabasasi.reflectionpane.matcher;

import com.mabasasi.reflectionpane.field.FormInterface;
import com.mabasasi.reflectionpane.field.StringForm;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * java.lang.String の振り分けクラス.
 * @author mabasasi
 */
public class StringMatcher implements FieldMatcher {
    
    @Override
    public boolean matchForm(Type type, boolean isArray, Type arrayType, boolean isGenerics, Type[] actualTypes, Field field) {
        return (type == String.class) && (isArray == false);
    }

    @Override
    public FormInterface createForm(Object object, Field field) throws IllegalArgumentException, IllegalAccessException {
        return new StringForm(object, field);
    }
    
}
