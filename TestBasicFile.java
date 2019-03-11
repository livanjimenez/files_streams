import javax.swing.*;
import java.io.*;

public class TestBasicFile {
    static void showMessage(String s, String data, int t) {
        JTextArea txt = new JTextArea(s, 25, 45);
        JScrollPane p = new JScrollPane(txt);
        JOptionPane.showMessageDialog(null, p, data, t);
    }

    public static void main(String args[]) throws IOException {
        BasicFile f = new BasicFile();
        boolean process = false;
        String menu = "Menu" + "\n1.Select file\n2.View file selected\n3.File size & path" + "\n4.Files & directory \n"
                + "5.Number of line(s) found in the file \n6.Search \n7.Overwrite file \n8.Copy file \n9.Exit";
        while (!process) {
            try {
                int i = Integer.parseInt(JOptionPane.showInputDialog(menu));
                switch (i) {
                case 1:
                    f.f_select();

                    if (f.exists()) {
                        JOptionPane.showMessageDialog(null, "File has been selected", "",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        f.f_select();
                    }
                    break;
                case 2:
                    showMessage(f.f_content(f.getFile()), "File", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 3:
                    showMessage(f.getPath() + f.getSize(), "File", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 4:
                    showMessage(f.list(f.getFile()), "File", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 5:
                    showMessage(f.tokenize(), "File", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 6:
                    String word = JOptionPane.showInputDialog("Enter word(s)");
                    showMessage(f.f_search(word), "Search: " + word, JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 7:
                    f.overwrite();
                    if (f.exists()) {
                        JOptionPane.showMessageDialog(null, "File has been overwritten",
                                "Press enter to overwrite", JOptionPane.INFORMATION_MESSAGE);
                        showMessage(f.f_content(f.getFile()), "File", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        f.overwrite();
                    }
                    break;
                case 8:
                    f.f_select();
                    f.f_backup();
                    break;
                case 9:
                    process = true;
                    break;
                default:
                    break;
                }
            } catch (NumberFormatException | NullPointerException e) {
                System.exit(0);
            }
        }
    }
}