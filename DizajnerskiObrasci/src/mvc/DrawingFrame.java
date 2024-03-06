package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.UIManager;


public class DrawingFrame extends JFrame{
	
	private DrawingView view = new DrawingView();
	private DrawingController controller;

	private JPanel contentPane;
	private ButtonGroup btnsShapes = new ButtonGroup();
	private  ButtonGroup btnGroup= new ButtonGroup();
	private JToggleButton btnDrawing = new JToggleButton("Draw");
	private JToggleButton tglBtnSelect = new JToggleButton("Select");
	private JButton btnActionEdit = new JButton("Modify");
	private JButton btnActionDelete = new JButton("Delete");
	private JToggleButton tglbtnPoint = new JToggleButton("Point");
	private JToggleButton tglbtnLine = new JToggleButton("Line");
	private JToggleButton tglbtnRectangle = new JToggleButton("Rectangle");
	private JToggleButton tglbtnCircle = new JToggleButton("Circle");
	private JToggleButton tglbtnDonut = new JToggleButton("Donut");
	private JToggleButton tglbtnHexagon = new JToggleButton("Hexagon");

	private final JPanel panel = new JPanel();
	
	private final JButton btnEdgeColor = new JButton("Edge Color");
	private final JButton btnColor = new JButton("Color");
	
	private final JButton btnToFront = new JButton("To Front");
	private final JButton btnToBack = new JButton("To Back");
	private final JButton btnBringToBack = new JButton("Bring to back");
	private final JButton btnBringToFront = new JButton("Bring to front");

	private final JButton btnUndo = new JButton("\u21B6");
	private final JButton btnRedo = new JButton("\u21B7");
	
	private final JScrollPane scrollPane = new JScrollPane();
	private final JList<String> list = new JList<String>();
	private DefaultListModel<String> dlm = new DefaultListModel<String>();

	private final JButton btnSaveDraw = new JButton("Save draw");
	private final JButton btnOpenDraw = new JButton("Open draw");
	private final JButton btnSaveLog = new JButton("Save log");
	private final JButton btnOpenLog = new JButton("Open log");
	private final JButton btnLoadNextCommand = new JButton("Load next");
	
	public DrawingFrame() {
		btnEdgeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEdgeColor.setBackground(controller.pickColor(btnEdgeColor.getBackground()));
			}
		});
		btnEdgeColor.setForeground(Color.WHITE);
		btnEdgeColor.setBackground(Color.BLACK);
		btnEdgeColor.setFont(new Font("Dialog", Font.PLAIN, 17));
		setTitle("Sasa Ilic,IT4/2019");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 964, 655);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(1100, 700));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setResizable(false);
		btnGroup.add(btnDrawing);
		btnGroup.add(tglBtnSelect);

		tglBtnSelect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {			
						tglBtnSelect.setSelected(true);	
						btnDrawing.setSelected(false);
						//btnDrawing.setEnabled(false);
						
			}
		});

		JPanel pnlBottom = new JPanel();
		contentPane.add(pnlBottom, BorderLayout.SOUTH);
		btnActionEdit.setEnabled(false);
		btnActionEdit.setFont(new Font("Tw Cen MT", Font.PLAIN, 17));
		
		btnActionEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(btnActionEdit.isEnabled()) {
					controller.editShape();
					btnDrawing.setSelected(false);
				}
			}
		});
		btnDrawing.setSelected(true);
		btnDrawing.setFont(new Font("Tw Cen MT", Font.PLAIN, 17));
		
		btnDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.deselect();		

			}
		});
		btnActionDelete.setEnabled(false);
		btnActionDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnActionDelete.isEnabled()) {					
						controller.remove();
						repaint();
				}
			}
		});
			
	
		
		btnToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						controller.toFront();
						repaint();
				
			}
		});
		btnToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						controller.toBack();
						repaint();
				
			}
		});
		
		btnBringToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						controller.bringToFront();
						repaint();
				}
			
		});
		
		btnBringToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
									
						controller.bringToBack();
						repaint();
				
			}
		});
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnUndo.isEnabled()) {					
						controller.undo();
						repaint();
				}
			}
		});
			
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnRedo.isEnabled()) {					
						controller.redo();
						repaint();
				}
			}
		});
		btnSaveDraw.setEnabled(false);
		btnSaveDraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(btnSaveDraw.isEnabled()) {
					try {
						controller.saveDraw();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		});
		btnSaveLog.setEnabled(false);
		btnSaveLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnSaveLog.isEnabled()) {					
						try {
							controller.saveLog();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						repaint();
				}
			}
		});
		btnOpenDraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnOpenDraw.isEnabled()) {					
						try {
							controller.openDraw();
						} catch (IOException | ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						repaint();
				}
			}
		});
		btnOpenLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnOpenLog.isEnabled()) {					
						try {
							controller.openLog();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						repaint();
				}
			}
		});
		btnLoadNextCommand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.loadNextCommand();
			}
		});
		    btnDrawing.setAlignmentX(Component.CENTER_ALIGNMENT);
			tglBtnSelect.setFont(new Font("Tw Cen MT", Font.PLAIN, 17));
			tglBtnSelect.setAlignmentX(Component.CENTER_ALIGNMENT);
			btnActionEdit.setAlignmentX(Component.CENTER_ALIGNMENT);
			btnActionDelete.setFont(new Font("Tw Cen MT", Font.PLAIN, 17));
			
					
					btnActionDelete.setAlignmentX(Component.CENTER_ALIGNMENT);
					
			
					 btnToFront.setFont(new Font("Dialog", Font.PLAIN, 17));
					 btnToFront.setEnabled(false);
					 btnToFront.setAlignmentX(0.5f);
					
				
					 btnToBack.setFont(new Font("Dialog", Font.PLAIN, 17));
					 btnToBack.setEnabled(false);
					 btnToBack.setAlignmentX(0.5f);
					

					 btnBringToBack.setFont(new Font("Dialog", Font.PLAIN, 17));
					 btnBringToBack.setEnabled(false);
					 btnBringToBack.setAlignmentX(0.5f);
					
					 
					 btnBringToFront.setFont(new Font("Dialog", Font.PLAIN, 17));
					 btnBringToFront.setEnabled(false);
					 btnBringToFront.setAlignmentX(0.5f);
					
					GroupLayout gl_pnlBottom = new GroupLayout(pnlBottom);
					gl_pnlBottom.setHorizontalGroup(
						gl_pnlBottom.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnlBottom.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_pnlBottom.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_pnlBottom.createSequentialGroup()
										.addComponent(btnDrawing, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(tglBtnSelect, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(btnActionEdit, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
										.addComponent(btnActionDelete, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
										.addGap(12)
										.addComponent(btnToFront, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
										.addGap(12)
										.addComponent(btnToBack, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
										.addGap(12)
										.addComponent(btnBringToBack, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(btnBringToFront, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE))
									.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 1061, GroupLayout.PREFERRED_SIZE))
								.addContainerGap())
					);
					gl_pnlBottom.setVerticalGroup(
						gl_pnlBottom.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnlBottom.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_pnlBottom.createParallelGroup(Alignment.BASELINE)
									.addComponent(btnDrawing, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnBringToBack, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnToBack, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnToFront, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnActionDelete, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
									.addComponent(tglBtnSelect, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnActionEdit, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnBringToFront, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					);
					
					
					scrollPane.setViewportView(list);
					list.setModel(dlm);
					pnlBottom.setLayout(gl_pnlBottom);
					
					contentPane.add(panel, BorderLayout.WEST);
					btnColor.setEnabled(false);
					
					btnColor.setBackground(Color.WHITE);
					btnColor.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							
							btnColor.setBackground(controller.pickColor(btnColor.getBackground()));

						}
					});
					btnColor.setFont(new Font("Dialog", Font.PLAIN, 17));
					view.setBackground(Color.WHITE);
							GroupLayout gl_view = new GroupLayout(view);
							gl_view.setHorizontalGroup(
								gl_view.createParallelGroup(Alignment.LEADING)
									.addGap(0, 916, Short.MAX_VALUE)
							);
							gl_view.setVerticalGroup(
								gl_view.createParallelGroup(Alignment.LEADING)
									.addGap(0, 538, Short.MAX_VALUE)
							);
							view.setLayout(gl_view);
							
									view.addMouseListener(new MouseAdapter() {
							
										@Override
										public void mouseClicked(MouseEvent e) {
											
											if(tglBtnSelect.isSelected()) {											
												controller.select(e);
											}										
											if(btnDrawing.isEnabled() && btnDrawing.isSelected() ) {
												
												controller.addShape(e);
											}														
										}															
									});
					
					JPanel pnlTop = new JPanel();
					tglbtnPoint.setSelected(true);
					
						tglbtnPoint.setFont(new Font("Tw Cen MT", Font.PLAIN, 17));
						
						tglbtnPoint.setAlignmentX(Component.CENTER_ALIGNMENT);
						tglbtnPoint.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								btnColor.setEnabled(false);
							}
						});
						
						tglbtnLine.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								btnColor.setEnabled(false);
							}
						});
						tglbtnRectangle.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								btnColor.setEnabled(true);
							}
						});
						tglbtnCircle.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								btnColor.setEnabled(true);
							}
						});
						tglbtnDonut.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								btnColor.setEnabled(true);
							}
						});
						tglbtnHexagon.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								btnColor.setEnabled(true);
							}
						});
						
							btnsShapes.add(tglbtnPoint);
							tglbtnLine.setFont(new Font("Tw Cen MT", Font.PLAIN, 17));
							
							tglbtnLine.setAlignmentX(Component.CENTER_ALIGNMENT);
							btnsShapes.add(tglbtnLine);
							tglbtnRectangle.setFont(new Font("Tw Cen MT", Font.PLAIN, 17));
							
							tglbtnRectangle.setAlignmentX(Component.CENTER_ALIGNMENT);
							btnsShapes.add(tglbtnRectangle);
							tglbtnCircle.setFont(new Font("Tw Cen MT", Font.PLAIN, 17));
							
							tglbtnCircle.setAlignmentX(Component.CENTER_ALIGNMENT);
							btnsShapes.add(tglbtnCircle);
							tglbtnDonut.setFont(new Font("Tw Cen MT", Font.PLAIN, 17));
							
							tglbtnDonut.setAlignmentX(Component.CENTER_ALIGNMENT);
							btnsShapes.add(tglbtnDonut);
							
							btnsShapes.add(tglbtnHexagon);
							tglbtnRectangle.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									btnColor.setEnabled(true);
								}
							});
							tglbtnHexagon.setFont(new Font("Dialog", Font.PLAIN, 17));
							GroupLayout gl_pnlTop = new GroupLayout(pnlTop);
							gl_pnlTop.setHorizontalGroup(
								gl_pnlTop.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_pnlTop.createSequentialGroup()
										.addContainerGap()
										.addComponent(tglbtnPoint, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(tglbtnLine, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(tglbtnRectangle)
										.addGap(18)
										.addComponent(tglbtnDonut, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(tglbtnCircle, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(tglbtnHexagon, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
										.addGap(128))
							);
							gl_pnlTop.setVerticalGroup(
								gl_pnlTop.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_pnlTop.createSequentialGroup()
										.addContainerGap()
										.addGroup(gl_pnlTop.createParallelGroup(Alignment.BASELINE)
											.addComponent(tglbtnPoint, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
											.addComponent(tglbtnLine, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
											.addComponent(tglbtnRectangle, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
											.addComponent(tglbtnDonut, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
											.addComponent(tglbtnCircle, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
											.addComponent(tglbtnHexagon, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
										.addContainerGap(16, Short.MAX_VALUE))
							);
							pnlTop.setLayout(gl_pnlTop);
					
					
					btnUndo.setForeground(Color.BLACK);
					btnUndo.setFont(new Font("Cambria Math", Font.PLAIN, 53));
					btnUndo.setEnabled(false);
					btnUndo.setBorderPainted(false);
					btnUndo.setBorder(null);
					btnUndo.setBackground(SystemColor.menu);

					
					btnRedo.setForeground(Color.BLACK);
					btnRedo.setFont(new Font("Cambria Math", Font.PLAIN, 53));
					btnRedo.setEnabled(false);
					btnRedo.setBorderPainted(false);
					btnRedo.setBorder(null);
					btnRedo.setBackground(SystemColor.menu);
					
					
					btnSaveDraw.setForeground(Color.BLACK);
					btnSaveDraw.setFont(new Font("Dialog", Font.PLAIN, 17));
					btnSaveDraw.setBackground(UIManager.getColor("Button.background"));
					
					
					btnOpenDraw.setForeground(Color.BLACK);
					btnOpenDraw.setFont(new Font("Dialog", Font.PLAIN, 17));
					btnOpenDraw.setBackground(UIManager.getColor("Button.background"));
					
					
					btnSaveLog.setForeground(Color.BLACK);
					btnSaveLog.setFont(new Font("Dialog", Font.PLAIN, 17));
					btnSaveLog.setBackground(UIManager.getColor("Button.background"));
					
					
					btnOpenLog.setForeground(Color.BLACK);
					btnOpenLog.setFont(new Font("Dialog", Font.PLAIN, 17));
					btnOpenLog.setBackground(UIManager.getColor("Button.background"));
					
					
					btnLoadNextCommand.setForeground(Color.BLACK);
					btnLoadNextCommand.setFont(new Font("Dialog", Font.PLAIN, 17));
					btnLoadNextCommand.setEnabled(false);
					btnLoadNextCommand.setBackground(UIManager.getColor("Button.background"));
					GroupLayout gl_panel = new GroupLayout(panel);
					gl_panel.setHorizontalGroup(
						gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(btnColor, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnEdgeColor, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addComponent(btnSaveDraw, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnSaveLog, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnOpenDraw, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnOpenLog, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnLoadNextCommand, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addComponent(view, GroupLayout.PREFERRED_SIZE, 915, GroupLayout.PREFERRED_SIZE)
									.addGroup(gl_panel.createSequentialGroup()
										.addComponent(pnlTop, GroupLayout.PREFERRED_SIZE, 782, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(btnUndo, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(btnRedo, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(19, Short.MAX_VALUE))
					);
					gl_panel.setVerticalGroup(
						gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel.createSequentialGroup()
										.addContainerGap()
										.addComponent(btnColor, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(btnEdgeColor, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
										.addGap(30)
										.addComponent(btnSaveDraw, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(btnSaveLog, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(btnOpenDraw, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
										.addGap(10)
										.addComponent(btnOpenLog, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(btnLoadNextCommand, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_panel.createSequentialGroup()
										.addGap(5)
										.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
											.addComponent(pnlTop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(btnUndo, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
											.addComponent(btnRedo, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(view, GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)))
								.addContainerGap())
					);
					panel.setLayout(gl_panel);
	}
	public DrawingView getView() {
		return view;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}
	public JToggleButton getTglbtnPoint() {
		return tglbtnPoint;
	}
	public void setTglbtnPoint(JToggleButton tglbtnPoint) {
		this.tglbtnPoint = tglbtnPoint;
	}
	public JButton getBtnActionDelete() {
		return btnActionDelete;
	}
	public JToggleButton getTglbtnLine() {
		return tglbtnLine;
	}
	public JToggleButton getTglbtnRectangle() {
		return tglbtnRectangle;
	}
	public JToggleButton getTglbtnCircle() {
		return tglbtnCircle;
	}
	public JToggleButton getTglbtnDonut() {
		return tglbtnDonut;
	}
	public JButton getbtnColor() {
		return btnColor;
	}
	public JToggleButton getTglbtnHexagon() {
		return tglbtnHexagon;
	}
	public JButton getBtnEdgeColor() {
		return btnEdgeColor;
	}
	public JButton getBtnActionEdit() {
		return btnActionEdit;
	}
	public JButton getBtnToFront() {
		return btnToFront;
	}
	public JButton getBtnToBack() {
		return btnToBack;
	}
	public JButton getBtnBringToBack() {
		return btnBringToBack;
	}
	public JButton getBtnBringToFront() {
		return btnBringToFront;
	}
	public JButton getBtnUndo() {
		return btnUndo;
	}
	public JButton getBtnRedo() {
		return btnRedo;
	}
	public JList<String> getList() {
		return list;
	}
	public DefaultListModel<String> getDlm() {
		return dlm;
	}
	public JButton getBtnLoadNextCommand() {
		return btnLoadNextCommand;
	}
	public JButton getBtnSaveDraw() {
		return btnSaveDraw;
	}
	public JButton getBtnSaveLog() {
		return btnSaveLog;
	}
	public void dlmClear() {
		dlm.clear();
	}
	public JToggleButton getBtnDrawing() {
		return btnDrawing;
	}
	public JToggleButton getTglBtnSelect() {
		return tglBtnSelect;
	}
}
