
package reflectionpane;

import com.mabasasi.reflectionpane.field.DefaultForm;
import com.mabasasi.reflectionpane.field.FormInterface;
import com.mabasasi.reflectionpane.matcher.*;
import java.awt.Container;
import java.lang.reflect.*;
import java.util.*;

/**
 * リフレクションを使用した変数内情報表示コンテナ.
 * <br>
 * 大きく分けてFormとMatcherに分けられる.
 * Formは、変数に対応したコンポーネントの管理クラス.
 * Matcherは、Fieldに対してFormを割り振るクラス.
 * 新たなクラスに対応させる場合は、ReflectionContainer.addFieldMatcher()を呼び出す.
 * これはstaticメソットなので、プロジェクト共有となる.
 * <br>
 * また標準では色が付く.
 * 背景緑はprotect要素、背景赤はprivate要素、文字色青はfinal要素である.
 * 背景橙は定義されていないクラスなので、toStringの結果を表示している.
 * @author mabasasi
 */
public final class ReflectionContainer extends Container {
    
    private static final long serialVersionUID = 5855494627086763537L;
    
    /**マッチャーリスト*/
    private static final LinkedList<FieldMatcher> MATCHERS;
    static {
        MATCHERS = new LinkedList<>();
        MATCHERS.add(new StringMatcher());
        MATCHERS.add(new IntegerMatcher());
        MATCHERS.add(new FloatMatcher());
        MATCHERS.add(new DoubleMatcher());
        MATCHERS.add(new BooleanMatcher());
        MATCHERS.add(new StringMatcher());
    }
    
    /**
     * フィールドに対応するフォームを追加する.
     * 判定の先頭に追加するため、既存処理の上書きが可能.
     * @param matcher フィールドマッチャー
     */
    public static void addFieldMatcher(FieldMatcher matcher) {
        MATCHERS.addFirst(matcher);
    }
    
    
    

    /**参照元オブジェクト*/
    private final Object object;
    /**フォーム一覧*/
    private final List<FormInterface> forms;

    /**
     * インスタンスの作成.
     * @param object 対象の変数
     * @throws IllegalArgumentException 変数が異なる等
     * @throws IllegalAccessException フィールドにアクセスできなかった
     */
    public ReflectionContainer(Object object) throws IllegalArgumentException, IllegalAccessException {
        this(object, false);
    }
    
    /**
     * インスタンスの作成.
     * @param object 対象の変数
     * @param canAccessible !!!注意!!!private, final メソットにアクセスする
     * @throws IllegalArgumentException 変数が異なる等
     * @throws IllegalAccessException フィールドにアクセスできなかった
     */
    public ReflectionContainer(Object object, boolean canAccessible) throws IllegalArgumentException, IllegalAccessException {
        this.forms  = new ArrayList<>();
        this.object = object;
                
        // 全てのフィールドを取得していく
        Field[] fields = getReflectionFields(object, canAccessible);
        for (Field field : fields) {
            FormInterface form = chooseForm(object, field);
            forms.add(form);
        }
        
        // コンポーネントに初期値を配布
        setDefaultValue();
        
        // 自コンテナに貼り付け
        attachComponent();
    }

    /**
     * フォームに初期値を設定.
     */
    public void setDefaultValue() {
        forms.stream().forEach(e -> e.setDefaultValue());
    }
    
    /**
     * フォームの値を初めに設定した変数に格納.
     * @throws java.lang.IllegalAccessException 変数にアクセスできなかった
     */
    public void applyValue() throws IllegalAccessException {
        this.applyValue(this.object);
    }
    
    /**
     * フォームの値を変数に格納.
     * 別の変数に格納すると、元に戻ることが出来そう.
     * @param object 格納する変数 初めに設定した変数と同じ型ならOK
     * @throws java.lang.IllegalAccessException 変数にアクセスできなかった
     */
    public void applyValue(Object object) throws IllegalAccessException {
        if (!this.object.getClass().equals(object.getClass())) {
            throw new IllegalArgumentException("Object type is defferent.");
        }
        
        for (FormInterface form : forms) {
            form.applyValue(object);
        }
    }
    
    /**
     * 設定されている変数の更新.
     */
    public void updateValue() {
        this.updateValue(this.object);
    }
    
    /**
     * 設定されている変数の更新.
     * 新たな変数を設定する際に使用.
     * @param object 更新する変数 初めに設定した変数と同じ型ならOK
     */
    public void updateValue(Object object) {
        if (!this.object.getClass().equals(object.getClass())) {
            throw new IllegalArgumentException("Object type is defferent.");
        }
        
        for (FormInterface form : forms) {
            form.updateValue(object);
        }
    }
    
    
    
    /**
     * フォーム一覧を取得する.
     * このメソットを使うと、このプロジェクトの意味が...
     * 特定のフォームのみ変更したい場合にこれを使用する.
     * フォームの識別は、初期フォームなら[FieldForm]にキャストして、getField()で行う.
     * @return フォーム一覧
     */
    public FormInterface[] getFormInterfaces() {
        return forms.toArray(new FormInterface[forms.size()]);
    }
    
    
    
    
    
    /**R/W可能なフィールド一覧を取得する*/
    private Field[] getReflectionFields(Object object, boolean canAccessible) {
        if (!canAccessible) {
            return object.getClass().getFields();
        } else {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(canAccessible);
            }
            return fields;
        }
    }
    
    /**フォームの選択*/
    private FormInterface chooseForm(Object object, Field field) throws IllegalArgumentException, IllegalAccessException {
        Type    type        = field.getGenericType();
        boolean isArray     = object.getClass().isArray();
        Type    arrayType   = object.getClass().getComponentType();
        boolean isGenerics  = type instanceof ParameterizedType;
        Type[]  actualTypes = (type != null && isGenerics) ? ((ParameterizedType) type).getActualTypeArguments() : null;
        
        // 対応するやつを探す
        for (FieldMatcher matcher : MATCHERS) {
            if (matcher.matchForm(type, isArray, arrayType, isGenerics, actualTypes, field)) {
//                System.out.printf("[%s] type=%s, isArray=%s, arrayType=%s, isGenerics=%s, actualTypes=%s%n",
//                        field, type, isArray, arrayType, isGenerics, Arrays.toString(actualTypes));
                return matcher.createForm(object, field);
            }
        }
        
        // 無いなら初期のやつ
//        System.out.printf("[%s] type=%s, isArray=%s, arrayType=%s, isGenerics=%s, actualTypes=%s%n",
//                field, type, isArray, arrayType, isGenerics, actualTypes);
        return new DefaultForm(object, field);
    }
    
    /**コンテナにコンポーネントの貼り付け*/
    private void attachComponent() {
        this.removeAll();   // 一応...
        
        GroupLayoutManager man = new GroupLayoutManager(this);
        man.setAutoCreateGaps(true);
        
        int n = 0;
        for (FormInterface form : forms) {
            man.setComponent(0, n, form.getLabel());
            man.setComponent(1, n, form.getComponent());
            n ++;
        }
        
        man.attach();
    }
}
