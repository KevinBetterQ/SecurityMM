package com.qwk.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.SignStyle;

import javax.swing.AbstractListModel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.qwk.security.*;

public class SwingWindow {

	private JTextArea writeText;//输入区文本
	private JTextArea showText;//显示区文本
	private JTextField signCrypt;//数字签名时自己设置的密码
	private JTextField MMcrypt;//加解密时自己设置的密码
	
	//下拉加密列表框组件
	private JComboBox<String> methods = new JComboBox<>(new MyComboBox());
	
	
	//下拉数字签名列表框组件
	private String[] signStrings = {"MD", "SHA", "MAC"};
	private JComboBox<String> signs = new JComboBox<>(signStrings);
	
	
	public static void main(String[] args) {
		SwingWindow gui = new SwingWindow();
		gui.go();

	}
	
	public void go() {
		JFrame frame = new JFrame("Java加解密");
		JPanel leftPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		
		Font bigFont1 = new Font("serif", Font.BOLD, 20);
		JLabel methLabelLabel = new JLabel("请选择加密/解密方式：");
		JLabel signLabel = new JLabel("请选择数字签名方式：");
		JLabel signMM = new JLabel("签名密钥:");
		signMM.setFont(bigFont1);
		JLabel signMM2 = new JLabel("密钥:");
		signMM2.setFont(bigFont1);
		methLabelLabel.setFont(bigFont1);
		signLabel.setFont(bigFont1);
		
		
		//按钮设计
		JButton btn_Encrypt = new JButton("    加密     ");
		JButton btn_Decrypt = new JButton("    解密     ");
		JButton btn_sign = new JButton("数字签名——>");
		Font bigFont2 = new Font("serif", Font.BOLD, 28);
		btn_Encrypt.setFont(bigFont2);
		btn_Decrypt.setFont(bigFont2);
		btn_sign.setFont(bigFont2);
		
		//按钮监听
		btn_Encrypt.addActionListener(new EncryptListener());
		btn_Decrypt.addActionListener(new DecryptListener());
		btn_sign.addActionListener(new SignListener());
		methods.setFont(bigFont1);
		
		//数字签名下拉框设置
		signs.setFont(bigFont1);
		signs.setSelectedItem(null);
		
		
		//文本区域
		writeText = new JTextArea(28,40);//初始化文本区域
		showText = new JTextArea(28,40);//初始化文本区域
		writeText.setLineWrap(true);//启动自动换行
		showText.setLineWrap(true);//启动自动换行
		signCrypt = new JTextField(8);
		signCrypt.setText("88888888");
		MMcrypt = new JTextField(8);
		MMcrypt.setText("88888888");
		
		
		//初始化滚动文本面板
		JScrollPane leftScroller = new JScrollPane(writeText);
		JScrollPane rightScroller = new JScrollPane(showText);
		leftScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//使用垂直滚动条
		leftScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);//不使用滚动条
		rightScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//使用垂直滚动条
		rightScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);//不使用滚动条
		
		//中间按钮面板设置
		//centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));//将布局管理器换掉
		centerPanel.add(methLabelLabel);
		centerPanel.add(methods);
		centerPanel.add(signMM2);
		centerPanel.add(MMcrypt);
		centerPanel.add(btn_Encrypt);
		centerPanel.add(btn_Decrypt);
		centerPanel.add(signLabel);
		centerPanel.add(signs);
		centerPanel.add(signMM);
		centerPanel.add(signCrypt);
		centerPanel.add(btn_sign);
		
		//面板
		leftPanel.add(leftScroller);
		rightPanel.add(rightScroller);
		
		//窗体
		frame.getContentPane().add(BorderLayout.WEST, leftPanel);
		frame.getContentPane().add(BorderLayout.EAST, rightPanel);
		frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
		frame.setSize(1200, 600);
		frame.setVisible(true);
	}
	
	//内部类，下拉列表
	class MyComboBox extends AbstractListModel<String> implements ComboBoxModel<String>{

		String selecteditem = null;
		String[] methodString = {"Base64", "DES", "AES", "PBE", "DH", "RSA"};
		
		@Override
		public String getElementAt(int index) {		//根据索引返回值
			return methodString[index];
		}

		@Override
		public int getSize() {					//返回下拉列表框中项目的数目
			return methodString.length;			
		}

		@Override
		public Object getSelectedItem() {		//获取下拉列表框中的项目
			return selecteditem;
		}

		@Override
		public void setSelectedItem(Object anItem) {	//设置下拉列表框中的项目
			selecteditem = (String)anItem;

		}
	}
	
	//加密 按钮监听
	class EncryptListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//获取出当前下拉框加密方法选择
			String methodItem;
			methodItem = (String) methods.getSelectedItem();
			if(methodItem == null) methodItem = "XXX";
			
			if(methodItem.equals("Base64")){        //调用Base64加密方法
				String in = writeText.getText();
				String out = QwkBase64.jdkBase64En(in);
				showText.setText(null);
				showText.append(out);
			}else if (methodItem.equals("DES")) {   //调用DES加密方法，密码只能为8位
				String in = writeText.getText();
				String mm = MMcrypt.getText();
				String out = QwkDES.encryptDES(in, mm); 
				showText.setText(null);
				showText.append(out);
			}else if (methodItem.equals("AES")) {//调用AES加密方式，密码只能为 16位
				String in = writeText.getText();
				String mm = MMcrypt.getText();
				int length = mm.length();
				if(length == 16){
					String out = QwkAES.encryptAES(in, mm);
					showText.setText(null);
					showText.append(out);
				}else{
					JOptionPane.showMessageDialog(null, "AES密钥只支持16位 ", "Sorry ", JOptionPane.ERROR_MESSAGE);
				}
			}else if (methodItem.equals("PBE")) {
				String in = writeText.getText();
				String mm = MMcrypt.getText();
				String out = QwkPBE.encryptPBE(in, mm);
				showText.setText(null);
				showText.append(out);
				
			}else if (methodItem.equals("DH")) {
				
				
			}else if (methodItem.equals("RSA")) {
				
				
			}
			
			else{
				showText.setText("");
				showText.append("对不起，暂不支持此加密方式");
			}

		}
	}
	
	//解密 按钮监听
	class DecryptListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//获取出当前解密方法下拉框选择
			String methodItem;
			methodItem = (String) methods.getSelectedItem();
			if(methodItem == null) methodItem = "XXX";
			
			if(methodItem.equals("Base64")){
				String in = writeText.getText();
				String out = QwkBase64.jdkBase64De(in);//调用Base64解密方法
				showText.setText(null);
				showText.append(out);
			}else if (methodItem.equals("DES")) {
				String in = writeText.getText();
				String mm = MMcrypt.getText();
				String out = QwkDES.dectyptDES(in, mm);
				showText.setText(null);
				showText.append(out);
			}else if (methodItem.equals("AES")) {
				String in = writeText.getText();
				String mm = MMcrypt.getText();
				int length = mm.length();
				if(length == 16){
					String out = QwkAES.dectyptAES(in, mm);
					showText.setText(null);
					showText.append(out);
				}else{
					JOptionPane.showMessageDialog(null, "AES密钥只支持16位 ", "Sorry ", JOptionPane.ERROR_MESSAGE);
				}
				
			}else if (methodItem.equals("PBE")) {
				String in = writeText.getText();
				String mm = MMcrypt.getText();
				String out = QwkPBE.dectyptPBE(in, mm);
				showText.setText(null);
				showText.append(out);
			}
			
			
			
			else{
				showText.setText("");
				showText.append("对不起，暂不支持此解密方式");
			}
			
		}
		
	}
	
	//数字签名按钮
	class SignListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//获取出当前选择签名方式
			String signItem;
			signItem = (String) signs.getSelectedItem();
			if(signItem == null) signItem = "XXX";
			
			if(signItem.equals("MD")){
				String in = writeText.getText();
				String out = QwkMD.jdkMD5(in);
				showText.setText(null);
				showText.append(out);
			}else if (signItem.equals("SHA")) {
				String in = writeText.getText();
				String out = QwkSHA.jdkSHA1(in);
				showText.setText(null);
				showText.append(out);
			}else if (signItem.equals("MAC")) {
				String in = writeText.getText();
				String out = QwkHmac.bcHmacMD5(in);
				showText.setText(null);
				showText.append(out);
			}
		}
		
	}
	
	
}
