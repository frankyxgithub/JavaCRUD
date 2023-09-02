import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

public class JavaCrud {
    private JPanel Main;
    private JTextField txtName;
    private JTextField txtPrice;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField txtpid;
    private JTextField txtQty;
    private JButton searchButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("JavaCrud");
        frame.setContentPane(new JavaCrud().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }



    Connection connection;
    PreparedStatement st;
    public JavaCrud() {
        Connect();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name, price, Qty;

                name = txtName.getText();
                price = txtPrice.getText();
                Qty = txtQty.getText();

                try {
                    st = connection.prepareStatement("INSERT INTO products(name,price,Qty)VALUES(?,?,?)");
                    st.setString(1, name);
                    st.setString(2, price);
                    st.setString(3,Qty);
                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Added!!");

                    txtName.setText("");
                    txtPrice.setText("");
                    txtQty.setText("");
                    txtName.requestFocus();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String productID = txtpid.getText();

                    st = connection.prepareStatement("SELECT name,price,Qty FROM products WHERE productID = ?");
                    st.setString(1, productID);
                    ResultSet rs = st.executeQuery();

                    if (rs.next()==true){
                        String name = rs.getString(1);
                        String price = rs.getString(2);
                        String Qty = rs.getString(3);

                        txtName.setText(name);
                        txtPrice.setText(price);
                        txtQty.setText(Qty);

                    }else {
                        txtName.setText("");
                        txtPrice.setText("");
                        txtQty.setText("");
                        JOptionPane.showMessageDialog( null, "Invalid Product ID");
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productID, name, price, Qty;

                productID = txtpid.getText();
                name = txtName.getText();
                price = txtPrice.getText();
                Qty = txtQty.getText();

                try {
                    st = connection.prepareStatement("UPDATE products SET name =?,price =?,Qty =? WHERE productID =?");

                    st.setString(1, name);
                    st.setString(2, price);
                    st.setString(3,Qty);
                    st.setString(4, productID);

                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Updated Successfully!!");

                    txtName.setText("");
                    txtPrice.setText("");
                    txtQty.setText("");
                    txtName.requestFocus();
                    txtpid.setText("");

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productID;

                productID = txtpid.getText();

                try {
                    st = connection.prepareStatement("DELETE FROM products WHERE productID =?");
                    st.setString(1, productID);

                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted!!");

                    txtName.setText("");
                    txtPrice.setText("");
                    txtQty.setText("");
                    txtName.requestFocus();
                    txtpid.setText("");

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public void Connect(){

        try {
            connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/java_crud", "root", "efe@123#");
            System.out.println("success");

        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }


}
