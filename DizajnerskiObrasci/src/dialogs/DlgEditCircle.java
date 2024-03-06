package dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import geometry.Circle;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class DlgEditCircle extends JDialog {
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtRadius;
	boolean btnEditClicked = false;

	private Color edgeColor, innerColor=null;
	private JLabel lblX;
	private JLabel lblY;
	private JLabel lblRadius;
	private JButton btnColor;
	private JButton btnEdgeColor;
	private int newX ;
	private int newY;
	private int newRadius;
	private JButton btnOk;
	private JButton btnExit;


	public DlgEditCircle() {
		setResizable(false);
		setTitle("Sasa Ilic,IT4/2019");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 300, 198);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			JPanel pnlCenter = new JPanel();
			getContentPane().add(pnlCenter, BorderLayout.CENTER);
			{
				lblX = new JLabel("X coordinate", SwingConstants.CENTER);
			}
			{
				txtX = new JTextField();
				txtX.setColumns(10);
			}
			{
				lblY = new JLabel("Y coordinate");
				lblY.setHorizontalAlignment(SwingConstants.CENTER);
			}
			{
				txtY = new JTextField();
				txtY.setColumns(10);
			}
			{
				lblRadius = new JLabel("Radius");
				lblRadius.setHorizontalAlignment(SwingConstants.CENTER);
			}
			{
				txtRadius = new JTextField();
				txtRadius.setColumns(10);
			}
			{
				btnColor = new JButton("Inner color");
				btnColor.setHorizontalAlignment(SwingConstants.CENTER);
				btnColor.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						innerColor=(JColorChooser.showDialog(null, "Choose color", btnColor.getBackground()));
						if(innerColor!=null)
						btnColor.setBackground(innerColor);
					}
				});
			}
			{
			btnEdgeColor = new JButton("Edge color");
			btnEdgeColor.addActionListener(new ActionListener() {
				@Override
					public void actionPerformed(ActionEvent e) {
					edgeColor= (JColorChooser.showDialog(null, "Choose color", btnEdgeColor.getBackground()));
					if(edgeColor!=null)
					btnEdgeColor.setBackground(edgeColor);
				}
				});
			}
			{
				btnOk = new JButton("OK");
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
							newX = Integer.parseInt(txtX.getText());
							newY = Integer.parseInt(txtY.getText());
							newRadius = Integer.parseInt(txtRadius.getText());
							innerColor=btnColor.getBackground();
							edgeColor=btnEdgeColor.getBackground();

							if(newX < 0 || newY < 0 || newRadius < 1) {
								JOptionPane.showMessageDialog(null, "You have entered incorrect informations!", "ERROR!", JOptionPane.ERROR_MESSAGE);
								return;
							}
							
							btnEditClicked=true;
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
				gl_pnlCenter.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlCenter.createSequentialGroup()
						.addComponent(lblX, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtX, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_pnlCenter.createSequentialGroup()
						.addComponent(lblY, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtY, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_pnlCenter.createSequentialGroup()
						.addComponent(lblRadius, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_pnlCenter.createSequentialGroup()
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addGap(24)
								.addComponent(btnColor, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGroup(Alignment.TRAILING, gl_pnlCenter.createSequentialGroup()
								.addContainerGap()
								.addComponent(btnOk)
								.addPreferredGap(ComponentPlacement.RELATED)))
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addGap(43)
								.addComponent(btnExit)
								.addGap(36))
							.addGroup(Alignment.TRAILING, gl_pnlCenter.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
								.addComponent(btnEdgeColor)
								.addGap(46))))
			);
			gl_pnlCenter.setVerticalGroup(
				gl_pnlCenter.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlCenter.createSequentialGroup()
						.addGap(1)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addComponent(lblX, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtX, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addComponent(lblY, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtY, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addComponent(lblRadius, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addComponent(btnColor, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
							.addComponent(btnEdgeColor, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnExit)
							.addComponent(btnOk)))
			);
			pnlCenter.setLayout(gl_pnlCenter);
		}
	}
	
	

	public JTextField getTxtX() {
		return txtX;
	}



	public JTextField getTxtY() {
		return txtY;
	}

	public JTextField getTxtRadius() {
		return txtRadius;
	}

	public Color getEdgeColor() {
		return edgeColor;
	}

	public Color getInnerColor() {
		return innerColor;
	}

	public JButton getBtnColor() {
		return btnColor;
	}



	public JButton getBtnEdgeColor() {
		return btnEdgeColor;
	}
	
	public int getNewX() {
		return newX;
	}
	public boolean isBtnEditClicked() {
		return btnEditClicked;
	}


	public int getNewY() {
		return newY;
	}


	public int getNewRadius() {
		return newRadius;
	}
	

}