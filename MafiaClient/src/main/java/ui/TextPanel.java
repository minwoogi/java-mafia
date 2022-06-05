package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class TextPanel extends JPanel{
	
	public TextPanel(int type, int size, String text) {
		setBackground(new Color(0,0,0,0));
		setOpaque(true);
		JLabel lbl = new JLabel(text);
		lbl.setPreferredSize(new Dimension(430,20));
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
			lbl.setForeground(Color.GRAY);
			break;
		}
		}
		lbl.setFont(new Font("", Font.BOLD, size));
		this.add(lbl);
	}
	
	public void setLineWarp(JLabel lbl) {
		
		
		
	}
}
