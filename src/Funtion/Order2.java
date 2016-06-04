package Funtion;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import Frame.MainFrame2;
import Util.JdbcUtils;


public class Order2 {
	private Object data[][] = new Object[1024][4] ;
    private String str ="" ;
    private String name ;
    private int caiDanlength;
    
 
    public JTextField nameField;
  	
    public JTextField priceField;
    
    static AbstractTableModel tm;
  	
    public JButton insert=new JButton("增加菜式");
    public JButton delete=new JButton("删除菜式");
    public JButton update=new JButton("改变菜式");
    public JButton queryByName=new JButton("查询菜式");
    public JButton queryAll=new JButton("全部记录");
  	
    static Vector<Object> rowData ;
	public Order2( JTextField nameField,JTextField priceField) {
		this.nameField=nameField;
		this.priceField=priceField;
	}
    
	public Order2(){
		
	}
	/**
	 * 获得table的JScrollPane
	 * @param table
	 * @return
	 */
	public  JScrollPane getRecipes(JTable table){
		JScrollPane scrollPane = null;
		String[] head = {"选择","id", "菜名", "价格"};
		Util.MyAbstractTableModel model ;
		 rowData = new Vector<Object>() ;
		
		
		model = new Util.MyAbstractTableModel(head, rowData) ;
		table = new JTable(model) ;
		
		//固定第一列的宽度
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(0).setMaxWidth(30);
		table.getColumnModel().getColumn(0).setMinWidth(30);
		
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setMaxWidth(50);
		table.getColumnModel().getColumn(1).setMinWidth(50);
		

		//往数据库查询菜单，添加到主面板中
		String sql="select *from t_chengpincais";//查询语句
		ResultSet rs=null;
		//获取查询结果
		try {
			rs=JdbcUtils.executeQuery(sql);
			
			int i=0;
			while(rs.next()){
				data[i][0] = Boolean.FALSE  ;
				data[i][1] = i+1 ;
				data[i][2] = rs.getString("caiName") ;
				data[i][3] = rs.getString("price") ;
				rowData.add(data[i]) ;
				i++;
			}
			
			this.caiDanlength=i;//获得当前的菜单长度，即共有几个菜
		} catch (SQLException e) {
			System.out.println("往主面板加入菜单时发生了错误"+e.getMessage());
		}finally{
			JdbcUtils.close(rs);
		}
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, r);
		table.setDefaultRenderer(Integer.class, r);
		
		scrollPane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(590, 170));
		
		
		return scrollPane;
		
	}
	
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public String[] isSelect() {
		str = "" ;
		name  = "谢谢惠顾，你点了如下的菜：\n" ;
		for (int i = 0; i <caiDanlength; i++) {
			if ((Boolean) data[i][0]) {
				str = str + data[i][1] + ";";
				name = name + data[i][1]+"\t"+data[i][2]+"\t"+data[i][3]+"\n" ;
			}
		}
		return new String[]{str,name};
	}
	
	
	private class TableListener implements ListSelectionListener{
	    JTable table ;

	     TableListener(JTable table) {
           this.table = table;
       }
	 
		@Override
		public void valueChanged(ListSelectionEvent e) {
			 if (e.getSource() == table.getColumnModel().getSelectionModel()
	                   && table.getColumnSelectionAllowed() ){
	                int firstRow = e.getFirstIndex();
	                int lastRow = e.getLastIndex();
	                
	                System.out.println(798797);
			 }
		}
	}
	
	//添加数据
		public void insertData(ActionEvent e){
			Connection conn=null;
			System.out.println(123);
			try {
				conn=JdbcUtils.createConnection();
				String caiName=nameField.getText();
				String sql="select * from T_chengpincais where caiName=?";
				
				ResultSet rs=JdbcUtils.executeQuery(conn, sql, caiName);
				if(rs.next()){
					//JOptionPane.showMessageDialog(MisforRestaurantFrame.this, "该菜式已经存在，请详细查看后才操作","添加记录",1);
				}else{
			
					String sqlStr="insert into T_chengpincais(caiName,price) values(?,?)";
					String price=priceField.getText();
					
					if(caiName!=null&&price!=null&&!caiName.equals("")&&!price.equals("")){
					JdbcUtils.executeUpdate(conn, sqlStr, caiName,price);
					
					//JOptionPane.showMessageDialog(Order2.this, "添加成功","添加记录",1);
					
					//清空输入信息
//					nameField.setText("");
//					priceField.setText("");
					
					//触发显示所有记录的按钮，显示更新后的数据
					new MainFrame2();
				}else{
					//JOptionPane.showMessageDialog(Order2.this,"添加失败，请输入菜名或者价格","添加记录",1);
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
			Connection conn=null;
			ResultSet rs=null;
			try{
				conn=JdbcUtils.createConnection();
				String caiName=nameField.getText();
				String sql="select * from T_chengpincais where caiName=?";
				
				 rs=JdbcUtils.executeQuery(conn, sql, caiName);
				if(!rs.next()){
					//JOptionPane.showMessageDialog(MisforRestaurantFrame.this, "该菜式不存在","查询记录",1);
				}else{
					if(caiName!=null&&!caiName.equals("")){
					String sqlStr="delete from T_chengpincais where caiName=?";
					JdbcUtils.executeUpdate(conn, sqlStr, caiName);
					
					//JOptionPane.showMessageDialog(MisforRestaurantFrame.this, "删除菜式成功","删除记录",1);
					
					nameField.setText("");
					priceField.setText("");
					
					//queryAllData(e);
					new MainFrame2();
				}else{
					//JOptionPane.showMessageDialog(MisforRestaurantFrame.this, "删除失败，请输入菜名","删除记录",1);
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
			Connection conn=null;
			ResultSet rs=null;
			try{
			conn=JdbcUtils.createConnection();
			String caiName=nameField.getText();
			String sql="select * from T_chengpincais where caiName=?";
			
			 rs=JdbcUtils.executeQuery(conn, sql, caiName);
			if(!rs.next()){
				//JOptionPane.showMessageDialog(MisforRestaurantFrame.this, "用修改的菜式不存在，请详细查看后才操作","添加记录",1);
			}else{
				String sqlStr="update T_chengpincais set price=? where caiName=?";
				String price=priceField.getText();
				
				if(price!=null&&!price.equals("")){
				JdbcUtils.executeUpdate(conn, sqlStr, price,caiName);
				
				//JOptionPane.showMessageDialog(MisforRestaurantFrame.this, "修改成功","修改记录",1);
				
				//清空输入信息
				nameField.setText("");
				priceField.setText("");
				
				//触发显示所有记录的按钮，显示更新后的数据
				new MainFrame2();
			}else{
				//JOptionPane.showMessageDialog(MisforRestaurantFrame.this, "修改失败，请输入菜名","修改记录",1);
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
			Connection conn=null;
			ResultSet rs=null;
			try{
				conn=JdbcUtils.createConnection();
				String caiName=nameField.getText();
				
				if(caiName==null||caiName.equals("")){
					//JOptionPane.showMessageDialog(MisforRestaurantFrame.this, "查询失败，请输入菜名","查询记录",1);
					return;
				}
				String sql="select * from T_chengpincais where caiName=?";
				
				rs=JdbcUtils.executeQuery(conn, sql, caiName);
				if(!rs.next()){
					//JOptionPane.showMessageDialog(MisforRestaurantFrame.this, "该菜式不存在","查询记录",1);
				}else{
					//vector.removeAllElements();
					//tm.fireTableStructureChanged();
					rs.previous();
					rowData=new Vector<Object>();
					int i=0;
					while(rs.next()){
						data[i][0] = Boolean.FALSE  ;
						data[i][1] = i+1 ;
						data[i][2] = rs.getString("caiName") ;
						data[i][3] = rs.getString("price") ;
						rowData.add(data[i]) ;
						i++;
					}
//					int i=0;
//					while(rs.next()){
//						Vector newVector=new Vector();
//						//加入数据
//						data[i][0] = Boolean.FALSE  ;
//						data[i][1] = i+1 ;
//						newVector.addElement(rs.getString("caiName"));
//						newVector.addElement(rs.getString("price")+"");
//						rowData.addElement(newVector);
//					}
//					
					//清空输入信息
					nameField.setText("");
					priceField.setText("");
					//new MainFrame2(1,"东2-1");
					
				}
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}finally{
				JdbcUtils.close(conn,rs);
			}
		}
		
		//查询所有菜式
		public void queryAllData(ActionEvent e){
			Connection conn=null;
			ResultSet rs=null;
			try {
				conn=JdbcUtils.createConnection();
				String sql="select *from T_chengpincais";
				
				 rs=JdbcUtils.executeQuery(conn, sql);
				
				//清空列表
				rowData.removeAllElements();
				//tm.fireTableStructureChanged();
				
				int i=0;
				while(rs.next()){
					Vector newVector=new Vector();
					//加入数据
					data[i][0] = Boolean.FALSE  ;
					data[i][1] = i+1 ;
					newVector.addElement(rs.getString("caiName"));
					newVector.addElement(rs.getString("price")+"");
					rowData.addElement(newVector);
				}
				
				//清空输入信息
				nameField.setText("");
				priceField.setText("");
				
				new MainFrame2();
				
				//tm.fireTableStructureChanged();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}finally{
				JdbcUtils.close(conn,rs);
				
			}
		}
}
	
	
