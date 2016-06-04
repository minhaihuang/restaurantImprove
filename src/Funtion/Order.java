package Funtion;

import java.awt.Dimension;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import Util.JdbcUtils;

public class Order {
	private Object data[][] = new Object[1024][4] ;
    private String str ="" ;
    private String name ;
    private int caiDanlength;
	public Order() {
		
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
		Vector<Object> rowData = new Vector<Object>() ;
		
		
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
		scrollPane.setPreferredSize(new Dimension(470, 170));
		
		
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
}
	
	
