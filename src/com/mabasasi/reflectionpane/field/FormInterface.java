
package com.mabasasi.reflectionpane.field;

import java.awt.Component;

/**
 * フォーム作成に必要なメソット定義インターフェース.
 * こいつを継承して新規に対応するオブジェクトを作成していく.
 * これに、必要そうなメソッドを追加したのがFieldFormである.
 * @author mabasasi
 */
public interface FormInterface {
    
    /**
     * フォーム入力用コンポーネントの取得.
     * @return 入力用コンポーネント
     */
    public Component getComponent();
    
    /**
     * フォームのラベル用コンポーネントの取得.
     * @return ラベル用コンポーネント
     */
    public Component getLabel();
    
    /**
     * コンポーネントに初期値を設定する.
     * コンストラクタや初期化ボタンなどで実行する感じ.
     */
    public void setDefaultValue();
    
    /**
     * コンポーネントの現在の値を取得する.
     * 返すオブジェクトは、formに設定したオブジェクトと同一のものとする
     * @return 現在の値
     */
    public Object getValue();
    
    /**
     * 設定されている変数の値を更新する.
     * @param object 書き換えるオブジェクト
     */
    public void updateValue(Object object);
    
    /**
     * コンポーネントの現在の値を、変数に記録する.
     * @param object 書き込む変数 !!注意!!
     * @throws java.lang.IllegalAccessException フィールドにアクセスできなかった
     */
    public void applyValue(Object object) throws IllegalAccessException;
    
}
