package Frame;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import Util.JdbcUtils;
import Util.subJframe;

public class ReviewSeat implements MouseListener,ItemListener{
	private String select[] = new String[1024];
    private int id ;
    private JComboBox comboBox ;
    private JLabel label ;
    private JList list ;
    private JFrame frame;
    private Container container ;
    private String seat ;
    
	public ReviewSeat(int id) {
		this.id = id ;
		getsekect() ;
		show() ;
		closeWindow() ;
	}
	
	/**
	 * 为combox赋值，导入数据库表
	 */
	private void getsekect(){
//		for(int i =0; i<select.length ;i++){
//			select[i] =""+i ;
//		}
		
		Connection conn=null;
		ResultSet rs=null;
		try {
			conn=JdbcUtils.createConnection();
			//查询桌子的语句
			String sql="select *from T_tables";
			//获取查询结果
			rs=JdbcUtils.executeQuery(conn, sql);
			int i=0;
			//添加选择项
			while(rs.next()){
				int msg=rs.getInt("isNull");
				if(msg==1){
				select[i]=rs.getString("tableName");
				i++;
				}
			}
			
		} catch (SQLException e) {
			System.out.println("在导入桌子的时候发生了异常"+e.getMessage());
		}finally{
			JdbcUtils.close(conn,rs);
		}
	}
	
	/**
	 * 添加窗口关闭事件，是点击关闭，返回登陆界面
	 */
	private void closeWindow() {
		frame.addWindowListener(new WindowAdapter() { 

			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				//new LoginFrame();
			}

		});
	}

	private void show(){
		
		frame  = new JFrame();
		
		    
		init() ;
		frame.setLayout(null) ;
     
        frame.setBounds(0, 0, 200, 60);

        // 窗体大小不能改变
        frame.setResizable(false);
         
        // 居中显示
        frame.setLocationRelativeTo(null);
 
        // 窗体可见
        frame.setVisible(true);
		
	}

	private void init() {
		container = frame.getContentPane() ;
		
		comboBox =new JComboBox(select) ;
		comboBox.setMaximumRowCount(15) ;
		comboBox.setBounds(0, 0, 100, 20) ;
		comboBox.addItemListener(this) ;
		
		label  = new JLabel("请选则桌子") ;
		label.setBounds(105, 0, 95, 20) ;
		label.setForeground(Color.green);
		label.addMouseListener(this) ;
		
		container.add(label) ;
		container.add(comboBox) ;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == comboBox){
			if(e.getStateChange() == ItemEvent.SELECTED){
				int i = comboBox.getSelectedIndex() ;
				seat = select[i] ;
				label.setText(select[i]+"   确定") ;
			}
		}
		
	}
   
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == label){ 
			//当点击确定的时候，获得选择桌子的名字
			String labelText=label.getText();
			String tableName=labelText.substring(0, 2);
			
			if(labelText.endsWith("确定")){//界面跳转，转到主界面
				//将选择的桌子的isNull改为0意思是正在使用中
				//UPDATE T_Persons Set Age=30 where Name='tom'
				String sql="update T_tables set isNull=? where tableName=?";
				try {
					JdbcUtils.executeUpdate(sql,0,tableName);
					
					//获取用户选择的桌子
					MainFrame.getTableName(tableName);
					
					//设置窗体不可见
					frame.setVisible(false);
					//跳转到主页面
					new MainFrame();
				} catch (SQLException e1) {
					System.out.println("更新桌子状态时发生错误"+e1.getMessage());
				}
			
			}
			
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


}
