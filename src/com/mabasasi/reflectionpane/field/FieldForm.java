
package com.mabasasi.reflectionpane.field;

import java.awt.Color;
import java.awt.Component;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import javax.swing.JLabel;

/**
 * フォーム作成用ベースクラス.
 * こいつを継承して新規に対応するオブジェクトを作成していく.
 * FormInterfaceを継承していれば良いので、必ずこれを使う必要はない.
 * @author mabasasi
 */
public abstract class FieldForm implements FormInterface {

    /**保持フィールド*/
    private Field     field;
    /**フォームのラベル(null)*/
    private Component label;
    /**ラベルに指定する名前*/
    private String    name;
    /**オブジェクトの初期値*/
    private Object    defaultValue;
    
    /**
     * フォーム管理のインスタンス作成.
     * @param object アタッチするオブジェクト
     * @param field オブジェクトに対応するフィールド
     * @throws java.lang.IllegalAccessException フィールドにアクセスできなかった
     */
    public FieldForm(Object object, Field field) throws IllegalArgumentException, IllegalAccessException {
        this.field = field;
        this.name  = field.getName();
        this.defaultValue = field.get(object);
    }
    
    /**
     * フォームのタイトルを指定する.
     * 初期値は変数名.
     * @param name フォームタイトル nullで初期値
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * フォームのラベルを指定する.
     * このメソットを使用すると、setName()は無効になる.
     * @param label 設定するラベル(ラベル以外もええで) nullで初期値
     */
    public void setLabel(Component label) {
        this.label = label;
    }
    
    /**
     * フィールドの初期値を取得する.
     * @return 初期値
     */
    public Object getDefauleValue() {
        return this.defaultValue;
    }
    
    /**
     * フィールドを取得する.
     * @return フィールド
     */
    public Field getField() {
        return this.field;
    }
    
    /**
     * フォームの名前を取得する.
     * @return フォームの名前
     */
    public String getName() {
        return this.name;
    }
    
    @Override
    public Component getLabel() {
        // ラベルがなければ新規に作成する
        if (label == null) {
            JLabel label = new JLabel(name);
            label.setOpaque(true);
            if (Modifier.isFinal    (field.getModifiers())) label.setForeground(Color.BLUE);
            if (Modifier.isPrivate  (field.getModifiers())) label.setBackground(Color.PINK);
            if (Modifier.isProtected(field.getModifiers())) label.setBackground(Color.GREEN);
            this.label = label;
        }
        
        return label;
    }

    @Override
    public void applyValue(Object object) throws IllegalAccessException {
        Object value = this.getValue();
        field.set(object, value);
    }

    @Override
    public void updateValue(Object object) {
        this.defaultValue = object;
        this.setDefaultValue();
    }
    
}
