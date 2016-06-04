package Frame;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Funtion.Accounts;
import Funtion.Accounts2;
import Funtion.Management;
import Funtion.ManagementCais;
import Funtion.Order;
import Funtion.Order2;
import Funtion.Tables;
import Util.JdbcUtils;
import Util.subJframe;

public class MainFrame2 implements ActionListener,ChangeListener,ItemListener{
    private int id ;
    private JFrame frame ;
    private Container container ;
    private JTabbedPane tabbedPane ;
    private  Order2 order2 ;
    private String seat = null ;
    public static JTextArea area ,area2,area3,area4;
    private JPanel panel,panel2 ,panel3= null;
    private JComboBox comboBox,comboBox2 ; 
    private JTextField field1,field2,field3,fieldcout,field4,field5,field6;
    private Management management ;
    public static String str;
    private String funtion[] = {"菜式管理","餐桌管理","仓库管理","客户建议"} ;
    private JScrollPane scrollPane;
    Object[] date,date2;

    Accounts accounts;
    
    ManagementCais managementcais;
    
	public MainFrame2() {
		super();
		show() ;
	}
	
	private void show(){
			frame = new subJframe("管理员页面") ;
	        // 窗体组件初始化
	        init();
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        // 设置布局方式为绝对定位
	        frame.setLayout(null);
	        frame.setBounds(0, 0, 710, 700);
	        //frame.setSize(500, 400) ;
	        // 窗体大小不能改变
	        frame.setResizable(true);
	         
	        // 居中显示
	        frame.setLocationRelativeTo(null);
	        // 窗体可见
	        frame.setVisible(true);
			
	}
	private void init() {
		container =frame.getContentPane() ;
		
	    tabbedPane  = new JTabbedPane() ;
	    tabbedPane.setBounds(0, 0, 590, 600) ;
	    tabbedPane.addChangeListener(this) ;                  
	    
	    
	    //仓库管理
	    for(int i = 0;i<funtion.length;i++){
	    	if(i==0){
	    		//tabbedPane.add(funtion[i],scrollPane) ;
	    		managementcais = new ManagementCais() ;
	    	    JPanel jPanel1= managementcais.getManagement() ;
	    	    tabbedPane.add(funtion[i],jPanel1) ;
	    	}
	    	if(i == 1)
	    		  initAccount();
	    	if(i== 2){
	    		management = new Management() ;
	    	    JPanel jPanel = management.getManagement() ;
	    	    tabbedPane.add(funtion[i],jPanel) ;
	    	}
	    	
	    	if(i==3){
	    		Suggest sug=new Suggest();
	    		JPanel panel=sug.getPanel();
	    		tabbedPane.add(funtion[i],panel);
	    	}
	    }
        
	   
	    container.add(tabbedPane) ;

	}
	
	private void initAccount(){
		//餐桌管理板块
		accounts=new Accounts(0);
		panel=accounts.getRecipes();
		
	    
	    fieldcout = new JTextField("",10) ;
	    fieldcout.setEditable(false) ;
	    panel.add(new JLabel("查询使用中的桌子的详细信息")) ;
	    
	    uptatepanel() ;
	    panel.add(panel2) ;
	    
	    uptatepane2() ;
	    panel.add(new JLabel("查询以往的消费记录")) ;
	    panel.add(panel3) ;
	    //保修功能
	    tabbedPane.add(funtion[1],panel) ;
	}
	
	/**
	 * 追加结账功能
	 */
	private void uptatepanel(){
		
		//导入所有的使用中的餐桌
		//数据库操作
		ResultSet rs=null;
		Connection conn=null;
		List list=new ArrayList();
		try {
			conn=JdbcUtils.createConnection();
			String sql="select * from T_consumes2";
			rs=JdbcUtils.executeQuery(conn, sql);
			
			list.add("选择桌子");
			while(rs.next()){
				if(rs.getInt("isPayMoney")==0){
				list.add("                "+rs.getObject("tableName"));
				}
			}
			
			date2=list.toArray();
			
			comboBox2=new JComboBox(date2) ;
			//添加点击事件
			comboBox2.addItemListener(this);
			
			field4= new JTextField("",10) ;
			//field2 = new JTextField("",10) ;
			area4=new JTextArea(5,10);
			scrollPane=new JScrollPane(area4);
			field6= new JTextField("",10) ;
			
			panel2  =new JPanel() ;
			
			panel2.add(comboBox2) ;
			panel2.add(new JLabel("使用客户：")) ;
			panel2.add(field4) ;
			panel2.add(new JLabel("消费内容：")) ;
			//panel2.add(field2) ;
			panel2.add(scrollPane);
			panel2.add(new JLabel("应付金额：")) ;
			panel2.add(field6) ;
			
		} catch (SQLException e) {
			System.out.println("操作数据库异常");
			e.printStackTrace();
		}
		
	}
	/**
	 * 获得另外一个面板
	 */
private void uptatepane2(){
		
		//导入所有的使用中的餐桌
		//数据库操作
		ResultSet rs=null;
		Connection conn=null;
		List list=new ArrayList();
		try {
			conn=JdbcUtils.createConnection();
			String sql="select * from T_consumes3";
			rs=JdbcUtils.executeQuery(conn, sql);
			
			list.add("选择项目");
			int i=1;
			while(rs.next()){
				if(rs.getInt("isPayMoney")==1){
				list.add("                "+i);
				i++;
				}
			}
			
			date=list.toArray();
			
			comboBox  =new JComboBox(date) ;
			//添加点击事件
			comboBox.addItemListener(this);
			
			field1 = new JTextField("",10) ;
			//field2 = new JTextField("",10) ;
			area3=new JTextArea(5,10);
			scrollPane=new JScrollPane(area3);
			
			field3 = new JTextField("",10) ;
		
			panel3 =new JPanel() ;
			
			panel3.add(comboBox) ;
			panel3.add(new JLabel("消费客户：")) ;
			panel3.add(field1) ;
			panel3.add(new JLabel("消费内容：")) ;
			panel3.add(scrollPane);
			//panel3.add(field2) ;
			panel3.add(new JLabel("已付金额：")) ;
			panel3.add(field3) ;
			
		} catch (SQLException e) {
			System.out.println("操作数据库异常");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * JTabbedPane的转换事件
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
	    
	    tabbedPane.setBounds(0, 0, 690, 700) ;
	}
    
	/**
	 * 
	 * @param e
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == comboBox){
			if(e.getStateChange() == ItemEvent.SELECTED){
				//获取项目id和选择的项目
				int i = comboBox.getSelectedIndex() ;
				
				//往面板输出消费内容
				ResultSet rs=null;
				String sql="select * from T_consumes3 where id=?";
				
				try {
					rs=JdbcUtils.executeQuery(sql,i);
					if(rs.next()){
						field1.setText(rs.getString("userName"));
						area3.setText(rs.getString("eat"));
						field3.setText(rs.getString("money"));
					}
				} catch (SQLException e1) {
					System.out.println("往面板输出消费内容异常");
					e1.printStackTrace();
				}finally{
					JdbcUtils.closeAll(rs);
				}
			}
		}
		
		//使用中的桌子
		if(e.getSource() == comboBox2){
			if(e.getStateChange() == ItemEvent.SELECTED){
				int i = comboBox2.getSelectedIndex() ;
				//处理下拉事件
				//获取到桌子的名称
				
				String temp=(String)date2[i];
				String tableName=temp.substring(temp.length()-2, temp.length());
				
				//往面板输出消费内容
				ResultSet rs=null;
				String sql="select * from T_consumes2 where tableName=?";
				System.out.println(tableName);
				try {
					rs=JdbcUtils.executeQuery(sql, tableName);
					if(rs.next()){
						field4.setText(rs.getString("userName"));
						area4.setText(rs.getString("eat"));
						field6.setText(rs.getString("money"));
					}
				} catch (SQLException e1) {
					System.out.println("往面板输出消费内容异常");
					e1.printStackTrace();
				}finally{
					JdbcUtils.closeAll(rs);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
