package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TextPanel extends JPanel {

	public TextPanel(int type, String text) {
		setBackground(new Color(0,0,0,0));
		setOpaque(true);
		JLabel lbl = new JLabel(text);
		lbl.setPreferredSize(new Dimension(430, 13));
		lbl.setFont(new Font("", Font.BOLD, 14));
		lineOverRap(lbl);
		switch (type) {
		case 5: { // Èò»ö ±Û¾¾
			lbl.setForeground(Color.WHITE);
			break;
		}
		case 6: { // »¡°£ ±Û¾¾
			lbl.setForeground(Color.RED);
			break;
		}
		case 7: { // ¹àÀº ÇÏ´Ã»ö ±Û¾¾
			lbl.setForeground(Color.CYAN);
			break;
		}
		case 8: { // È¸»ö ±Û¾¾
			lbl.setForeground(Color.LIGHT_GRAY);
			break;
		}
		case 9: {
			setBackground(new Color(255,212,0));
			lbl.setForeground(Color.BLUE);
		}
		}
		this.add(lbl);
	}

	public void lineOverRap(JLabel lbl) {
		String text = "<html>";
		char[] arr = lbl.getText().toCharArray();
		if (arr.length >= 28) {
			for (int i = 0; i < 28; i++) {
				text += arr[i];
			}
			text += "<br>";
			for (int i = 28; i < arr.length; i++) {
				text += arr[i];
			}
			text += "</html>";
			lbl.setText(text);
			lbl.setPreferredSize(new Dimension(430, 37));
		}
	}
}
