/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reflectionpane;

/**
 *
 * @author mabasasi
 */
public class Test {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
        // ダイアログ呼び出し例
        ReflectionContainer reflect = ReflectionPane.dialogBuilder(new Person(), true).title("たいとる").position(100, 100).size(640, 480).show();
        
        // コンテナだったらこんな感じで使う
//        ReflectionContainer reflect = new ReflectionContainer(new Person());
//        JFrame f = new JFrame();
//        f.getContentPane().add(reflect);
//        f.setSize(640, 480);
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.setVisible(true);

        // 後は、外部からreflectに対してメソットの実行をすると使える.
    }
    
}
