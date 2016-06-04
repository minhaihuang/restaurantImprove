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
import java.util.Arrays;

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
import Funtion.Accounts2;
import Funtion.Management;
import Funtion.Order;
import Util.JdbcUtils;
import Util.subJframe;

public class MainFrame implements ActionListener,ChangeListener,ItemListener{
    private JFrame frame ;
    private Container container ;
    private JTabbedPane tabbedPane ;
    private JScrollPane scrollPane,scrollPane2;
    private JTable table;
    private JButton button,button2 ;
    private  Order order ;
    public static JTextArea area ,area2;
    private Accounts2 accounts ;
    private JPanel panel,panel2 = null;
    private JComboBox comboBox ; 
    private JTextField field1,field2,field3,fieldcout;
    public static String str;
    private String funtion[] = {"点菜","结账"} ;

    public static String userName;//用户名
    public static String passWord;//密码
    public static String tableName;//用户选择的餐桌
    public static int money;//获得消费总金额
    
    private JButton suggest;
    private JButton payMoney;
    
	public MainFrame() {
		super();
		show() ;
	}
	
	private void show(){
			frame = new subJframe("欢迎关顾“好好吃餐厅”") ;
	        // 窗体组件初始化
	        init();
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        // 设置布局方式为绝对定位
	        frame.setLayout(null);
	        frame.setBounds(0, 0, 600, 500);
	        //frame.setSize(500, 400) ;
	        // 窗体大小不能改变
	        frame.setResizable(false);
	         
	        // 居中显示
	        frame.setLocationRelativeTo(null);
	        // 窗体可见
	        frame.setVisible(true);
			
	}
	private void init() {
		container =frame.getContentPane() ;
		
	    tabbedPane  = new JTabbedPane() ;
	    tabbedPane.setBounds(0, 0, 590, 400) ;
	    tabbedPane.addChangeListener(this) ;                  
	    
	    //点菜功能板
	    order = new Order() ;
	    scrollPane = order.getRecipes(table) ;
	    area  = new JTextArea("") ;
	    scrollPane2 = new JScrollPane(area) ;
	    scrollPane2.setBounds(120, 410, 300, 50) ;
	    scrollPane2.setVisible(false) ;
	    
	    button = new JButton("确认点菜") ;
	    button.setBounds(480, 410, 100, 50) ;
	    button.addActionListener(this) ;
	    
	    button2 = new JButton("呼叫服务员") ;
	    button2.setBounds(0, 410, 100, 50) ;
	    button2.addActionListener(this) ;
	      
	    //仓库管理
	    for(int i = 0;i<funtion.length;i++){
	    	if(i==0){
	    		tabbedPane.add(funtion[i],scrollPane) ;
	    	}
	    
	    }
        
	    container.add(button2) ;
	    container.add(button) ;
	    container.add(scrollPane2) ;
	    container.add(tabbedPane) ;
	}
	
	private void initAccount(){
		//结账功能板
	    accounts = new Accounts2(area) ;
	    panel=accounts.getPanel();
		
		 
	    money=accounts.getMoney(area);//获得消费总金额
	    
	    //这是提交建议按钮
	    suggest=new JButton("提交建议");
	    //suggest.setBounds(x, y, width, height);
	    suggest.addActionListener(this);
	    
	    //确认付款按钮
	    payMoney=new JButton("确认付款");
	    payMoney.addActionListener(this);
	    
	    String temp=Integer.toString(money);
	    fieldcout = new JTextField(temp,10) ;
	    fieldcout.setEditable(false) ;
	    panel.add(new JLabel("本次消费总额：")) ;
	    panel.add(fieldcout) ;
	    panel.add(payMoney);
	    panel.add(new JLabel("————————————————————————————————————————————————————————————")) ;
	    panel.add(new JLabel("你可以查询你之前的消费")) ;
	    uptatepanel() ;
	    panel.add(panel2) ;
	    
	    //保修功能
	    panel.add(new JLabel("————————————————————————————————————————————————————————————")) ;
	    panel.add(new JLabel("如果您对我们餐厅有什么意见，欢迎提出")) ;
	    JPanel panelx = new JPanel() ;

	    area2 = new JTextArea("您的建议很宝贵\n") ;
	    panel.add(panelx);
	    JScrollPane pane = new JScrollPane(area2) ;
	    pane.setPreferredSize(new Dimension(160, 70)) ;
	    panel.add(pane) ;
	    panel.add(suggest);
	    tabbedPane.add(funtion[1],panel) ;
	}
	
	/**
	 * 追加结账功能
	 */
	private void uptatepanel(){
		
		//查询consotor表找出该顾客
		String date[]={"2015-12-19","2015-08-19","2015-05-19","2015-07-19","2015-05-30"} ;
		comboBox  =new JComboBox(date) ;
		
		field1 = new JTextField("",10) ;
		field2 = new JTextField("",10) ;
		field3 = new JTextField("",10) ;
		
		panel2  =new JPanel() ;
		panel2.add(comboBox) ;
		panel2.add(new JLabel("日期：")) ;
		panel2.add(field1) ;
		panel2.add(new JLabel("桌位：")) ;
		panel2.add(field2) ;
		panel2.add(new JLabel("消费：")) ;
		panel2.add(field3) ;
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button){
			if(!order.isSelect()[0].equals("")){
				tabbedPane.remove(panel);
		          area.setText(order.isSelect()[1]) ;//显示所点的菜
		         
		          initAccount();
		          
		          //往T_consumes2表中插入数据
		          insertIntoConsumes2();
			}else{
				new Notice("你还没选择菜", "温馨提示") ;
			}
			
		}
		
		if(e.getSource() ==  button2){
			new Notice("我们的服务员马上就来", "请稍等片刻") ;
		}
		
		//提交建议按钮事件
		if(e.getSource()==suggest){
			String name=getUserName(userName);//获取用户名
			String text=area2.getText();
			
			//插入数据到T_suggests表
			Connection conn=null;
		
			
			try {
				conn=JdbcUtils.createConnection();
				String sql="insert into T_suggests(userName,suggestText) values(?,?)";
				JdbcUtils.executeUpdate(conn, sql, name,text);
				
				new Notice("建议已提交", "谢谢您的建议") ;
			} catch (SQLException e1) {
				System.out.println("操作数据库异常");
				e1.printStackTrace();
			}finally{
				JdbcUtils.close(conn);
			}
		}
		
		//确认付款按钮事件,将当前桌子设为虚位待客状态
		if(e.getSource()==payMoney){
			String name=getUserName(userName);//获取用户名
			String table=getTableName(tableName);//获取用户选择的餐桌
			
			String msg=area.getText();//获取用户选择的菜式
			
			int index=msg.indexOf("：");
			String temp=msg.substring(index+2);//切割的信息要从：的索引加上2开始。因为有回车符和换行符
			String[] data=temp.split("\\s+");//切割信息
			//获取菜式
			String cais=new String();
			for(int i=0;i<data.length;){
				cais+=data[i+1]+"\n";
				i+=3;
			}
			Connection conn=null;
			try {
				conn=JdbcUtils.createConnection();
				
				String sql="insert into T_consumes3(tableName,userName,eat,money,isPayMoney) values(?,?,?,?,?)";
				String sql2="update T_tables set isNull=? where tableName=?";
				String sql3="update T_consumes2 set isPayMoney=? where tableName=?";
				JdbcUtils.executeUpdate(conn, sql,table,name,cais,money,1);
				JdbcUtils.executeUpdate(conn, sql2, 1,table);
				JdbcUtils.executeUpdate(conn, sql3, 1,table);
				new Notice("已成功付款", "谢谢您的惠顾") ;
			} catch (SQLException e1) {
				System.out.println("操作数据库有误");
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * JTabbedPane的转换事件
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
	    int selectedIndex = tabbedPane.getSelectedIndex();
	    //area.setText("") ;//不用清空
	    if(selectedIndex!=0){
	    	button.setVisible(false) ;
	    	button2.setVisible(false) ;
	    	scrollPane2.setVisible(false) ;
	    	
	    }
	    
	    if(selectedIndex==1||selectedIndex==2){
	    	 tabbedPane.setBounds(0, 0, 590, 500) ;
	    }else{
	    	 tabbedPane.setBounds(0, 0, 590, 400) ;
	    }
	    switch (selectedIndex) {
	    case 0:
	    	//点菜页面
	    	scrollPane2.setVisible(true) ;
	    	button.setVisible(true) ;
	    	button2.setVisible(true) ;
	    	System.out.println(34452);
	    	break ;
	    }
		
	}
    
	/**
	 * 
	 * @param e
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == comboBox){
			if(e.getStateChange() == ItemEvent.SELECTED){
				int i = comboBox.getSelectedIndex() ;
				//处理下拉事件
			}
		}
	}
	
	//获取用户登录的用户名
		public static String getUserName(String userName){
			MainFrame.userName=userName;
			
			//System.out.println(MainFrame.userName);
			return MainFrame.userName;
		}
	      
		//获取用户选择的桌子
		public static String getTableName(String tableName){
			MainFrame.tableName=tableName;
			return MainFrame.tableName;
		}
		
		//往消费表中插入数据
		public void  insertIntoConsumes2(){
			String name=getUserName(userName);//获取用户名
			String table=getTableName(tableName);//获取用户选择的餐桌
			
			String msg=area.getText();//获取用户选择的菜式
			
			int index=msg.indexOf("：");
			String temp=msg.substring(index+2);//切割的信息要从：的索引加上2开始。因为有回车符和换行符
			String[] data=temp.split("\\s+");//切割信息
			//获取菜式
			String cais=new String();
			for(int i=0;i<data.length;){
				cais+=data[i+1]+"\n";
				i+=3;
			}
			//获取用户名是否存在,接下来的都是数据库操作
			ResultSet rs=null;
			
			try {
				//开始插入了
				rs=JdbcUtils.executeQuery("select * from T_consumes2 where userName=?", name);
				boolean f=rs.next();
				if(f==false){//不存在则插入数据
					
				String sql="insert into T_consumes2(tableName,userName,eat,money,isPayMoney) values(?,?,?,?,?)";
				JdbcUtils.executeUpdate(sql, table,name,cais,money,0);
				System.out.println("插入成功");
				
				}else{//存在，则更新数据
					String sql2="update T_consumes2 set eat=?,money=? where userName=?";
					JdbcUtils.executeUpdate(sql2, cais,money,name);
					System.out.println("更新成功");
				}
			} catch (SQLException e) {
				System.out.println("插入消费表发生了异常"+e.getMessage());
				e.printStackTrace();
			}
		}
      
}
