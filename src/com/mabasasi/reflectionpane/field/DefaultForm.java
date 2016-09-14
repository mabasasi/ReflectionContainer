
package com.mabasasi.reflectionpane.field;

import java.awt.Color;
import java.awt.Component;
import java.lang.reflect.Field;
import javax.swing.JLabel;

/**
 * 設定されていないクラスのフォーム.
 * @author mabasasi
 */
public class DefaultForm extends FieldForm {

    /**入力フォーム*/
    private JLabel form;

    public DefaultForm(Object object, Field field) throws IllegalArgumentException, IllegalAccessException {
        super(object, field);
        this.form = new JLabel();
        this.form.setOpaque(true);
        this.form.setBackground(Color.ORANGE);
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
        // 編集できないので、元の値をそのまま返す
        return getDefauleValue();
    }
}
