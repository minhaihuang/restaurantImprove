package Frame;


import java.awt.Color;
import java.awt.Container;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Manager.ManagerFrame;
import Util.Config;
import Util.JdbcUtils;

public class LoginFrame extends JFrame implements ItemListener,ActionListener,MouseListener{
	
	private Container container ;
	private JLabel label1,label2,label3,lable4,label;
	private JTextField field ;
	private JPasswordField passwordField1,passwordField2 ;
	private JRadioButton radioButton1,radioButton2,radioButton3,radioButton;
	private ButtonGroup group ;
	private JButton button1,button2,button3;
	private JCheckBox box2 ;
	private Connection connection = null ;
	private int id ;//记录用户id 用于传输
	private int n = Config.USER ;  //用户的权限
	
	
	public LoginFrame() {
		connection() ;//实现数据库连接
		 // 设置窗口标题
        this.setTitle("欢迎光临");
        // 窗体组件初始化
        init();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置布局方式为绝对定位
        this.setLayout(null);
         
        this.setBounds(0, 0, 400, 300);

        // 窗体大小不能改变
        this.setResizable(false);
         
        // 居中显示
        this.setLocationRelativeTo(null);
 
        // 窗体可见
        this.setVisible(true);
		
	}

	private void init() {
		  container = this.getContentPane();
		  
		  label = new JLabel("请登录!") ;
		  label.setBounds(50, 240, 200,20) ;
		  label.setFont(new java.awt.Font("Dialog",1, 15)) ;//设置字体大小
		  
		  label1 = new JLabel("账号：");
		  label1.setBounds(100, 80, 40, 30) ;
		
		  field  =new JTextField("请输入账号", 10) ;
		  field.setBounds(label1.getX()+label1.getWidth(), label1.getY()+3, 150, 25) ;
		 
		  label2 = new JLabel("密码：") ;
		  label2.setBounds(label1.getX(), label1.getY()+label1.getHeight()+5, label1.getWidth(), label1.getHeight()) ;
		  
		  label3 = new JLabel("密码：") ;
		  label3.setBounds(label1.getX(), label2.getY()+label1.getHeight()+5, label1.getWidth(), label1.getHeight()) ;
		  
		  lable4 = new JLabel("没有账号点击这里") ;
		  lable4.setBounds(320, 0, 140, 20) ;
		  lable4.addMouseListener(this) ;
		  lable4.setForeground(Color.green);
		  lable4.setFont(new java.awt.Font("Dialog",0, 9));
		  
		  passwordField1  =new JPasswordField("") ;
		  passwordField1.setBounds(field.getX(), field.getY()+34, field.getWidth(), field.getHeight()) ;
		  
		  passwordField2  =new JPasswordField("") ;
		  passwordField2.setBounds(field.getX(), field.getY()+68, field.getWidth(), field.getHeight()) ;
		  
		  radioButton1 = new JRadioButton("一般", true) ;
		  radioButton1.setBounds(label2.getX()-5, passwordField1.getY()+34, 70, 20) ;
		  radioButton1.addItemListener(this) ;
		 
		  radioButton2 = new JRadioButton("vip") ;
		  radioButton2.setBounds(radioButton1.getX()+70, passwordField1.getY()+34, radioButton1.getWidth(), radioButton1.getHeight()) ;
		  radioButton2.addItemListener(this) ;
		  
		  radioButton3 = new JRadioButton("管理员") ;
		  radioButton3.setBounds(radioButton2.getX()+70, passwordField1.getY()+34,radioButton1.getWidth(), radioButton1.getHeight()) ;
		  radioButton3.addItemListener(this) ;
		  
		  radioButton = new JRadioButton("boss") ;
		  radioButton.setBounds(340, 250,radioButton1.getWidth(), radioButton1.getHeight()) ;
		  radioButton.setEnabled(false) ;
		  radioButton.addItemListener(this) ;
		 
		  group  =new ButtonGroup() ;
		  group.add(radioButton) ;
		  group.add(radioButton1) ;
		  group.add(radioButton2) ;
		  group.add(radioButton3) ;
		  
		  button1 = new JButton("登陆") ;
		  button1.setBounds(165, radioButton3.getY()+35, 70, 30) ;
		  button1.setBackground(Color.green) ;
		  button1.addActionListener(this) ;
		  
		  button2 = new JButton("注册") ;
		  button2.setBounds(button1.getX(), radioButton3.getY()+35, 70, 30) ;
		  button2.addActionListener(this) ;
		  
		  button3 = new JButton("返回登陆") ;
		  button3.setBounds(300, 251, 95, 20) ;
		  button3.addActionListener(this) ;
		  
		  box2 = new JCheckBox("开启超级管理模式") ;
		  box2.setFont(new java.awt.Font("Dialog",0, 10));
		  box2.setBounds(290, 22, 150, 20) ;
		  
		  box2.addItemListener(this) ;
		  
		  container.add(radioButton) ;
		  container.add(radioButton1) ;
		  container.add(radioButton2) ;
		  container.add(radioButton3) ;
		  
		  container.add(button1) ;
		  container.add(button2) ;
		  container.add(button3) ;
		  
		  container.add(box2) ;
		  
		  container.add(field) ;
		  
		  container.add(label1) ;
		  container.add(label2) ;
		  container.add(label3) ;
		  container.add(lable4) ;
		  container.add(label) ;
		  
		  container.add(passwordField2) ;
		  container.add(passwordField1) ;
		  
		  register(false) ;
		
	}
	
    /**
     * 转换成注册界面
     * @param val
     */
	private void register(Boolean val){
		radioButton.setVisible(!val) ;
		radioButton1.setVisible(!val) ;
		radioButton2.setVisible(!val) ;
		radioButton3.setVisible(!val) ;
		button1.setVisible(!val) ;
		box2.setVisible(!val) ;
		button2.setVisible(val) ;
		label3.setVisible(val) ;
		passwordField2.setVisible(val) ;
		button3.setVisible(val) ;
	}
	
	private void connection(){
	   try {
		connection  = JdbcUtils.createConnection() ;
	} catch (SQLException e) {
		System.out.println("操作数据库失败"+e.getMessage());
	}
	}
    
	//checkbox点击事件
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == box2){
			if(e.getStateChange() == ItemEvent.SELECTED){
				Root(false) ;
			}else{
				Root(true) ;
			}
		}
		
		if(e.getSource() == radioButton1){
			n = Config.USER ;
		}
		if(e.getSource() == radioButton2){
			n = Config.USER_VIP ;
		}
		if(e.getSource() == radioButton3){
			n = Config.USER_ADMIN ;
		}
		if(e.getSource() == radioButton){
			n = Config.USER_BOSS ;
		}
	}
	
	/**
	 * 开启超级管理模式
	 * @param val
	 */
	private void Root(Boolean val){
		radioButton.setEnabled(!val) ;
		radioButton.setSelected(!val) ;
		radioButton1.setEnabled(val) ;
		radioButton2.setEnabled(val) ;
		radioButton3.setEnabled(val) ;
		radioButton1.setSelected(val) ;;
	}
    
	//按钮点击事件
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == button1){ //登陆
			if(ispass()){//登陆成功
				if(n==Config.USER){
				this.setVisible(false);
				new ReviewSeat(0) ;//选择桌子
				}else if(n==Config.USER_ADMIN){
					//跳转到管理员页面
					this.setVisible(false);
					new MainFrame2();
				}
			}			
		}
		
		if(e.getSource() ==button2){ //注册
			System.out.println(123);
			if(isexit()){//注册成功
				
				register(false) ;
				lable4.setForeground(Color.green) ;
				label.setText("注册成功，请登录") ;
			}
		}
		
		if(e.getSource() == button3){
			register(false) ;
			lable4.setForeground(Color.green) ;
		}		
	}
    
	//判断是否符合注册规范
	private boolean isexit() {
		boolean flag=false;
		String userName=field.getText();
		Connection conn=null;
		ResultSet rs=null;
		String sql="select *from T_users where userName=?";
		try {
			conn=JdbcUtils.createConnection();
			rs=JdbcUtils.executeQuery(conn, sql,userName);
			if(rs.next()){//如果已经已经存在客户，注册失败，返回false
				label.setText("该用户名已经存在");
				flag=false;
				return flag;
			}else{//如果不存在
				
				char[] ch1=passwordField1.getPassword();
				char[] ch2=passwordField2.getPassword();
				
				if(ch1==null||ch2==null){
					label.setText("请输入密码") ;
					return false;
				}
				String passWord1=new String(ch1,0,ch1.length);
				String passWord2=new String(ch2,0,ch2.length);
				
				if(!passWord1.equals(passWord2)){
					//如果两次输入的密码不相等
					label.setText("两次输入的密码不同") ;
					flag=false;
					return flag;
				}else{
					//注册成功
					//往数据库插入数据
					String sql2="insert into T_users(userName,passWord,type,consumeTimes) values(?,?,?,?)";
					JdbcUtils.executeUpdate(conn, sql2, userName,passWord1,0,0);
					return true;
				}
			}
		} catch (SQLException e) {
			System.out.println("操作数据库异常");
			e.printStackTrace();
		}
		return false;
	}

	//判断是否符合登陆规范
	private boolean ispass() {
		String parameters1 = field.getText();//获取用户名
		
		char[] password = passwordField1.getPassword();//获取密码
		String parameters2=new String(password,0,password.length);
		
		boolean flag=false;
		if((!parameters1.equals(""))&&(!parameters2.equals(""))&&(!parameters1.startsWith("请"))){
			
			if(n== Config.USER){//普通会员登陆
				String sql  ="select * from t_users where username=?" ;
				System.out.println(n);
				ResultSet rs =null;
				try {
					rs = JdbcUtils.executeQuery(connection,sql,parameters1) ;
					if(rs.next()){
						if((rs.getInt("consumeTimes") == 100)&& (n == Config.USER)){ //如果消费超过100次，自动升级vip
							String sql2  ="update t_users set type=? where usename=?" ;
							JdbcUtils.executeUpdate(sql2, connection, Config.USER_VIP,parameters1) ;
							label.setText("用户已提升为vip,请更换登陆") ;
							
							flag=false;
						}else{   
							//正常登录
							if(rs.getString("passWord").equals(parameters2)){
								
								//向主页面发送用户名
								MainFrame.getUserName(parameters1);
								
								flag=true;
							}else{                                 //密码错误
								label.setText("密码有误！") ;
								passwordField1.setText("") ;
								flag=false;
							}
						}
					}else{
						label.setText("用户不存在，请更换类型或者注册账号") ;
						flag=false;
					}
				} catch (SQLException e) {
					System.out.println("查询数据库失败"+e.getMessage());
					flag=false;
				}
			}else if(n==Config.USER_ADMIN){//管理员登陆
				String sql  ="select * from t_managers where managerName=?" ;
				System.out.println(n);
				ResultSet rs =null;
				try {
					rs = JdbcUtils.executeQuery(connection,sql,parameters1) ;
					if(rs.next()){
							//正常登录
							if(rs.getString("passWord").equals(parameters2)){
								
								flag=true ;
							}else{                                 //密码错误
								label.setText("密码有误！") ;
								passwordField1.setText("") ;
								flag=false;
							}
					}else{
						label.setText("该管理员不存在") ;
						flag=false;
					}
				} catch (SQLException e) {
					System.out.println(46565);
					flag=false;
				}
			}
		}
		
		return flag;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() ==lable4){
			if(lable4.getForeground() !=Color.red){
			        lable4.setForeground(Color.red) ;
			        register(true) ;
			        label.setText("请注册") ;
			       }
		}
	}
	
	//鼠标点击
	//鼠标点击
	@Override
	public void mousePressed(MouseEvent e) {
		
	}
    
	//鼠标点击后
	@Override
	public void mouseReleased(MouseEvent e) {     
		
	}
    
	//鼠标移到指定位置
	@Override
	public void mouseEntered(MouseEvent e) {	
		
	}
    
	//鼠标离开指定位置后
	@Override
	public void mouseExited(MouseEvent e) {
		
	}

}
