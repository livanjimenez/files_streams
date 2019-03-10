import java.io.*;
import javax.swing.*;
import java.text.*;
import java.util.*;

public class BasicFile {
    File file;
    // f_ stands for file

    File f_backup = new File(".", "File Backup");

    String pat = "";
    String list = "";
    String searchResult;

    JFileChooser f_choose;

    public BasicFile() {
        f_choose = new JFileChooser(".");
    }

    public void showMessage(String m, String s, int i) {
        JOptionPane.showMessageDialog(null, m, s, i);
    }

    public void f_select() {
        int status = f_choose.showOpenDialog(null);

        try {
            if (status != f_choose.APPROVE_OPTION)
                throw new IOException();
            file = f_choose.getSelectedFile();
            if (!file.exists())
                showMessage(file.getName(), "File has been choosen", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            showMessage("No file found", e.toString(), JOptionPane.WARNING_MESSAGE);
        } catch (IOException e) {
            showMessage("Approve option was not selected", e.toString(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public void f_backup() throws FileNotFoundException, IOException {
        DataInputStream in = null;
        DataOutputStream out = null;

        try {
            while (true) {
                byte data = in.readByte();
                out.writeByte(data);
            }
        } catch (EOFException e) {
            showMessage("File backup completed", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            showMessage("File not found", "Error", JOptionPane.INFORMATION_MESSAGE);
        } finally {
            try {
                in.close();
                out.close();
            } catch (Exception e) {
                showMessage("Error", e.toString(), JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public String getPath() {
        return "Path: " + file.getAbsolutePath() + "\n";
    }

    public String getSize() {
        return "File size: " + Long.toString(file.length()) + " Bytes\n";
    }

    boolean exists() {
        return file.exists();
    }

    public String f_contents(File f) throws IOException {
        String s_data = "";
        String temp = "";
        BufferedReader br = new BufferedReader(new FileReader(f));

        while ((temp = br.readLine()) != null) {
            s_data += temp + "\n";
        }
        br.close();
        return s_data;
    }

    public String f_list(File f) throws FileNotFoundException {
        return pat + list;
    }
}