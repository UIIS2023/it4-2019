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

public class DlgEditDonut extends JDialog {
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtRadius;
	private JTextField txtInnerRadius;
	
	private int newX;
	private int newY;
	private int newRadius ;
	private int newInnerRadius;
	
	private JButton btnInnerColor;
	private JButton btnEdgeColor;
	
	boolean btnEditClicked = false;
	
	private Donut donut = null;
	private Color edgeColor = null, innerColor = null;

	public DlgEditDonut() {
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
			pnlCenter.setLayout(new GridLayout(5, 2, 0, 0));
			{
				JLabel lblX = new JLabel("X coordinate", SwingConstants.CENTER);
				pnlCenter.add(lblX);
			}
			{
				txtX = new JTextField();
				pnlCenter.add(txtX);
				txtX.setColumns(10);
			}
			{
				JLabel lblY = new JLabel("Y coordinate");
				lblY.setHorizontalAlignment(SwingConstants.CENTER);
				pnlCenter.add(lblY);
			}
			{
				txtY = new JTextField();
				pnlCenter.add(txtY);
				txtY.setColumns(10);
			}
			{
				JLabel lblRadius = new JLabel("Radius");
				lblRadius.setHorizontalAlignment(SwingConstants.CENTER);
				pnlCenter.add(lblRadius);
			}
			{
				txtRadius = new JTextField();
				pnlCenter.add(txtRadius);
				txtRadius.setColumns(10);
			}
			{
				JLabel lblInnerRadius = new JLabel("Inner radius");
				lblInnerRadius.setHorizontalAlignment(SwingConstants.CENTER);
				pnlCenter.add(lblInnerRadius);
			}
			{
				txtInnerRadius = new JTextField();
				pnlCenter.add(txtInnerRadius);
				txtInnerRadius.setColumns(10);
			}
			{
				btnEdgeColor = new JButton("Edge color\r\n");
				btnEdgeColor.setHorizontalAlignment(SwingConstants.CENTER);
				btnEdgeColor.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						edgeColor= (JColorChooser.showDialog(null, "Choose color", btnEdgeColor.getBackground()));
						if(edgeColor!=null)
						btnEdgeColor.setBackground(edgeColor);
					}
				});
				pnlCenter.add(btnEdgeColor);
			}
			{
				btnInnerColor = new JButton("Inner Color");
				btnInnerColor.setHorizontalAlignment(SwingConstants.CENTER);
				btnInnerColor.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						innerColor=(JColorChooser.showDialog(null, "Choose color", btnInnerColor.getBackground()));
						if(innerColor!=null)
						btnInnerColor.setBackground(innerColor);
					}
				});
				pnlCenter.add(btnInnerColor);
			}
		}
		{
			JPanel pnlSouth = new JPanel();
			getContentPane().add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JButton btnOk = new JButton("OK");
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
							newX = Integer.parseInt(txtX.getText());
							newY = Integer.parseInt(txtY.getText());
							newRadius = Integer.parseInt(txtRadius.getText());
							newInnerRadius = Integer.parseInt(txtInnerRadius.getText());
							innerColor=btnInnerColor.getBackground();
							edgeColor=btnEdgeColor.getBackground();

							if(newX < 0 || newY < 0 || newRadius < 1 || newInnerRadius < 1 || newInnerRadius >= newRadius) {
								JOptionPane.showMessageDialog(null, "You have entered incorrect informations!", "ERROR", JOptionPane.ERROR_MESSAGE);
								return;
							}
							btnEditClicked=true;

							dispose();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "You have entered incorrect informations!", "ERROR", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				pnlSouth.add(btnOk);
			}
			{
				JButton btnExit = new JButton("EXIT");
				btnExit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				pnlSouth.add(btnExit);
			}
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

	public JTextField getTxtInnerRadius() {
		return txtInnerRadius;
	}


	public Color getEdgeColor() {
		return edgeColor;
	}

	public Color getInnerColor() {
		return innerColor;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}



	public JButton getBtnEdgeColor() {
		return btnEdgeColor;
	}
	
	public int getNewX() {
		return newX;
	}


	public int getNewY() {
		return newY;
	}


	public boolean isBtnEditClicked() {
		return btnEditClicked;
	}


	public int getNewInnerRadius() {
		return newInnerRadius;
	}


	public int getNewRadius() {
		return newRadius;
	}

}