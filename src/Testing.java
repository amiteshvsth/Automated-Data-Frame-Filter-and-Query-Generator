import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;

public class Testing extends JFrame implements ActionListener{
    
	JTable table;
	JButton b1;
	public static String heading[] =  {"abcsd"};
	String header;

    public Testing() {
    	super("CSV File Data in the form of Table");

        b1 = new JButton("Read File");
        add(b1, BorderLayout.SOUTH);
     
        table = new JTable(new MyModel());
        table.setPreferredScrollableViewportSize(new Dimension(700, 70));
        table.setFillsViewportHeight(true);
        
        add(table, BorderLayout.CENTER);
        
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        
        b1.addActionListener(this);

        setSize(700,600);
        setVisible(true);
        setLocation(600,150);
        
    }
    
    public void getFile(File file){
    
    	DefaultTableModel model = null;

    	try{

    			BufferedReader txtReader = new BufferedReader(new FileReader(file));
        		header = txtReader.readLine();
    			
        		System.out.println(header);
        		heading = header.split("\\;");
     
        		
        		for(int i = 0 ; i < heading.length ; i++){
        			System.out.println(heading[i]);
        		}
    			
    	}catch(Exception e){
    			e.printStackTrace();
    	}
    }
    
    public void actionPerformed(ActionEvent ae){

    	File file = null;
    	
		JFileChooser chooser = new JFileChooser("C:/Users/768970/Desktop/Databases");
		chooser.setAcceptAllFileFilterUsed(false); 
		FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .csv files", "csv"); 
	    chooser.addChoosableFileFilter(restrict);

	    int result = chooser.showOpenDialog(table);
		if(result == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
			getFile(file);
		}
	    
	    table.setBorder(new EmptyBorder(5, 5, 5, 5));
        CSVFile Rd = new CSVFile();
        MyModel NewModel = new MyModel();
        table.setModel(NewModel);

	    
	    ArrayList<String[]> Rs2 = Rd.ReadCSVfile(file);
        NewModel.AddCSVData(Rs2);
        System.out.println("Rows: " + NewModel.getRowCount());
        System.out.println("Cols: " + NewModel.getColumnCount());
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
	}			

    // Method for reading CSV file
    public class CSVFile {
        private final ArrayList<String[]> Rs = new ArrayList<String[]>();
        private String[] OneRow;

        public ArrayList<String[]> ReadCSVfile(File DataFile) {
            try {
                BufferedReader brd = new BufferedReader(new FileReader(DataFile));
                brd.readLine();
                while (brd.ready()) {
                    String st = brd.readLine();
                    OneRow = st.split("\\" + ";");  //s
                    Rs.add(OneRow);
                    
                }
            }
            catch (Exception e) {
                String errmsg = e.getMessage();
                System.out.println("File not found:" + errmsg);
            }
            return Rs;
        }
    }


    class MyModel extends AbstractTableModel {
    	
    	private ArrayList<String[]> Data = new ArrayList<String[]>();


        public void AddCSVData(ArrayList<String[]> DataIn) {
            this.Data = DataIn;
            this.fireTableDataChanged();
        }

        @Override
        public int getColumnCount() {
            return heading.length;
        }

        @Override
        public int getRowCount() {
            return Data.size();
        }

        @Override
        public String getColumnName(int col) {
            return heading[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            return Data.get(row)[col];
        }
    }

    public static void main(String[] args) {
        new Testing();
    }
}



