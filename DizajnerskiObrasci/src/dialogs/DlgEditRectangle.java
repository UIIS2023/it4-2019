package dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import geometry.Point;
import geometry.Rectangle;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class DlgEditRectangle extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtHeight;
	private JTextField txtWidth;
	
	boolean btnEditClicked = false;
	
	private int newX;
	private int newY ;
	private int newHeight;
	private int newWIdth;
	private JButton btnInnerColor;
	private JButton btnEdgeColor;

	private Color edgeColor = null, innerColor = null;
	private JLabel lblX;
	private JLabel lblY;
	private JLabel lblHeight;
	private JLabel lblWidth;
	private JButton btnOk;
	private JButton btnExit;

	public DlgEditRectangle() {
		setResizable(false);
		setTitle("Sasa Ilic,IT4/2019");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 300, 240);
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
				btnEdgeColor = new JButton("Edge color");
				btnEdgeColor.setHorizontalAlignment(SwingConstants.CENTER);
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
				btnInnerColor = new JButton("Inner color");
				btnInnerColor.setHorizontalAlignment(SwingConstants.CENTER);
				btnInnerColor.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						innerColor=(JColorChooser.showDialog(null, "Choose color", btnInnerColor.getBackground()));
						if(innerColor!=null)
						btnInnerColor.setBackground(innerColor);
					}
				});
			}
			GroupLayout gl_pnlCenter = new GroupLayout(pnlCenter);
			gl_pnlCenter.setHorizontalGroup(
				gl_pnlCenter.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlCenter.createSequentialGroup()
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addComponent(lblX, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtX, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addComponent(lblY, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtY, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addComponent(lblHeight, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtHeight, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
									.addComponent(lblWidth, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
									.addGroup(gl_pnlCenter.createSequentialGroup()
										.addGap(12)
										.addComponent(btnInnerColor, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
									.addComponent(txtWidth, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnEdgeColor, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			gl_pnlCenter.setVerticalGroup(
				gl_pnlCenter.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlCenter.createSequentialGroup()
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addComponent(lblX, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtX, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addComponent(lblY, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtY, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addComponent(lblHeight, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtHeight, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addComponent(lblWidth, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtWidth, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addComponent(btnEdgeColor, GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
							.addComponent(btnInnerColor, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
						.addGap(14))
			);
			pnlCenter.setLayout(gl_pnlCenter);
		}
		{
			JPanel pnlSouth = new JPanel();
			getContentPane().add(pnlSouth, BorderLayout.SOUTH);
			{
				btnOk = new JButton("OK");
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
							newX = Integer.parseInt(txtX.getText());
							newY = Integer.parseInt(txtY.getText());
							newHeight = Integer.parseInt(txtHeight.getText());
							newWIdth = Integer.parseInt(txtWidth.getText());
							innerColor=btnInnerColor.getBackground();
							edgeColor=btnEdgeColor.getBackground();

							if(newX < 0 || newY < 0 || newHeight < 1 || newWIdth < 1) {
								JOptionPane.showMessageDialog(null, "You have entered incorrect informations!", "Greska!", JOptionPane.ERROR_MESSAGE);
								return;
							}
							btnEditClicked = true;
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
			GroupLayout gl_pnlSouth = new GroupLayout(pnlSouth);
			gl_pnlSouth.setHorizontalGroup(
				gl_pnlSouth.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlSouth.createSequentialGroup()
						.addGap(90)
						.addComponent(btnOk)
						.addGap(5)
						.addComponent(btnExit))
			);
			gl_pnlSouth.setVerticalGroup(
				gl_pnlSouth.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlSouth.createSequentialGroup()
						.addGap(5)
						.addGroup(gl_pnlSouth.createParallelGroup(Alignment.LEADING)
							.addComponent(btnOk)
							.addComponent(btnExit)))
			);
			pnlSouth.setLayout(gl_pnlSouth);
			
		
		}
		
	}
	public Color getEdgeColor() {
		return edgeColor;
	}

	public Color getInnerColor() {
		return innerColor;
	}

	public JButton getBtnColor() {
		return btnInnerColor;
	}



	public JButton getBtnEdgeColor() {
		return btnEdgeColor;
	}

	public JTextField getTxtX() {
		return txtX;
	}

	public JTextField getTxtY() {
		return txtY;
	}

	public JTextField getTxtHeight() {
		return txtHeight;
	}

	public JTextField getTxtWidth() {
		return txtWidth;
	}

	public int getNewX() {
		return newX;
	}

	public int getNewY() {
		return newY;
	}

	public int getNewHeight() {
		return newHeight;
	}

	public int getNewWIdth() {
		return newWIdth;
	}
	public boolean isBtnEditClicked() {
		return btnEditClicked;
	}
	
	

	
}