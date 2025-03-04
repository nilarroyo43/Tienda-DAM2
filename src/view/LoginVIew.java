package view;

import javax.swing.JOptionPane;
import model.Employee;
import static utils.Constants.MAX_ATTEMPTS;

import java.io.IOException;

public class LoginVIew extends javax.swing.JFrame {

	private int failedAttempts = 0;

	/**
	 * Creates new form LoginVIew
	 */
	public LoginVIew() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jLNum = new javax.swing.JLabel();
		jLPass = new javax.swing.JLabel();
		jTNum = new javax.swing.JTextField();
		jTPass = new javax.swing.JTextField();
		jBLogin = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jLNum.setText("Número de empleado");

		jLPass.setText("Password");

		jTNum.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTNumActionPerformed(evt);
			}
		});

		jBLogin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
		jBLogin.setText("Acceder");
		jBLogin.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jBLogin.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					jBLoginActionPerformed(evt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(76, 76, 76)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(jLPass).addComponent(jLNum).addComponent(jTNum)
								.addComponent(jTPass, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
						.addContainerGap(174, Short.MAX_VALUE))
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jBLogin,
								javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(41, 41, 41)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(84, 84, 84).addComponent(jLNum).addGap(18, 18, 18)
						.addComponent(jTNum, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(37, 37, 37).addComponent(jLPass).addGap(18, 18, 18)
						.addComponent(jTPass, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18).addComponent(jBLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(14, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void jTNumActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTNumActionPerformed
	}// GEN-LAST:event_jTNumActionPerformed

	private void jBLoginActionPerformed(java.awt.event.ActionEvent evt) throws IOException {// GEN-FIRST:event_jBLoginActionPerformed
		if (evt.getSource() == jBLogin) {
		}
		logUser();
	}// GEN-LAST:event_jBLoginActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new LoginVIew().setVisible(true);
			}
		});
	}

	private void logUser() throws IOException {
        Employee employee = new Employee();
        int user = Integer.parseInt(jTNum.getText());
        String pass = jTPass.getText();
          boolean finish = employee.logEmployee(user, pass);
        if (!finish) {
            failedAttempts++;
            if (failedAttempts >= MAX_ATTEMPTS) {
                JOptionPane.showMessageDialog(this, "Se ha alcanzado el número máximo de intentos.", "ERROR", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(this, "Numero de usuario o contraseña erronea.", "ERROR", JOptionPane.ERROR_MESSAGE);
             jTPass.setText("");
             jTNum.setText("");
            }
        } else {
        
            JOptionPane.showMessageDialog(this, "Session iniciada", "LOGIN", JOptionPane.PLAIN_MESSAGE);

            this.setVisible(false);
            dispose();
            ShopView SV = new ShopView();
            SV.setVisible(true);
        }
	 }

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jBLogin;
	private javax.swing.JLabel jLNum;
	private javax.swing.JLabel jLPass;
	private javax.swing.JTextField jTNum;
	private javax.swing.JTextField jTPass;
	// End of variables declaration//GEN-END:variables
}
