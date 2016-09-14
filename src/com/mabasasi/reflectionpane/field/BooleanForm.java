
package com.mabasasi.reflectionpane.field;

import java.awt.Component;
import java.lang.reflect.Field;
import javax.swing.JCheckBox;

/**
 * java.lang.Boolean クラスのフォーム.
 * @author mabasasi
 */
public class BooleanForm extends FieldForm {

    private final JCheckBox check;
    
    public BooleanForm(Object object, Field field) throws IllegalArgumentException, IllegalAccessException {
        super(object, field);
        this.check = new JCheckBox();
    }

    @Override
    public Component getComponent() {
        return this.check;
    }

    @Override
    public void setDefaultValue() {
        check.setSelected((boolean)getValue());
    }

    @Override
    public Object getValue() {
        return check.isSelected();
    }
    
}
