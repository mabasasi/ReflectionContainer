
package reflectionpane;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * リフレクションペインのユーティリティクラス.
 * @author mabasasi
 */
public class ReflectionPane {
    
    public static class DialogBuilder {
        
        private final ReflectionContainer reflect;
        
        private String title;
        private Component parent;
        private Point  pos;
        private Dimension size;
        private boolean isResizable;
        private boolean isModal;
        private int close = -1;
        
        private DialogBuilder(Object object, boolean canAccessible) throws IllegalArgumentException, IllegalAccessException {
            this.reflect = new ReflectionContainer(object, canAccessible);
        }
        
        public DialogBuilder title(String title) {
            this.title = title;
            return this;
        }
        
        public DialogBuilder parent(Component parent) {
            this.parent = parent;
            return this;
        }
        
        public DialogBuilder position(int x, int y) {
            this.pos = new Point(x, y);
            return this;
        }
        
        public DialogBuilder size(int width, int height) {
            this.size = new Dimension(width, height);
            return this;
        }
        
        public DialogBuilder resizable(boolean isResizable) {
            this.isResizable = isResizable;
            return this;
        }
        
        public DialogBuilder modal(boolean isModal) {
            this.isModal = isModal;
            return this;
        }
        
        public DialogBuilder defaultCloseOperation(int value) {
            this.close = value;
            return this;
        }
        
        public ReflectionContainer show() throws IllegalArgumentException, IllegalAccessException {
            JOptionPane pane = new JOptionPane(reflect, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION) {
            public static final long serialVersionUID = -4536297022915930028L;
                @Override
                public void selectInitialValue() {  }
            };
            
            JDialog d = pane.createDialog(parent, title);
            d.setResizable(isResizable);
            d.setModal(isModal);
            if (pos   != null)  d.setLocation(pos);
            if (size  != null)  d.setSize(size);
            if (close != -1)    d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
            d.setVisible(true);
            return reflect;
        }
    }
    
    
    public static DialogBuilder dialogBuilder(Object object, boolean canAccessible) throws IllegalArgumentException, IllegalAccessException {
        return new DialogBuilder(object, canAccessible);
    }
    
    public static DialogBuilder dialogBuilder(Object object) throws IllegalArgumentException, IllegalAccessException {
        return new DialogBuilder(object, false);
    }
            
    
}
