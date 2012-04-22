package us.tehonlygod.rockcrabs;

import java.awt.BorderLayout;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JCheckBox;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;



public class FlawlessRockCrabGUI extends JFrame {

	private JPanel contentPane;
	private JCheckBox chckbxFood;
	private JCheckBox chckbxLoot;
	private JComboBox comboBox;
	private JButton btnNewButton;
	private boolean START = false;
	

	public FlawlessRockCrabGUI() {
		setTitle("Flawless Rock Crabs");
		setVisible(true);
		this.setBounds(100, 100, 377, 135);
		this.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		BufferedImage img = null;
		try {
			img = ImageIO.read(new URL("http://img99.imageshack.us/img99/6393/finalbotlogo1.png"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblNewLabel = new JLabel("");
		try{
		lblNewLabel.setIcon(new ImageIcon(img));
		} catch (Exception e){
			
		}
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.gridheight = 4;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel label = new JLabel("");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 5;
		gbc_label.gridy = 1;
		panel.add(label, gbc_label);
		
		chckbxFood = new JCheckBox("Food     ");
		chckbxFood.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxFood.setBackground(Color.DARK_GRAY);
		chckbxFood.setForeground(new Color(0, 255, 255));
		GridBagConstraints gbc_chckbxFood = new GridBagConstraints();
		gbc_chckbxFood.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxFood.gridx = 2;
		gbc_chckbxFood.gridy = 2;
		panel.add(chckbxFood, gbc_chckbxFood);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "Trout", "Salmon", "Tuna", "Lobster", "Swordfish", "Monkfish", "Shark"}));
		comboBox.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 2;
		panel.add(comboBox, gbc_comboBox);
		
		chckbxLoot = new JCheckBox("Kill Steal");
		chckbxLoot.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxLoot.setBackground(Color.DARK_GRAY);
		chckbxLoot.setForeground(new Color(0, 255, 255));
		GridBagConstraints gbc_chckbxLoot = new GridBagConstraints();
		gbc_chckbxLoot.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxLoot.gridx = 2;
		gbc_chckbxLoot.gridy = 3;
		panel.add(chckbxLoot, gbc_chckbxLoot);
		
		btnNewButton = new JButton("Start");
		btnNewButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				START = true;
				setVisible(false);
				
			}
			
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.BELOW_BASELINE;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 3;
		gbc_btnNewButton.gridy = 3;
		panel.add(btnNewButton, gbc_btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 4;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
	}
	
	public boolean getChckbxFood(){
		return chckbxFood.isSelected();
	}

	public boolean getChckbxLoot(){
		return chckbxLoot.isSelected();
	}
	
	public String getComboBox(){
		return (String)comboBox.getSelectedItem();
	}
	public boolean getStart(){
		return START;
	}
}
