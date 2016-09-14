
package com.mabasasi.reflectionpane.field;

import java.awt.Component;
import java.lang.reflect.Field;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * java.lang.Float クラスのフォーム.
 * @author mabasasi
 */
public class FloatForm extends FieldForm {

    private final JSpinner              spinner;
    private final SpinnerNumberModel    model;
    private final JSpinner.NumberEditor editor;
    
    public FloatForm(Object object, Field field) throws IllegalArgumentException, IllegalAccessException {
        super(object, field);
        this.model = new SpinnerNumberModel(0.0f, null, null, 0.1);
        this.spinner = new JSpinner(model);
        this.editor = new JSpinner.NumberEditor(spinner, "#,##0.0");
        this.spinner.setEditor(editor);
    }

    @Override
    public Component getComponent() {
        return this.spinner;
    }

    @Override
    public void setDefaultValue() {
        //model.setValue(getDefauleValue());
    }

    @Override
    public Object getValue() {
        return model.getNumber().floatValue();
    }
    
}
