package Funtion;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import Util.JdbcUtils;

public class Accounts implements ActionListener {
	private Object data[][] = new Object[15][3] ;
	private int id ;
	private JPanel panel1,panel2 ;
	private JLabel label ;
	private JButton button ;
	JScrollPane scrollPane ;
	Util.MyAbstractTableModel model ;
	private JTable table ;
	
	
	public Accounts(int id) {
		this.id = id;
	}


	public JPanel getRecipes() {
		JPanel panel = null ;
		
		String[] head = {"餐桌id", "餐桌名", "餐桌状态" };
		Vector<Object> rowData = new Vector<Object>();

		model = new Util.MyAbstractTableModel(head, rowData);
		table = new JTable(model);

		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(0).setMaxWidth(50);
		table.getColumnModel().getColumn(0).setMinWidth(50);

        
		getTableData(rowData) ;
		
		
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, r);
		table.setDefaultRenderer(Integer.class, r);

		scrollPane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		scrollPane.setPreferredSize(new Dimension(550, 200));
		
		funtion() ;
		
		panel = new JPanel() ;
	    panel.setBounds(0, 0, 590, 500) ;
	    panel.add(panel1) ;
	    panel.add(scrollPane) ;
		
		return panel;
	}
	
	private void funtion(){
		panel1 = new JPanel(new GridLayout(1, 3)) ;
		panel1.setPreferredSize(new Dimension(550, 30)) ;
		
		label = new JLabel("本次账单：",JLabel.LEFT) ;
		
		//button = new JButton("本次账单") ;
		panel1.add(label) ;
		
		
	}
	
	private void getTableData(Vector<Object> rowData){
		ResultSet rs=null;
		String sql="select *from T_tables";
		
		try {
			rs=JdbcUtils.executeQuery(sql);
			int i=0;
			while(rs.next()){
				data[i][0]=i;
				data[i][1]=rs.getObject("tableName");
			
				int temp=rs.getInt("isNull");
				if(temp==0){
					//使用中
					data[i][2]="使用中";
				}else if(temp==1){
					data[i][2]="虚位待客";
				}
				rowData.add(data[i]);
				i++;
			}
			
		} catch (SQLException e) {
			System.out.println("导入餐桌失败");
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(rs);
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()  ==button){
			
		}
		
	}
	
}