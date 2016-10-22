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

	private JTextArea writeText;//�������ı�
	private JTextArea showText;//��ʾ���ı�
	private JTextField signCrypt;//����ǩ��ʱ�Լ����õ�����
	private JTextField MMcrypt;//�ӽ���ʱ�Լ����õ�����
	
	//���������б�����
	private JComboBox<String> methods = new JComboBox<>(new MyComboBox());
	
	
	//��������ǩ���б�����
	private String[] signStrings = {"MD", "SHA", "MAC"};
	private JComboBox<String> signs = new JComboBox<>(signStrings);
	
	
	public static void main(String[] args) {
		SwingWindow gui = new SwingWindow();
		gui.go();

	}
	
	public void go() {
		JFrame frame = new JFrame("Java�ӽ���");
		JPanel leftPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		
		Font bigFont1 = new Font("serif", Font.BOLD, 20);
		JLabel methLabelLabel = new JLabel("��ѡ�����/���ܷ�ʽ��");
		JLabel signLabel = new JLabel("��ѡ������ǩ����ʽ��");
		JLabel signMM = new JLabel("ǩ����Կ:");
		signMM.setFont(bigFont1);
		JLabel signMM2 = new JLabel("��Կ:");
		signMM2.setFont(bigFont1);
		methLabelLabel.setFont(bigFont1);
		signLabel.setFont(bigFont1);
		
		
		//��ť���
		JButton btn_Encrypt = new JButton("    ����     ");
		JButton btn_Decrypt = new JButton("    ����     ");
		JButton btn_sign = new JButton("����ǩ������>");
		Font bigFont2 = new Font("serif", Font.BOLD, 28);
		btn_Encrypt.setFont(bigFont2);
		btn_Decrypt.setFont(bigFont2);
		btn_sign.setFont(bigFont2);
		
		//��ť����
		btn_Encrypt.addActionListener(new EncryptListener());
		btn_Decrypt.addActionListener(new DecryptListener());
		btn_sign.addActionListener(new SignListener());
		methods.setFont(bigFont1);
		
		//����ǩ������������
		signs.setFont(bigFont1);
		signs.setSelectedItem(null);
		
		
		//�ı�����
		writeText = new JTextArea(28,40);//��ʼ���ı�����
		showText = new JTextArea(28,40);//��ʼ���ı�����
		writeText.setLineWrap(true);//�����Զ�����
		showText.setLineWrap(true);//�����Զ�����
		signCrypt = new JTextField(8);
		signCrypt.setText("88888888");
		MMcrypt = new JTextField(8);
		MMcrypt.setText("88888888");
		
		
		//��ʼ�������ı����
		JScrollPane leftScroller = new JScrollPane(writeText);
		JScrollPane rightScroller = new JScrollPane(showText);
		leftScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//ʹ�ô�ֱ������
		leftScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);//��ʹ�ù�����
		rightScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//ʹ�ô�ֱ������
		rightScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);//��ʹ�ù�����
		
		//�м䰴ť�������
		//centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));//�����ֹ���������
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
		
		//���
		leftPanel.add(leftScroller);
		rightPanel.add(rightScroller);
		
		//����
		frame.getContentPane().add(BorderLayout.WEST, leftPanel);
		frame.getContentPane().add(BorderLayout.EAST, rightPanel);
		frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
		frame.setSize(1200, 600);
		frame.setVisible(true);
	}
	
	//�ڲ��࣬�����б�
	class MyComboBox extends AbstractListModel<String> implements ComboBoxModel<String>{

		String selecteditem = null;
		String[] methodString = {"Base64", "DES", "AES", "PBE", "DH", "RSA"};
		
		@Override
		public String getElementAt(int index) {		//������������ֵ
			return methodString[index];
		}

		@Override
		public int getSize() {					//���������б������Ŀ����Ŀ
			return methodString.length;			
		}

		@Override
		public Object getSelectedItem() {		//��ȡ�����б���е���Ŀ
			return selecteditem;
		}

		@Override
		public void setSelectedItem(Object anItem) {	//���������б���е���Ŀ
			selecteditem = (String)anItem;

		}
	}
	
	//���� ��ť����
	class EncryptListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//��ȡ����ǰ��������ܷ���ѡ��
			String methodItem;
			methodItem = (String) methods.getSelectedItem();
			if(methodItem == null) methodItem = "XXX";
			
			if(methodItem.equals("Base64")){        //����Base64���ܷ���
				String in = writeText.getText();
				String out = QwkBase64.jdkBase64En(in);
				showText.setText(null);
				showText.append(out);
			}else if (methodItem.equals("DES")) {   //����DES���ܷ���������ֻ��Ϊ8λ
				String in = writeText.getText();
				String mm = MMcrypt.getText();
				String out = QwkDES.encryptDES(in, mm); 
				showText.setText(null);
				showText.append(out);
			}else if (methodItem.equals("AES")) {//����AES���ܷ�ʽ������ֻ��Ϊ 16λ
				String in = writeText.getText();
				String mm = MMcrypt.getText();
				int length = mm.length();
				if(length == 16){
					String out = QwkAES.encryptAES(in, mm);
					showText.setText(null);
					showText.append(out);
				}else{
					JOptionPane.showMessageDialog(null, "AES��Կֻ֧��16λ ", "Sorry ", JOptionPane.ERROR_MESSAGE);
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
				showText.append("�Բ����ݲ�֧�ִ˼��ܷ�ʽ");
			}

		}
	}
	
	//���� ��ť����
	class DecryptListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//��ȡ����ǰ���ܷ���������ѡ��
			String methodItem;
			methodItem = (String) methods.getSelectedItem();
			if(methodItem == null) methodItem = "XXX";
			
			if(methodItem.equals("Base64")){
				String in = writeText.getText();
				String out = QwkBase64.jdkBase64De(in);//����Base64���ܷ���
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
					JOptionPane.showMessageDialog(null, "AES��Կֻ֧��16λ ", "Sorry ", JOptionPane.ERROR_MESSAGE);
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
				showText.append("�Բ����ݲ�֧�ִ˽��ܷ�ʽ");
			}
			
		}
		
	}
	
	//����ǩ����ť
	class SignListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//��ȡ����ǰѡ��ǩ����ʽ
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
