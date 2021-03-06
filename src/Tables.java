import java.awt.Component;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class Tables {

	private Warehouse w;
	protected Object[] column = {};
	protected Object[][] data = {};
	JTable table = new JTable();
	JScrollPane jpane = new JScrollPane();
	private TableModel model;

	public Tables(){
	}

	//creates a scrollPane to hold the JTable
	public JScrollPane createTable() {
		table = new JTable(data, column) {
			public Component prepareRenderer(TableCellRenderer r, int rIndex, int cIndex) {
				if (cIndex == 1) {
					setFont(new Font("Arial", Font.BOLD, 12));
				} else {
					setFont(new Font("Arial", Font.ITALIC, 12));
				}
				return super.prepareRenderer(r, rIndex, cIndex);
			}
		};
        //checks if a chnage has occured in the table when the user selects the 'Enter'
		//key on a field and updates the JTable and the orderdetails table in my database
		table.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				int col = e.getColumn();
				int rowID = table.getSelectedRow();
				Object value = getValueAt(rowID, col);
				System.out.println("value changed to " +value);
				Warehouse.modifyOrderDetails(value, rowID, col);
			}
		});

		jpane = new JScrollPane(table);
		jpane.setSize(400, 200);
		jpane.revalidate();
		return jpane;
	}

	public JScrollPane getJPane() {
		return jpane;
	}

	public JTable getJTable() {
		return table;
	}

	public int getColumnCount() {
		return column.length;
	}

	public int getRowCount() {
		return data.length;
	}

	public Object getColumnName(int col) {
		return column[col];
	}

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}


}
