
package reflectionpane;

import com.mabasasi.reflectionpane.field.*;
import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

/**
 *
 * @author morishige
 */
public class GroupLayoutManager {
    private final Container   host;
    private final GroupLayout group;
    
    private Map<Point, Component> data;
    
    
    /**
     * 通常のコンストラクタ.
     * 内部で GroupLayoutの作成と、hostへのアタッチを行う.
     * @param host ベースとなるコンテナ.
     */
    public GroupLayoutManager(Container host) {
        this.host = host;
        
        group = new GroupLayout(host);
        host.setLayout(group);
        
        data = new LinkedHashMap();
    }
    
    /**
     * レイアウトにコンポーネントを追加する.
     * @param x X座標
     * @param y Y座標
     * @param c 追加するコンポーネント
     */
    public void setComponent(int x, int y, Component c) {
        data.put(new Point(x, y), c);
    }
    
    public void setAutoCreateGaps(boolean val) {
        group.setAutoCreateGaps(val);
        group.setAutoCreateContainerGaps(val);
    }
    
    
    public void attach() {
        horizon();
        vertical();
    }
    
    protected void vertical() {
        //System.out.println("("+getWidthNum()+", "+getHeightNum()+")");
        // 水平方向のグループ
        SequentialGroup hg = group.createSequentialGroup();
        
        for (int i=0; i<getHeightNum(); i++) {
            ParallelGroup pg = group.createParallelGroup(GroupLayout.Alignment.BASELINE);
            for (Component c : getRowComponents(i)) {
                if (c == null) continue;
                pg = pg.addComponent(c);
                host.add(c);
            }
            hg = hg.addGroup(pg);
        }
        
        group.setVerticalGroup(hg);
    }
    
    protected void horizon() {
        //System.out.println("("+getWidthNum()+", "+getHeightNum()+")");
        // 垂直方向のグループ
        SequentialGroup vg = group.createSequentialGroup();
        
        for (int i=0; i<getWidthNum(); i++) {
            ParallelGroup pg = group.createParallelGroup();
            for (Component c : getColumnComponents(i)) {
                if (c == null) continue;
                pg = pg.addComponent(c);
                host.add(c);
            }
            vg = vg.addGroup(pg);
        }
        
        group.setHorizontalGroup(vg);
    }
    
    
    
    
    
    
    
    
    
    
    /**
     * 指定行のコンポーネントを取得する.
     * @param n 行番号
     * @return コンポーネント配列 nullで存在しない
     */
    private Component[] getRowComponents(int n) {
        int rowNum = getWidthNum();
        if (rowNum > 0) {
            Component coms[] = new Component[rowNum];
            for (Point p : data.keySet()) {
                if (p.y == n) {
                    coms[p.x] = data.get(p);
                }
            }
            
            return coms;
        }
        
        return null;
    }
    
    /**
     * 指定列のコンポーネントを取得する.
     * @param m 列番号
     * @return コンポーネント配列 nullで存在しない
     */
    private Component[] getColumnComponents(int m) {
        int colNum = getHeightNum();
        if (colNum > 0) {
            Component coms[] = new Component[colNum];
            for (Point p : data.keySet()) {
                if (p.x == m) {
                    coms[p.y] = data.get(p);
                }
            }
            
            return coms;
        }
        
        return null;
    }
    
    
    
    
    
    
    public int getWidthNum() {
        int w = 0;
        
        for (Point p : data.keySet()) {
            if ((p.x+1) > w) w = (p.x+1);
        }
        
        return w;
    }
    
    public int getHeightNum() {
        int h = 0;
        
        for (Point p : data.keySet()) {
            if ((p.y+1) > h) h = (p.y+1);
        }
        
        return h;
    }
    
    
}
