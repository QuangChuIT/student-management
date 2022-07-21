package com.aptech.student.management.views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.aptech.student.management.model.Student;
import com.aptech.student.management.services.StudentService;
import com.aptech.student.management.util.SystemConfig;
import com.aptech.student.management.util.ValidatorUtil;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

public class frmMain extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel controlPanel;
	private JTextField txtBrithPlace, txtStudentID;
	private JButton btnInsert, btnUpdate, btnDelete, btnCancel, btnFilter, btnOK;
	private JTable table;
	private JTextField txtID, txtName, txtDate, txtMath, txtPhysic, txtChem, txtTotal;
	private JComboBox<String> cboCity;
	private JRadioButton btnMale, btnFeMale;
	private DefaultTableModel tableModel;
	private String[] colsName = { "ID", "Name", "Place", "DateOfBirth", "Sex", "Math", "Physical", "Chemistry" };
	private GridBagLayout gb;
	private GridBagConstraints gbc;
	private List<Student> students;
	private Student student = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmMain frame = new frmMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public frmMain() {

		/*
		 * if (LoginService.getInstance().getCurrentUser() == null) { new
		 * frmLogin().setVisible(true); return; }
		 */

		setSize(1100, 680);
		setTitle("Student Management");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		controlPanel = createJPanel();
		add(controlPanel, BorderLayout.CENTER);
	}

	private JPanel createJPanel() {
		JPanel panel = new JPanel();
		gb = new GridBagLayout();
		panel.setLayout(gb);
		gbc = new GridBagConstraints();

		Box studentFilterBox = Box.createVerticalBox();
		JPanel filterPanel = new JPanel();
		filterPanel.setLayout(gb);
		gbc.insets = new Insets(0, 10, 0, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		Add(filterPanel, new JLabel("BirthPlace"), 0, 0, 0, 0);
		txtBrithPlace = new JTextField("", 15);
		Add(filterPanel, txtBrithPlace, 1, 0, 30, 10);
		Add(filterPanel, new JLabel("StudentID"), 2, 0, 0, 0);
		txtStudentID = new JTextField("", 15);
		Add(filterPanel, txtStudentID, 3, 0, 50, 10);
		Add(filterPanel, new JLabel("   "), 4, 0, 0, 10);
		String root = System.getProperty("user.dir");
		System.out.println(root);
		Icon imageIcon = new ImageIcon(root + "/resources/plus-321.png");
		btnFilter = createJButtonIcon("Filter", imageIcon);
		Add(filterPanel, btnFilter, 5, 0, 40, 10);
		filterPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		studentFilterBox.add(filterPanel);
		studentFilterBox.setBorder(BorderFactory.createTitledBorder("Student Filter"));

		Box listBox = Box.createVerticalBox();
		table = createJTable();

		JScrollPane scroll = new JScrollPane(table);
		listBox.add(scroll);
		listBox.setBorder(BorderFactory.createTitledBorder("List Student"));

		Box studentInfo = Box.createVerticalBox();
		JPanel student = new JPanel();
		student.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		student.setLayout(gb);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 10, 5, 5);
		Add(student, new JLabel("ID"), 0, 0, 10, 10);
		txtID = new JTextField("", 15);
		txtID.setEditable(false);
		Add(student, txtID, 1, 0, 50, 10);
		txtName = new JTextField("", 15);
		Add(student, new JLabel("Name"), 0, 1, 10, 10);
		Add(student, txtName, 1, 1, 50, 10);
		Add(student, new JLabel("Place"), 0, 2, 10, 10);
		cboCity = createJComBoBox();
		Add(student, cboCity, 1, 2, 50, 10);
		Add(student, new JLabel("Date"), 0, 3, 10, 10);
		txtDate = new JTextField("", 15);
		Add(student, txtDate, 1, 3, 50, 10);
		Add(student, new JLabel("Sex"), 0, 4, 10, 10);
		JPanel psex = new JPanel();
		psex.setLayout(new FlowLayout());
		ButtonGroup btnGroupSex = new ButtonGroup();
		btnMale = new JRadioButton("Male");
		btnFeMale = new JRadioButton("Female");
		btnGroupSex.add(btnMale);
		btnGroupSex.add(btnFeMale);
		btnMale.setSelected(true);
		psex.add(btnMale);
		psex.add(btnFeMale);
		Add(student, psex, 1, 4, 50, 10);
		Add(student, new JLabel("Math"), 2, 0, 10, 10);
		txtMath = new JTextField("", 15);
		Add(student, txtMath, 3, 0, 50, 10);
		Add(student, new JLabel("Physical"), 2, 1, 50, 10);
		txtPhysic = new JTextField("", 15);
		txtChem = new JTextField("", 15);
		Add(student, txtPhysic, 3, 1, 50, 10);
		Add(student, new JLabel("Chemistry"), 2, 2, 10, 10);
		Add(student, txtChem, 3, 2, 50, 10);
		Add(student, new JLabel("Total"), 2, 3, 10, 10);
		txtTotal = new JTextField("", 15);
		Add(student, txtTotal, 3, 3, 50, 10);
		btnInsert = createJButton("Insert");
		btnUpdate = createJButton("Update");
		btnDelete = createJButton("Delete");
		btnCancel = createJButton("Cancel");
		btnOK = createJButton("OK");
		JPanel panelAction = new JPanel();
		panelAction.setLayout(gb);
		panel.setBackground(Color.GRAY);
		Add(panelAction, btnInsert, 0, 0, 30, 10);
		Add(panelAction, btnUpdate, 1, 0, 30, 10);
		Add(panelAction, btnDelete, 2, 0, 30, 10);
		Add(panelAction, btnOK, 3, 0, 40, 10);
		Add(panelAction, btnCancel, 4, 0, 30, 10);
		studentInfo.add(student);
		studentInfo.add(panelAction);
		studentInfo.setBorder(BorderFactory.createTitledBorder("Student Information"));
		gbc.fill = GridBagConstraints.HORIZONTAL;
		Add(panel, studentFilterBox, 0, 0, 450, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		Add(panel, listBox, 0, 1, 450, 200);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		Add(panel, studentInfo, 0, 2, 450, 5);
		this.loadData();
		return panel;
	}

	private void Add(Container ct, Component c, int col, int row, int ncol, int nrow) {
		gbc.gridx = col;
		gbc.gridy = row;

		gbc.ipadx = ncol;
		gbc.ipady = nrow;

		gb.setConstraints(c, gbc);

		ct.add(c);

	}

	private JButton createJButton(String title) {
		JButton btn = new JButton(title);
		btn.setPreferredSize(new Dimension(50, 20));
		btn.addActionListener(this);
		return btn;
	}

	private JButton createJButtonIcon(String title, Icon imageIcon) {
		JButton btn = new JButton(title, imageIcon);
		btn.setPreferredSize(new Dimension(50, 20));
		btn.addActionListener(this);
		return btn;
	}

	private JComboBox<String> createJComBoBox() {
		JComboBox<String> cbo = new JComboBox<>();
		return cbo;
	}

	private void loadData() {
		String keyword = txtStudentID.getText();
		students = StudentService.getInstance().getStudents(keyword);
		tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
		for (Student item : students) {
			String[] row = { item.getId() + "", item.getName(), item.getPalace(), item.getDateOfBirth(),
					item.isGender() == false ? "Male" : "Female", String.valueOf(item.getMath()),
					String.valueOf(item.getPhysical()), String.valueOf(item.getChemistry()) };
			tableModel.addRow(row);
		}
		if (this.students.size() > 0) {
			table.setRowSelectionInterval(0, 0);
			Long studentId = Long.parseLong(table.getValueAt(0, 0).toString());
			displayStudent(studentId);
		}
	}

	private void displayStudent(Long studentId) {
		Student student = students.stream().filter(item -> item.getId() == studentId).findFirst().get();
		this.student = student;
		txtID.setText(String.valueOf(student.getId()));
		txtName.setText(student.getName());
		txtDate.setText(student.getDateOfBirth());
		float math = student.getMath();
		float chemistry = student.getChemistry();
		float physical = student.getPhysical();
		float total = math + chemistry + physical;
		txtMath.setText(String.valueOf(math));
		txtChem.setText(String.valueOf(chemistry));
		txtPhysic.setText(String.valueOf(physical));
		txtTotal.setText(String.valueOf(total));
		boolean sex = student.isGender();
		if (sex) {
			btnFeMale.setSelected(true);
			btnMale.setSelected(false);
		} else {
			btnFeMale.setSelected(false);
			btnMale.setSelected(true);
		}
	}

	private JTable createJTable() {
		JTable tbl = new JTable();
		tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(colsName);
		tbl.setModel(tableModel);
		tbl.setRowHeight(25);
		tbl.getTableHeader().setFont(new Font("Time New Roman", Font.BOLD, 13));
		tbl.getTableHeader().setResizingAllowed(false);
		tbl.getTableHeader().setReorderingAllowed(false);
		tbl.getColumnModel().getColumn(0).setPreferredWidth(20);
		tbl.getColumnModel().getColumn(1).setPreferredWidth(120);
		tbl.getColumnModel().getColumn(2).setPreferredWidth(50);
		tbl.getColumnModel().getColumn(3).setPreferredWidth(100);
		tbl.getColumnModel().getColumn(4).setPreferredWidth(20);
		tbl.getColumnModel().getColumn(5).setPreferredWidth(30);
		tbl.getColumnModel().getColumn(6).setPreferredWidth(30);
		tbl.getColumnModel().getColumn(7).setPreferredWidth(30);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tbl.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tbl.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		;
		tbl.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		;
		tbl.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		;
		tbl.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		;
		tbl.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		;
		tbl.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
		;
		tbl.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
		;
		tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbl.setSelectionBackground(Color.CYAN);

		tbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				DefaultTableModel tableModel = (DefaultTableModel) tbl.getModel();
				Long studentID = Long.parseLong(tableModel.getValueAt(tbl.getSelectedRow(), 0).toString());
				displayStudent(studentID);
			}

		});
		tbl.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent arg0) {

				super.keyReleased(arg0);
			}

		});
		tbl.setAutoCreateRowSorter(true);
		tbl.setColumnSelectionAllowed(false);
		return tbl;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object cmd = e.getSource();
		if (cmd.equals(btnInsert)) {

		}
		if (cmd.equals(btnUpdate)) {
			this.update();
		}

		if (cmd.equals(btnDelete)) {
			this.delete();
		}
		if (cmd.equals(btnFilter)) {
			this.loadData();
		}
	}

	private void delete () {
		if (this.student == null) {
			JOptionPane.showMessageDialog(controlPanel, "Chưa chọn học sinh");
			return;
		}
		int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa học sinh " + this.student.getName() + " không?");
		if (!(choice == JOptionPane.YES_OPTION)) {
			return;
		}
		boolean deleted = StudentService.getInstance().delete(this.student.getId());
		if (deleted) {
			this.loadData();
			JOptionPane.showMessageDialog(controlPanel, "Xóa thông tin học sinh thành công");
		} else {
			JOptionPane.showMessageDialog(controlPanel, "Xóa thông tin học sinh thất bại");
		}
	}
	
	private void update() {
		try {
			if (this.student == null) {
				JOptionPane.showMessageDialog(controlPanel, "Chưa chọn học sinh");
				return;
			}
			if (!ValidatorUtil.validate(txtName.getText(), SystemConfig.getInstance().getProperty("rule_validate_name"))) {
				JOptionPane.showMessageDialog(controlPanel, "Tên không hợp lệ !");
				return;
			}
			this.student.setName(txtName.getText());
			boolean gender;
			if (btnFeMale.isSelected()) {
				gender = true;
			} else {
				gender = false;
			}
			this.student.setGender(gender);
			this.student.setMath(Float.parseFloat(txtMath.getText()));
			this.student.setChemistry(Float.parseFloat(txtChem.getText()));
			this.student.setPhysical(Float.parseFloat(txtPhysic.getText()));
			boolean updated = StudentService.getInstance().update(student);
			if (updated) {
				this.loadData();
				JOptionPane.showMessageDialog(controlPanel, "Cập nhật thông tin học sinh thành công");
			} else {
				JOptionPane.showMessageDialog(controlPanel, "Cập nhật thông tin học sinh thất bại");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
