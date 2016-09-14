
package com.mabasasi.reflectionpane.matcher;

import com.mabasasi.reflectionpane.field.FormInterface;
import com.mabasasi.reflectionpane.field.IntegerForm;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 *
 * @author mabasasi
 */
public class IntegerMatcher implements FieldMatcher {
    
    @Override
    public boolean matchForm(Type type, boolean isArray, Type arrayType, boolean isGenerics, Type[] actualTypes, Field field) {
        return (type == int.class) && (isArray == false);
    }

    @Override
    public FormInterface createForm(Object object, Field field) throws IllegalArgumentException, IllegalAccessException {
        return new IntegerForm(object, field);
    }
    
}
