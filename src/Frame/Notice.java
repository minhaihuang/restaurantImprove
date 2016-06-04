package Frame;

import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Frame.LoginFrame;

public class Notice extends JFrame implements ActionListener{
     private JButton button1  ;
     private JLabel label ;
     private Container container ;
	 public Notice(final String notice,final String title){
		 
		 this.addWindowListener(new WindowAdapter() { 
				public void windowClosing(WindowEvent e) {
					
					new Notice(notice,title) ;
				}

			});
		    this.setTitle(title) ;
		    container = this.getContentPane() ;
		    
	        label =new JLabel(notice,JLabel.CENTER) ;
	        label.setBounds(100, 0, 200, 20) ;
	        
	        
	        button1 =new JButton("确定") ;
	        button1.setBounds(150, 35, 100, 20) ;
	        button1.addActionListener(this) ;
	        
	        container.add(button1) ;
	        container.add(label) ;
	        
	        this.setLayout(null);
	        this.setBounds(0, 0, 400, 100);
	        //frame.setSize(500, 400) ;
	        // 窗体大小不能改变
	        this.setResizable(false);
	         
	        // 居中显示
	        this.setLocationRelativeTo(null);
	        // 窗体可见
	        this.setVisible(true);

	 }
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button1){
			//this.setVisible(false) ;
			this.dispose() ;
		}
		
	}

	 
     
}
