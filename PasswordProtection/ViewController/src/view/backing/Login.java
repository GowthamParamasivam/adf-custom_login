package view.backing;

import java.security.spec.KeySpec;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.servlet.http.HttpSession;

import oracle.adf.view.rich.component.rich.RichDocument;
import oracle.adf.view.rich.component.rich.RichForm;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelStretchLayout;
import oracle.adf.view.rich.component.rich.nav.RichButton;

import oracle.igf.ids.util.Base64;

import oracle.jdbc.OracleDriver;

public class Login {
    private RichForm f1;
    private RichDocument d1;
    private RichPanelStretchLayout psl1;
    private RichPanelFormLayout pfl1;
    private RichInputText it1;
    private RichInputText it2;
    private RichButton b1;
    
    public void setb1(RichButton f1) {
        this.b1 = b1;
    }

    public RichButton getb1() {
        return b1;
    }

    public void setF1(RichForm f1) {
        this.f1 = f1;
    }

    public RichForm getF1() {
        return f1;
    }

    public void setD1(RichDocument d1) {
        this.d1 = d1;
    }

    public RichDocument getD1() {
        return d1;
    }


    public void setPsl1(RichPanelStretchLayout psl1) {
        this.psl1 = psl1;
    }

    public RichPanelStretchLayout getPsl1() {
        return psl1;
    }

    public void setPfl1(RichPanelFormLayout pfl1) {
        this.pfl1 = pfl1;
    }

    public RichPanelFormLayout getPfl1() {
        return pfl1;
    }

    public void setIt1(RichInputText it1) {
        this.it1 = it1;
    }

    public RichInputText getIt1() {
        return it1;
    }

    public void setIt2(RichInputText it2) {
        this.it2 = it2;
    }

    public RichInputText getIt2() {
        return it2;
    }

    public void login(ActionEvent actionEvent) {
        String user = this.getIt1().getValue().toString();
        String pass = this.getIt2().getValue().toString();
        Connection conn;

        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rset =
                stmt.executeQuery("select max(user_password) up,max(u_salt) us,max(user_name) un from c_user where user_id='" + user +
                                  "'");
            if (rset.next()) {
                String usersalt = rset.getString("us");
                String upassword = rset.getString("up");
                try {
                    String tempcalhas = getEncryptedPassword(pass, usersalt);
                    if (upassword.equals(tempcalhas)) {
                        FacesContext context = FacesContext.getCurrentInstance();
                        context.getApplication().getNavigationHandler().handleNavigation(context, "login",
                                                                                         "landingpage");
                        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
                        session.setAttribute("user_name", rset.getString("un"));

                    }
                } catch (Exception e) {
                    conn.close();
                    return;
                }
                conn.close();
                return;
            }
            conn.close();

        } catch (SQLException e) {
            System.out.println("Login failed "+e);
            return;
        }

        return;

    }

    public static Connection getConnection() throws SQLException {
        String username = "gowtham ";
        String password = "gowtham";
        String thinConn = "jdbc:oracle:thin:@localhost:1521:XE";
        DriverManager.registerDriver(new OracleDriver());
        Connection conn = DriverManager.getConnection(thinConn, username, password);
        conn.setAutoCommit(false);
        return conn;
    }

    private String getEncryptedPassword(String password, String salt) throws Exception {
        String algorithm = "PBKDF2WithHmacSHA1";
        int derivedKeyLength = 160; // for SHA1
        int iterations = 20000; // NIST specifies 10000
        byte[] saltBytes = Base64.decode(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, iterations, derivedKeyLength);
        SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
        byte[] encBytes = f.generateSecret(spec).getEncoded();
        return Base64.encode(encBytes);
    }
}
