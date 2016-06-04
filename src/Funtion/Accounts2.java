package Funtion;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

import Frame.MainFrame;

public class Accounts2 implements ActionListener {
	private int id ;
	private JPanel panel1,panel2 ;
	private JLabel label ;
	private JButton button ;
	JScrollPane scrollPane ;
	Util.MyAbstractTableModel model ;
	private JTable table ;
	private JTextArea area;
	private Order order;
	private Vector<Object> rowData;
	public JPanel panel = null ;
	public Accounts2(int id) {
		this.id = id;
	}
	
	public Accounts2(JTextArea area) {
		this.area=area;
		panel = new JPanel() ;
		String msg=area.getText();
		funtion() ;
		panel.add(panel1) ;
		area  = new JTextArea(msg) ;
	    scrollPane = new JScrollPane(area) ;
	    scrollPane.setBounds(120, 410, 300, 50) ;
	    //scrollPane.setVisible(false) ;
	    scrollPane.setPreferredSize(new Dimension(550, 100));
	    panel.add(scrollPane) ;
	}

	public Accounts2() {
		
	}
	
	//获取消费总金额
	public int getMoney(JTextArea area){
		area=this.area;
		
		//获取消费信息
		String msg=area.getText();
		int index=msg.indexOf("：");
		String temp=msg.substring(index+2);//切割的信息要从：的索引加上2开始。因为有回车符和换行符
		String[] data=temp.split("\\s+");//切割信息
		
		int count=0;
		for(int i=0;i<data.length;){
			int m=Integer.parseInt(data[i+2]);//转为Integer类型的数字
			count+=m;
			i=i+3;
		}
		
		return count;
	}
	public JPanel getPanel(){
		return this.panel;
	}
	public JPanel getRecipes() {

		panel = new JPanel() ;
		funtion() ;
		panel.add(panel1) ;
		area  = new JTextArea("哈哈") ;
	    scrollPane = new JScrollPane(area) ;
	    scrollPane.setBounds(120, 410, 300, 50) ;
	    //scrollPane.setVisible(false) ;
	    scrollPane.setPreferredSize(new Dimension(550, 100));
	    panel.add(scrollPane) ;
	    
		return panel;
	}
	
	private void getText(JTextArea area){
		
	}
	private void funtion(){
		panel1 = new JPanel(new GridLayout(1, 3)) ;
		panel1.setPreferredSize(new Dimension(550, 30)) ;
		
		label = new JLabel("本次账单",JLabel.LEFT) ;
		
		//button = new JButton("本次账单") ;
		panel1.add(label) ;
		
		
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()  ==button){
			
		}
		
	}
	
}