package Util;

import javax.swing.JFrame;

public class subJframe extends JFrame {
    public subJframe(){} ;
    
    public subJframe(String title){
   	 super(title) ;
    }
    
    protected void frameInit(){
   	 super.frameInit() ;
   	 setDefaultCloseOperation(EXIT_ON_CLOSE) ;
    }
}
