package Manager;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;

import Util.JdbcUtils;
		
public class ManagerFrame{
	boolean packOk=false;
	public ManagerFrame(){
	MisforRestaurantFrame frame=new MisforRestaurantFrame();
		if(packOk){
			frame.pack();
		}else{
			frame.validate();
		}
		
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize=frame.getSize();
		
		if(frameSize.height>screenSize.height){
			frameSize.height=screenSize.height-100;
		}
		
		if(frameSize.width>screenSize.width){
			frameSize.width=screenSize.width;
		}
	
		frame.setLocation((screenSize.width-frameSize.width)/2,(screenSize.height-frameSize.height)/2);
		
		frame.setVisible(true);
	}
	
}

class  MisforRestaurantFrame extends JFrame{
	private JPanel contentPane;
	
	private FlowLayout xYLayout1=new FlowLayout();
	private JLabel label1=new JLabel("菜名");
	private JTextField nameField=new JTextField(8);
	
	private JLabel label2=new JLabel("价钱");
	private JTextField priceField=new JTextField(16);
	
	private JButton insert=new JButton("增加菜式");
	private JButton delete=new JButton("删除菜式");
	private JButton update=new JButton("改变菜式");
	private JButton queryByName=new JButton("查询菜式");
	private JButton queryAll=new JButton("全部记录");
	private JButton showAllTables=new JButton("显示全部餐桌");
	
	Vector vector;
	String[] title={"菜名","价格"};
	String[] tableTitle={"餐桌号","状态"};
	Connection conn=null;//数据库连接
	ResultSet rs=null;
	Statement st=null;
	
	AbstractTableModel tm;
	
	//构造函数
	public MisforRestaurantFrame(){
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try{
			jbInit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//初始化数据
	private void jbInit(){
		contentPane=(JPanel)this.getContentPane();
		
		contentPane.setLayout(xYLayout1);//设置容器的布局管理对象
		setSize(new Dimension(820,700));//设置容器窗口的大小
		setTitle("菜式管理");
		
		insert.addActionListener(new java.awt.event.ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				insertData(e);
				
			}
			
		});
		
		delete.addActionListener(new java.awt.event.ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				deleteData(e);
				
			}
			
		});
		
		update.addActionListener(new java.awt.event.ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				updateData(e);
				
			}
			
		});
		
		queryByName.addActionListener(new java.awt.event.ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				queryByNameData(e);
				
			}
			
		});
		
		queryAll.addActionListener(new java.awt.event.ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				queryAllData(e);
				
			}
			
		});
		
		queryAll.addActionListener(new java.awt.event.ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				queryAllData(e);
				
			}
			
		});
		
		showAllTables.addActionListener(new java.awt.event.ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				showAllTables(e);
				
			}
			
		});
		
		createTable();
		contentPane.add(label1);
		contentPane.add(nameField);
		contentPane.add(label2);
		contentPane.add(priceField);
		contentPane.add(insert);
		contentPane.add(delete);
		contentPane.add(update);
		contentPane.add(queryByName);
		contentPane.add(queryAll);
		contentPane.add(showAllTables);
	}
	
	public void createTable(){
		JTable table;
		JScrollPane scroll;
		vector =new Vector();
		tm=new AbstractTableModel() {
			
			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				if(!vector.isEmpty()){
					return ((Vector)vector.elementAt(rowIndex)).elementAt(columnIndex);
				}else{
					return null;
				}
			}
			
			@Override
			public int getRowCount() {
				return vector.size();
			}
			
			@Override
			public int getColumnCount() {
				return title.length;
			}
			
			public String getColumnName(int column){
				return title[column];
			}
			
			public Class getColumnClass(int c){
				return getValueAt(0,c).getClass();
			}
			
			public boolean isCellEditable(int row,int column){
				return false;
			}
		};
		
		table=new JTable(tm);
		table.setToolTipText("Display Query Result");
		
		//设置表格调整尺寸模式
		table.setAutoResizeMode(table.AUTO_RESIZE_ALL_COLUMNS);
		table.setCellSelectionEnabled(false);
		table.setShowHorizontalLines(true);
		table.setShowVerticalLines(true);
		scroll=new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(800,600));
		contentPane.add(scroll);
	}
	
	
	protected void processWindowEvent(WindowEvent e){
		super.processWindowEvent(e);
		if(e.getID()==WindowEvent.WINDOW_CLOSING){
			System.exit(0);
		}
	}
	
	//显示所有餐桌的方法
	public void showAllTables(ActionEvent e){
		JTable table;
		JScrollPane scroll;
		vector =new Vector();
		contentPane.repaint();//刷新窗口
		tm=new AbstractTableModel() {
			
			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				if(!vector.isEmpty()){
					return ((Vector)vector.elementAt(rowIndex)).elementAt(columnIndex);
				}else{
					return null;
				}
			}
			
			@Override
			public int getRowCount() {
				return vector.size();
			}
			
			@Override
			public int getColumnCount() {
				return tableTitle.length;
			}
			
			public String getColumnName(int column){
				return tableTitle[column];
			}
			
			public Class getColumnClass(int c){
				return getValueAt(0,c).getClass();
			}
			
			public boolean isCellEditable(int row,int column){
				return false;
			}
		};
		
		table=new JTable(tm);
		table.setToolTipText("Display Query Result");
		
		//设置表格调整尺寸模式
		table.setAutoResizeMode(table.AUTO_RESIZE_ALL_COLUMNS);
		table.setCellSelectionEnabled(false);
		table.setShowHorizontalLines(true);
		table.setShowVerticalLines(true);
		scroll=new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(800,600));
		contentPane.add(scroll);
		contentPane.repaint();//刷新窗口
		try {
			conn=JdbcUtils.createConnection();
			String sql="select *from T_tables";
			
			ResultSet rs=JdbcUtils.executeQuery(conn, sql);
			
			//清空列表
			vector.removeAllElements();
			tm.fireTableStructureChanged();
			
			while(rs.next()){
				Vector newVector=new Vector();
				//加入数据
				newVector.addElement(rs.getString("tableName"));
				newVector.addElement(rs.getString("isNull")+"");
				vector.addElement(newVector);
			}
			
			//清空输入信息
			nameField.setText("");
			priceField.setText("");
			
			tm.fireTableStructureChanged();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}finally{
			JdbcUtils.close(conn,rs);
		}
	}
	//添加数据
	public void insertData(ActionEvent e){
		try {
			conn=JdbcUtils.createConnection();
			String caiName=nameField.getText();
			String sql="select * from T_chengpincais where caiName=?";
			
			ResultSet rs=JdbcUtils.executeQuery(conn, sql, caiName);
			if(rs.next()){
				JOptionPane.showMessageDialog(MisforRestaurantFrame.this, "该菜式已经存在，请详细查看后才操作","添加记录",1);
			}else{
		
				String sqlStr="insert into T_chengpincais(caiName,price) values(?,?)";
				String price=priceField.getText();
				
				if(caiName!=null&&price!=null&&!caiName.equals("")&&!price.equals("")){
				JdbcUtils.executeUpdate(conn, sqlStr, caiName,price);
				
				JOptionPane.showMessageDialog(MisforRestaurantFrame.this, "添加成功","添加记录",1);
				
				//清空输入信息
				nameField.setText("");
				priceField.setText("");
				
				//触发显示所有记录的按钮，显示更新后的数据
				queryAllData(e);
			}else{
				JOptionPane.showMessageDialog(MisforRestaurantFrame.this,"添加失败，请输入菜名或者价格","添加记录",1);
			}
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}finally{
			JdbcUtils.close(conn);
		}
	}
	
	//删除数据
	public void deleteData(ActionEvent e){
		try{
			conn=JdbcUtils.createConnection();
			String caiName=nameField.getText();
			String sql="select * from T_chengpincais where caiName=?";
			
			ResultSet rs=JdbcUtils.executeQuery(conn, sql, caiName);
			if(!rs.next()){
				JOptionPane.showMessageDialog(MisforRestaurantFrame.this, "该菜式不存在","查询记录",1);
			}else{
				if(caiName!=null&&!caiName.equals("")){
				String sqlStr="delete from T_chengpincais where caiName=?";
				JdbcUtils.executeUpdate(conn, sqlStr, caiName);
				
				JOptionPane.showMessageDialog(MisforRestaurantFrame.this, "删除菜式成功","删除记录",1);
				
				nameField.setText("");
				priceField.setText("");
				
				queryAllData(e);
			}else{
				JOptionPane.showMessageDialog(MisforRestaurantFrame.this, "删除失败，请输入菜名","删除记录",1);
			}
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}finally{
			JdbcUtils.close(conn,rs);
		}
	}
	
	//改变数据
	public void updateData(ActionEvent e){
		try{
		conn=JdbcUtils.createConnection();
		String caiName=nameField.getText();
		String sql="select * from T_chengpincais where caiName=?";
		
		ResultSet rs=JdbcUtils.executeQuery(conn, sql, caiName);
		if(!rs.next()){
			JOptionPane.showMessageDialog(MisforRestaurantFrame.this, "用修改的菜式不存在，请详细查看后才操作","添加记录",1);
		}else{
			String sqlStr="update T_chengpincais set price=? where caiName=?";
			String price=priceField.getText();
			
			if(price!=null&&!price.equals("")){
			JdbcUtils.executeUpdate(conn, sqlStr, price,caiName);
			
			JOptionPane.showMessageDialog(MisforRestaurantFrame.this, "修改成功","修改记录",1);
			
			//清空输入信息
			nameField.setText("");
			priceField.setText("");
			
			//触发显示所有记录的按钮，显示更新后的数据
			queryAllData(e);
		}else{
			JOptionPane.showMessageDialog(MisforRestaurantFrame.this, "修改失败，请输入菜名","修改记录",1);
		}
		}
	} catch (SQLException e1) {
		
		e1.printStackTrace();
	}finally{
		JdbcUtils.close(conn,rs);
	}
	}
	
	//根据菜名查询菜
	public void queryByNameData(ActionEvent e){
		try{
			conn=JdbcUtils.createConnection();
			String caiName=nameField.getText();
			
			if(caiName==null||caiName.equals("")){
				JOptionPane.showMessageDialog(MisforRestaurantFrame.this, "查询失败，请输入菜名","查询记录",1);
				return;
			}
			String sql="select * from T_chengpincais where caiName=?";
			
			ResultSet rs=JdbcUtils.executeQuery(conn, sql, caiName);
			if(!rs.next()){
				JOptionPane.showMessageDialog(MisforRestaurantFrame.this, "该菜式不存在","查询记录",1);
			}else{
				vector.removeAllElements();
				tm.fireTableStructureChanged();
				rs.previous();
				
				while(rs.next()){
					Vector newVector=new Vector();
					newVector.addElement(rs.getString("caiName"));
					newVector.addElement(rs.getString("price"));
					vector.addElement(newVector);
				}
				
				//清空输入信息
				nameField.setText("");
				priceField.setText("");
				
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}finally{
			JdbcUtils.close(conn,rs);
		}
	}
	
	//查询所有菜式
	public void queryAllData(ActionEvent e){
		try {
			conn=JdbcUtils.createConnection();
			String sql="select *from T_chengpincais";
			
			ResultSet rs=JdbcUtils.executeQuery(conn, sql);
			
			//清空列表
			vector.removeAllElements();
			tm.fireTableStructureChanged();
			
			while(rs.next()){
				Vector newVector=new Vector();
				//加入数据
				newVector.addElement(rs.getString("caiName"));
				newVector.addElement(rs.getString("price")+"");
				vector.addElement(newVector);
			}
			
			//清空输入信息
			nameField.setText("");
			priceField.setText("");
			
			tm.fireTableStructureChanged();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}finally{
			JdbcUtils.close(conn,rs);
			
		}
	}
}
    

