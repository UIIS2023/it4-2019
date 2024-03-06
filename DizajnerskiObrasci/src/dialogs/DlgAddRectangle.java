package dialogs;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class DlgAddRectangle extends JDialog {
	private JTextField txtHeight;
	private JTextField txtWidth;
	

	private int newHeight;
	private int newWIdth;

	private JLabel lblHeight;
	private JLabel lblWidth;
	private JButton btnOk;
	private JButton btnExit;

	public DlgAddRectangle() {
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
				lblHeight = new JLabel("Height");
				lblHeight.setHorizontalAlignment(SwingConstants.CENTER);
			}
			{
				txtHeight = new JTextField();
				txtHeight.setColumns(10);
			}
			{
				lblWidth = new JLabel("Width");
				lblWidth.setHorizontalAlignment(SwingConstants.CENTER);
			}
			{
				txtWidth = new JTextField();
				txtWidth.setColumns(10);
			}
			{
				btnOk = new JButton("OK");
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
							
							newHeight = Integer.parseInt(txtHeight.getText());
							newWIdth = Integer.parseInt(txtWidth.getText());
						

							if(newHeight < 1 || newWIdth < 1) {
								JOptionPane.showMessageDialog(null, "You have entered incorrect informations!", "Greska!", JOptionPane.ERROR_MESSAGE);
								return;
							}
							dispose();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "You have entered incorrect informations!", "Greska!", JOptionPane.ERROR_MESSAGE);
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
				gl_pnlCenter.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlCenter.createSequentialGroup()
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnOk))
							.addGroup(Alignment.LEADING, gl_pnlCenter.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlCenter.createSequentialGroup()
									.addContainerGap()
									.addComponent(lblHeight, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
								.addGroup(Alignment.TRAILING, gl_pnlCenter.createSequentialGroup()
									.addContainerGap()
									.addComponent(lblWidth, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
									.addComponent(txtHeight, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtWidth, 0, 0, Short.MAX_VALUE))
								.addContainerGap())
							.addGroup(Alignment.TRAILING, gl_pnlCenter.createSequentialGroup()
								.addGap(51)
								.addComponent(btnExit)
								.addGap(72))))
			);
			gl_pnlCenter.setVerticalGroup(
				gl_pnlCenter.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlCenter.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblHeight, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtHeight, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblWidth, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtWidth, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnExit)
							.addComponent(btnOk))
						.addGap(26))
			);
			pnlCenter.setLayout(gl_pnlCenter);
		}
	}
	
	public int getNewHeight() {
		return newHeight;
	}

	public int getNewWIdth() {
		return newWIdth;
	}

	
}