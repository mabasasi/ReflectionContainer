
package com.mabasasi.reflectionpane.field;

import java.awt.Component;
import java.lang.reflect.Field;
import javax.swing.JTextField;

/**
 * java.lang.String クラスのフォーム.
 * @author mabasasi
 */
public class StringForm extends FieldForm {
    
    private final JTextField form;
    
    public StringForm(Object object, Field field) throws IllegalArgumentException, IllegalAccessException {
        super(object, field);
        this.form = new JTextField();
    }

    @Override
    public Component getComponent() {
        return this.form;
    }

    @Override
    public void setDefaultValue() {
        form.setText(this.getDefauleValue().toString());
    }

    @Override
    public Object getValue() {
        return form.getText();
    }
    
}
