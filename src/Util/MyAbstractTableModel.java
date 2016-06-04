package Util;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class MyAbstractTableModel extends AbstractTableModel {
	    /** 
	     *  
	     */  
	    private static final long serialVersionUID = 1L;  
	      
	    private Vector<Object> rowData;  
	    
	    private String head[] ;
	  
	    public MyAbstractTableModel(String[] head, Vector<Object> rowData) {
			this.head = head ;
			this.rowData  =rowData ;
		}

		public Vector<Object> getRowData() {  
	        return rowData;  
	    }  
	  
	    public void setRowData(Vector<Object> rowData) {  
	        this.rowData = rowData;  
	    }  
	  
	    //获得列数
	    public int getColumnCount() {  
	        return head.length;  
	    }  
	    
	    
	    //设置表头
	    public String getColumnName(int column) {  
	        return head[column];  
	    }  
	    
	    //获得行数
	    public int getRowCount() {  
	        return rowData.size();  
	    }  
	  
	    
	   // 获得表格的单元格的数据 
	    public Object getValueAt(int row, int column) {  
	        Object[] obj = (Object[]) rowData.get(row);  
	        return obj[column];  
	    }  
	  
	    @SuppressWarnings("rawtypes")  
	    public Class getColumnClass(int column) { 
	        return (getValueAt(0, column).getClass());  
	    }  
	    
	    
	    //设置数值
	    public void setValueAt(Object value, int row, int column) {  
	        Object[] obj = (Object[]) rowData.get(row);  
	        obj[column] = value;  
	    }  
	  
	    public boolean isCellEditable(int row, int column) {  
	        return (column == 0);  
	    }  
	}  

