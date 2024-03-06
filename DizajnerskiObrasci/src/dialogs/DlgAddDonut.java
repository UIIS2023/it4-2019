package dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;

import geometry.Donut;
import geometry.Point;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class DlgAddDonut extends JDialog {
	private JTextField txtRadius;
	private JTextField txtInnerRadius;
	
	private int newRadius;
	private int newInnerRadius;
	private JLabel lblRadius;
	private JLabel lblInnerRadius;
	private JButton btnOk;
	private JButton btnExit;

	public DlgAddDonut() {
		setResizable(false);
		setTitle("Sasa Ilic,IT4/2019");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 300, 210);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			JPanel pnlCenter = new JPanel();
			getContentPane().add(pnlCenter, BorderLayout.CENTER);
			{
				lblRadius = new JLabel("Radius:");
				lblRadius.setHorizontalAlignment(SwingConstants.CENTER);
			}
			{
				txtRadius = new JTextField();
				txtRadius.setColumns(10);
			}
			{
				lblInnerRadius = new JLabel("Inner radius:");
				lblInnerRadius.setHorizontalAlignment(SwingConstants.CENTER);
			}
			{
				txtInnerRadius = new JTextField();
				txtInnerRadius.setColumns(10);
			}
			{
				btnOk = new JButton("OK");
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
						
							newRadius = Integer.parseInt(txtRadius.getText());
							newInnerRadius = Integer.parseInt(txtInnerRadius.getText());

							if( newRadius < 1 || newInnerRadius < 1 || newInnerRadius >= newRadius) {
								JOptionPane.showMessageDialog(null, "You have entered incorrect informations!", "ERROR", JOptionPane.ERROR_MESSAGE);
								return;
							}
							dispose();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "You have entered incorrect informations!", "ERROR", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
			}
			{
				btnExit = new JButton("EXIT");
				btnExit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
			}
			GroupLayout gl_pnlCenter = new GroupLayout(pnlCenter);
			gl_pnlCenter.setHorizontalGroup(
				gl_pnlCenter.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_pnlCenter.createSequentialGroup()
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_pnlCenter.createSequentialGroup()
										.addGap(19)
										.addComponent(lblInnerRadius, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
									.addComponent(lblRadius, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
								.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING, false)
									.addComponent(txtRadius)
									.addComponent(txtInnerRadius, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)))
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addGap(48)
								.addComponent(btnOk)
								.addGap(77)
								.addComponent(btnExit)))
						.addContainerGap())
			);
			gl_pnlCenter.setVerticalGroup(
				gl_pnlCenter.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlCenter.createSequentialGroup()
						.addGap(24)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblRadius, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblInnerRadius, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtInnerRadius, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addGap(30))
			);
			pnlCenter.setLayout(gl_pnlCenter);
		}
	}
	
	public int getNewRadius() {
		return newRadius;
	}
	public int getNewInnerRadius() {
		return newInnerRadius;
	}

}