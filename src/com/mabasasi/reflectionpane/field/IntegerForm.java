
package com.mabasasi.reflectionpane.field;

import java.awt.Component;
import java.lang.reflect.Field;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * java.lang.Integer クラスのフォーム.
 * @author mabasasi
 */
public class IntegerForm extends FieldForm {

    private final JSpinner              spinner;
    private final SpinnerNumberModel    model;
    private final JSpinner.NumberEditor editor;
    
    public IntegerForm(Object object, Field field) throws IllegalArgumentException, IllegalAccessException {
        super(object, field);
        this.model = new SpinnerNumberModel(0, null, null, 1);
        this.spinner = new JSpinner(model);
        this.editor = new JSpinner.NumberEditor(spinner, "#,##0");
        this.spinner.setEditor(editor);
    }

    @Override
    public Component getComponent() {
        return this.spinner;
    }

    @Override
    public void setDefaultValue() {
        model.setValue(getDefauleValue());
    }

    @Override
    public Object getValue() {
        return model.getNumber().intValue();
    }
    
}
