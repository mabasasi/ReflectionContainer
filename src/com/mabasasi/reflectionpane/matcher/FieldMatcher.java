
package com.mabasasi.reflectionpane.matcher;

import com.mabasasi.reflectionpane.field.FormInterface;
import com.mabasasi.reflectionpane.field.FormInterface;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Fieldに対してフォームを作成する振り分けクラス.
 * @author mabasasi
 */
public interface FieldMatcher {
    
    /**
     * フィールドがフォームと対応しているか判定.
     * @param type 変数の型
     * @param isArray 配列かどうか
     * @param arrayType isArray=trueで配列の型
     * @param isGenerics 総称型を使用しているか
     * @param actualTypes isGenerics=trueで総称型の型
     * @param field その他情報がほしいなら自分で実装して
     * @return trueでこのクラスのcreateFormに対応している
     */
    public boolean matchForm(Type type, boolean isArray, Type arrayType, boolean isGenerics, Type[] actualTypes, Field field);
    
    /**
     * フィールドに対応したフォームを作成する.
     * この中は基本的にインスタンス化のみ行う.
     * @param object アタッチするオブジェクト
     * @param field オブジェクトに対応するフィールド
     * @return フォーム
     * @throws IllegalArgumentException その他例外
     * @throws java.lang.IllegalAccessException フィールドにアクセスできなかった
     */
    public FormInterface createForm(Object object, Field field) throws IllegalArgumentException, IllegalAccessException;
    
}
