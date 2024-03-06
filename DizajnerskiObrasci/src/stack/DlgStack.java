package stack;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import geometry.Point;
import geometry.Rectangle;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgStack extends JDialog {

	private final JPanel pnlCenter = new JPanel();
	private JButton btnAdd;
	private JButton cancelButton;
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtHeigth;
	private JTextField txtWidth;
	private Rectangle rectangle = null;

	public static void main(String[] args) {
		try {
			DlgStack dialog = new DlgStack();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DlgStack() {
		setModal(true);
		setResizable(false);
		setTitle("Sasa Ilic,IT4/2019");
		setBounds(100, 100, 306, 250);
		getContentPane().setLayout(new BorderLayout());
		pnlCenter.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(pnlCenter, BorderLayout.CENTER);
		
		JLabel lblX = new JLabel("X coordinate");
		
		JLabel lblY = new JLabel("Y coordinate");
		
		JLabel lblHeight = new JLabel("Height");
		
		JLabel lblWidth = new JLabel("Width");
		
		txtX = new JTextField();
		txtX.setColumns(10);
		
		txtY = new JTextField();
		txtY.setColumns(10);
		
		txtHeigth = new JTextField();
		txtHeigth.setColumns(10);
		
		txtWidth = new JTextField();
		txtWidth.setColumns(10);
		
		GroupLayout gl_pnlCenter = new GroupLayout(pnlCenter);
		gl_pnlCenter.setHorizontalGroup(
			gl_pnlCenter.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCenter.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_pnlCenter.createSequentialGroup()
							.addComponent(lblX)
							.addGap(18)
							.addComponent(txtX, 0, 0, Short.MAX_VALUE))
						.addGroup(gl_pnlCenter.createSequentialGroup()
							.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
								.addComponent(lblY)
								.addComponent(lblHeight)
								.addComponent(lblWidth))
							.addGap(18)
							.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING, false)
								.addComponent(txtY, 0, 0, Short.MAX_VALUE)
								.addComponent(txtHeigth, 0, 0, Short.MAX_VALUE)
								.addComponent(txtWidth, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))))
					.addContainerGap(92, Short.MAX_VALUE))
		);
		gl_pnlCenter.setVerticalGroup(
			gl_pnlCenter.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCenter.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlCenter.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblX)
						.addComponent(txtX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlCenter.createSequentialGroup()
							.addComponent(lblY)
							.addGap(18)
							.addComponent(lblHeight)
							.addGap(18)
							.addComponent(lblWidth))
						.addGroup(gl_pnlCenter.createSequentialGroup()
							.addComponent(txtY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtHeigth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		pnlCenter.setLayout(gl_pnlCenter);
		{
			JPanel pnlBottom = new JPanel();
			getContentPane().add(pnlBottom, BorderLayout.SOUTH);
			{
				btnAdd = new JButton("ADD");
				btnAdd.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
							int X = Integer.parseInt(txtX.getText());
							int Y = Integer.parseInt(txtY.getText());
							int height = Integer.parseInt(txtHeigth.getText());
							int width= Integer.parseInt(txtWidth.getText());
						
							if(X < 0|| Y < 0 || height < 1 || width < 1) {
								JOptionPane.showMessageDialog(null, "You have entered incorrect informations!", "ERROR", JOptionPane.ERROR_MESSAGE);
								return;
							}

							rectangle = new Rectangle(new Point(X, Y), height, width);
							dispose();
					}
					catch(Exception ex) {
							JOptionPane.showMessageDialog(null, "You have entered incorrect informations!", "ERROR", JOptionPane.ERROR_MESSAGE);

						}
					}
						
				});
				btnAdd.setActionCommand("OK");
				getRootPane().setDefaultButton(btnAdd);
			}
			{
				cancelButton = new JButton("EXIT");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
			}
			GroupLayout gl_pnlBottom = new GroupLayout(pnlBottom);
			gl_pnlBottom.setHorizontalGroup(
				gl_pnlBottom.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlBottom.createSequentialGroup()
						.addGap(38)
						.addComponent(btnAdd)
						.addGap(66)
						.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
						.addGap(55))
			);
			gl_pnlBottom.setVerticalGroup(
				gl_pnlBottom.createParallelGroup(Alignment.LEADING)
					.addGroup(Alignment.TRAILING, gl_pnlBottom.createSequentialGroup()
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_pnlBottom.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
						.addContainerGap())
			);
			pnlBottom.setLayout(gl_pnlBottom);
		}
	}

	public Rectangle getRectangle() {
		return rectangle;
	}
	public void setRectangle(Rectangle rect) {
		txtX.setText("" + rect.getUpperLeftPoint().getX());
		txtY.setText("" + rect.getUpperLeftPoint().getY());
		txtHeigth.setText("" + rect.getHeight());
		txtWidth.setText("" + rect.getWidth());
	}
}
