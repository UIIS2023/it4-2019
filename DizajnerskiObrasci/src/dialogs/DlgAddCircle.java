package dialogs;

import java.awt.BorderLayout;
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
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

public class DlgAddCircle extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JTextField txtRadius;
	private JLabel lblRadius;
	private JButton btnOk;
	private JButton btnExit;
	private int newRadius;


	public DlgAddCircle() {
		setResizable(false);
		setTitle("Sasa Ilic,IT4/2019");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 300, 180);
		setResizable(false);
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
				btnOk = new JButton("OK");
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
							
							 newRadius = Integer.parseInt(txtRadius.getText());

							if( newRadius < 1) {
								JOptionPane.showMessageDialog(null, "You have entered incorrect informations!", "ERROR!", JOptionPane.ERROR_MESSAGE);
								return;
							}
							
							
							
							dispose();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "You have entered incorrect informations!", "ERROR!", JOptionPane.ERROR_MESSAGE);
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
								.addContainerGap()
								.addComponent(lblRadius, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addGap(53)
								.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(38, Short.MAX_VALUE))
			);
			gl_pnlCenter.setVerticalGroup(
				gl_pnlCenter.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlCenter.createSequentialGroup()
						.addGap(31)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblRadius, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addGap(28)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
						.addContainerGap())
			);
			pnlCenter.setLayout(gl_pnlCenter);
		}
	}


	public int getNewRadius() {
		return newRadius;
	}

	
	
	
}