
package reflectionpane;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author mabasasi
 */
public class Person {
    public String name = "おれ";
    public String nickname = "NN";
    public int age = 30;
    public double height;
    public double width;
    public boolean isMan;
    public String[] job = {"teacher", "doctor", "reader"};
    public List<String> friend = new ArrayList<>();

    public Person() {
        friend.add("たなか");
        friend.add("たむら");
    }
    
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Dump site config > ");
        
        Field[] fields = this.getClass().getFields();
        try {
            for (Field field : fields) {
                Object obj = field.get(this);
                
                String key = field.getName();
                String msg = (obj == null) ? "null" : (obj.getClass().isArray()) ? Arrays.toString((Object[]) obj) : obj.toString();
                
                sb.append(String.format("%n  %-20s : %s", key, msg));
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            sb.append(e.toString());
        }
        return sb.toString();
    }
    
}
