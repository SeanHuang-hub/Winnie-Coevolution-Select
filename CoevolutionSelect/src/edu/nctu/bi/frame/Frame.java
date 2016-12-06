package edu.nctu.bi.frame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import edu.nctu.bi.process.Select;
import javax.swing.UIManager;
import javax.swing.JCheckBox;

public class Frame extends JFrame {
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	JLabel lblRange;
	JLabel lblNewLabel_1;
	
	String path;
	Select select = new Select();
	private JTextField textField_3;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBackground(Color.WHITE);
		textField.setEditable(false);
		textField.setBounds(109, 19, 328, 25);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("序列檔案 : ");
		label.setFont(new Font("�L�n������", Font.BOLD, 16));
		label.setBounds(10, 24, 87, 15);
		contentPane.add(label);
		
		JLabel lblNewLabel = new JLabel("選擇兩個 Index ");
		lblNewLabel.setFont(new Font("�L�n������", Font.PLAIN, 14));
		lblNewLabel.setBounds(31, 208, 113, 15);
		contentPane.add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setBounds(156, 205, 96, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(264, 205, 96, 21);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		lblRange = new JLabel("Range  : ");
		lblRange.setForeground(Color.RED);
		lblRange.setFont(new Font("�L�n������", Font.BOLD, 14));
		lblRange.setBounds(109, 238, 62, 20);
		contentPane.add(lblRange);
		lblRange.setVisible(false);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setFont(new Font("�L�n������", Font.BOLD, 14));
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setBounds(183, 238, 113, 20);
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setVisible(false);
		
		JCheckBox chckbxdash = new JCheckBox("不要印出雙dash");
		chckbxdash.setBounds(10, 150, 128, 23);
		contentPane.add(chckbxdash);
		
		JButton button = new JButton("\u8B80\u53D6\u6A94\u6848\u4F4D\u7F6E");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION)
				{ 
					File selectedFile = fileChooser.getSelectedFile();
					path = selectedFile.getAbsolutePath();
					textField.setText(path);
					
					int range = select.loadFileMethod(path);
					lblRange.setVisible(true);
					lblNewLabel_1.setText("1 - " + range);
					lblNewLabel_1.setVisible(true);
				} 
			}
		});
		button.setBackground(new Color(240, 240, 240));
		button.setFont(new Font("�L�n������", Font.PLAIN, 12));
		button.setBounds(319, 57, 105, 23);
		contentPane.add(button);
		
		JButton btnNewButton = new JButton("處理 ");
		btnNewButton.setFont(new Font("�L�n������", Font.PLAIN, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean noBothDash = chckbxdash.isSelected();
				select.selectFileMethod(textField_1.getText().toString(), textField_2.getText().toString(), noBothDash);
			}
		});
		btnNewButton.setBounds(319, 238, 105, 23);
		contentPane.add(btnNewButton);
		
		JLabel label_1 = new JLabel("座標檔案 : ");
		label_1.setFont(new Font("Dialog", Font.BOLD, 16));
		label_1.setBounds(10, 98, 87, 15);
		contentPane.add(label_1);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		textField_3.setBackground(Color.WHITE);
		textField_3.setBounds(109, 91, 328, 25);
		contentPane.add(textField_3);
		
		JLabel lblSuccess = new JLabel("Success");
		lblSuccess.setBounds(247, 154, 61, 16);
		lblSuccess.setVisible(false);
		contentPane.add(lblSuccess);
		
		JButton button_1 = new JButton("讀取檔案位置");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION)
				{ 
					File selectedFile = fileChooser.getSelectedFile();
					path = selectedFile.getAbsolutePath();
					textField_3.setText(path);
					
					select.loadIndexFileMethod(path);
				} 
			}
		});
		button_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		button_1.setBackground(UIManager.getColor("TabbedPane.selectedTabTitlePressedColor"));
		button_1.setBounds(319, 125, 105, 23);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("處理 ");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean noBothDash = chckbxdash.isSelected();
				select.selectIndexListMethod(noBothDash);
				lblSuccess.setVisible(true);
			}
		});
		button_2.setFont(new Font("Dialog", Font.PLAIN, 14));
		button_2.setBounds(319, 151, 105, 23);
		contentPane.add(button_2);
		
	}
}
