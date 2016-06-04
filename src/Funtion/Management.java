package Funtion;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import Util.JdbcUtils;

public class Management extends JFrame implements ActionListener{
	private JPanel panel;
	private Util.MyAbstractTableModel model;
	private JTable table;
	private Object data1[][] = new Object[15][3];
	private JButton add=new JButton("增加原料");
	private JButton delete=new JButton("删除原料");
	private JButton change=new JButton("改变原料");
	private JButton query=new JButton("查看原料");
	private JButton queryAll=new JButton("查看全部原料");

	public JLabel label1=new JLabel("菜名");
    public JTextField nameField=new JTextField(8);
	
    public JLabel label2=new JLabel("价钱");
    public JTextField priceField=new JTextField(16);
    
    AbstractTableModel tm;
    
    Vector<Object> vector ;
    String[] head = { "原料名", "价格", "数量" };
	
	public JPanel getManagement() {
		panel = new JPanel(null);
		JPanel panel1;

		panel1 = new JPanel();
		
		//为各个按钮注册单击事件
		query.addActionListener(this);
		delete.addActionListener( this);
		change.addActionListener( this);
		add.addActionListener(this);
		
		
		//设置各个按钮的位置
		add.setBounds(0, 395, 100, 60);
		delete.setBounds(110, 395, 100, 60);
		change.setBounds(220,395,100, 60);
		query.setBounds(330, 395, 100, 60);
		queryAll.setBounds(440, 395, 120, 60);
		
		label1.setBounds(160,280,50, 50);
		nameField.setBounds(200, 300, 150, 25);
		
		label2.setBounds(160, 320,50,50);
		priceField.setBounds(200, 340, 150, 25);

		vector = new Vector<Object>();

		//原料管理
		JLabel label = new JLabel("原料管理：");
		label.setBounds(0, 0, 100, 20);
        
		createTable();
		panel.add(label);
		panel.add(add);
		panel.add(delete);
		panel.add(query);
		panel.add(change);
		panel.add(label1);
		panel.add(nameField);
		panel.add(label2);
		panel.add(priceField);
		panel.add(queryAll);
		return panel;

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
				return head.length;
			}
			
			public String getColumnName(int column){
				return head[column];
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
		scroll.setBounds(25, 25, 550, 250);
		panel.add(scroll);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==query){//查询按钮
			queryData();
		}
		
		if(e.getSource()==delete){//删除按钮
			deleteData();
		}
		
		if(e.getSource()==add){//增加按钮
			addData();
		}
		
		if(e.getSource()==change){//改变按钮
			changeData();
		}
		
	}
	
	public void queryData(){
		System.out.println(123);
		Connection conn=null;
		ResultSet rs=null;
		try {
			conn=JdbcUtils.createConnection();
			String sql="select *from t_yuanliaocai";
			
			 rs=JdbcUtils.executeQuery(conn, sql);
			
			//清空列表
			vector.removeAllElements();
			tm.fireTableStructureChanged();
			
			while(rs.next()){
				Vector newVector=new Vector();
				//加入数据
				newVector.addElement(rs.getString("caiName"));
				newVector.addElement(rs.getString("price")+"");
				newVector.addElement(rs.getInt("number"));
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
	
	//删除数据
	public void deleteData(){
		Connection conn=null;
		ResultSet rs=null;
		try{
			conn=JdbcUtils.createConnection();
			String caiName=nameField.getText();
			String sql="select * from T_yuanliaocai where caiName=?";
			
			rs=JdbcUtils.executeQuery(conn, sql, caiName);
			if(!rs.next()){
				JOptionPane.showMessageDialog(Management.this, "该菜式不存在","查询记录",1);
			}else{
				if(caiName!=null&&!caiName.equals("")){
				String sqlStr="delete from T_yuanliaocai where caiName=?";
				JdbcUtils.executeUpdate(conn, sqlStr, caiName);
				
				JOptionPane.showMessageDialog(Management.this, "删除菜式成功","删除记录",1);
				
				nameField.setText("");
				priceField.setText("");
				
				queryData();
			}else{
				JOptionPane.showMessageDialog(Management.this, "删除失败，请输入菜名","删除记录",1);
			}
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}finally{
			JdbcUtils.close(conn,rs);
		}
	}
	
		//添加数据
		public void addData(){
			System.out.println(123445566);
			Connection conn=null;
			ResultSet rs=null;
			try {
				conn=JdbcUtils.createConnection();
				String caiName=nameField.getText();
				String sql="select * from T_yuanliaocai where caiName=?";
				
				 rs=JdbcUtils.executeQuery(conn, sql, caiName);
				if(rs.next()){
					JOptionPane.showMessageDialog(Management.this, "该菜式已经存在，请详细查看后才操作","添加记录",1);
				}else{
			
					String sqlStr="insert into T_yuanliaocai(caiName,price,number) values(?,?,?)";
					String price=priceField.getText();
					
					if(caiName!=null&&price!=null&&!caiName.equals("")&&!price.equals("")){
					JdbcUtils.executeUpdate(conn, sqlStr, caiName,price,40);
					
					JOptionPane.showMessageDialog(Management.this, "添加成功","添加记录",1);
					
					//清空输入信息
					nameField.setText("");
					priceField.setText("");
					
					System.out.println("插入完成");
					//触发显示所有记录的按钮，显示更新后的数据
					queryData();
				}else{
					JOptionPane.showMessageDialog(Management.this,"添加失败，请输入菜名或者价格","添加记录",1);
				}
				}
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}finally{
				JdbcUtils.close(conn);
			}
		}

		/**
		 *改变原料 
		 */
		public void changeData(){
			Connection conn=null;
			ResultSet rs=null;
		try{
			conn=JdbcUtils.createConnection();
			String caiName=nameField.getText();
			String sql="select * from T_yuanliaocai where caiName=?";
			
			rs=JdbcUtils.executeQuery(conn, sql, caiName);
			if(!rs.next()){
				JOptionPane.showMessageDialog(Management.this, "用修改的菜式不存在，请详细查看后才操作","添加记录",1);
			}else{
				String sqlStr="update T_yuanliaocai set price=? where caiName=?";
				String price=priceField.getText();
				
				if(price!=null&&!price.equals("")){
				JdbcUtils.executeUpdate(conn, sqlStr, price,caiName);
				
				JOptionPane.showMessageDialog(Management.this, "修改成功","修改记录",1);
				
				//清空输入信息
				nameField.setText("");
				priceField.setText("");
				
				//触发显示所有记录的按钮，显示更新后的数据
				queryData();
			}else{
				JOptionPane.showMessageDialog(Management.this, "修改失败，请输入菜名","修改记录",1);
			}
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}finally{
			JdbcUtils.close(conn,rs);
		}
	}
}
