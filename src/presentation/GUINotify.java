package presentation;

import javax.swing.JOptionPane;

public class GUINotify {
	
	public static void errorData(String data) {
	    JOptionPane.showMessageDialog(null, data, "Advertencia", JOptionPane.WARNING_MESSAGE);
	}
	
	public static void formData(String data) {
	    JOptionPane.showMessageDialog(null, data, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void successfullData(String data) {
	    JOptionPane.showMessageDialog(null, data, "Exito", JOptionPane.INFORMATION_MESSAGE);
	}

	public static boolean validateAnswer(String message) {
		Object[] options = 
			{"Si","No","Cancelar"};
		int confirmOption = 
				JOptionPane.showOptionDialog(
						null, message,
						"", JOptionPane.DEFAULT_OPTION
						,JOptionPane.PLAIN_MESSAGE, null ,options,options[0]);
		return (confirmOption==0) ? true:false;		
	}
	
}
