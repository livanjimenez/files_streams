import javax.swing.*;
import java.io.*;
import java.util.*;

public class BasicFile {
    File f;
    File backup = new File(".", "File Backup");
    String fileList = "";
    JFileChooser choose;

    public BasicFile() {
        choose = new JFileChooser(".");
    }

    void showMessage(String msg, String s, int t) {
        JOptionPane.showMessageDialog(null, msg, s, t);
    }

    String getPath() {
        return "Path: " + f.getAbsolutePath() + "\n";
    }

    File getFile() {
        return f;
    }

    String getSize() {
        return "File size: " + Long.toString(f.length()) + " Bytes\n";
    }

    String getFileName() {
        return f.getName();
    }

    boolean exists() {
        return f.exists();
    }

    public void f_select() {
        int status = choose.showOpenDialog(null);

        try {
            if (status != JFileChooser.APPROVE_OPTION)
                throw new IOException();
            f = choose.getSelectedFile();
            if (!f.exists())
                showMessage(f.getName(), "File has been choosen", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {

            showMessage("File not found..", e.toString(), JOptionPane.WARNING_MESSAGE);
        } catch (IOException e) {
            showMessage("Select approve to continue..", e.toString(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public void f_backup() throws FileNotFoundException, IOException {
        DataInputStream in = null;
        DataOutputStream out = null;
        try {
            in = new DataInputStream(new FileInputStream(f));
            out = new DataOutputStream(new FileOutputStream(backup));
            try {
                while (true) {
                    byte a = in.readByte();
                    out.writeByte(a);
                }
            } catch (EOFException notFound) {
                JOptionPane.showMessageDialog(null, "File backup was successful", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException notFound) {
                JOptionPane.showMessageDialog(null, "File not found..", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        } finally {
            try {
                in.close();
                out.close();
            } catch (Exception e) {
                showMessage("error", e.toString(), JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public String f_content(File f) throws IOException {
        String data = "";
        String temp = "";
        BufferedReader br = new BufferedReader(new FileReader(f));
        while ((temp = br.readLine()) != null)
            data = data + temp + "\n";
        br.close();
        return data;
    }

    public String list(File f) throws FileNotFoundException {
        try {
            if (f.exists()) {
                File dir = f.getParentFile();

                if (dir.isDirectory()) {
                    fileList = fileList + "Directory:  " + dir + "\n";
                    File[] list = dir.listFiles();
                    for (int i = 0; i < list.length; i++) {
                        if (list[i].isFile())
                            fileList = fileList + "File:  " + list[i].getName() + "\n";
                        else if (list[i].isDirectory())
                            fileList = fileList + "Folder:  " + list[i].getName() + "\n";
                    }
                }
                list(dir);
            } else
                throw new FileNotFoundException("File does not exist");
        } catch (NullPointerException e) {
        }
        return fileList;
    }

    public String tokenize() {
        {
            int wordCount = 0;
            int num = 0;
            int lineCount = 1;
            int charCount = 0;
            String str = "";
            try {
                FileReader fr = new FileReader(f);
                StreamTokenizer st = new StreamTokenizer(fr);

                st.eolIsSignificant(true);

                while (st.nextToken() != StreamTokenizer.TT_EOF) {
                    switch (st.ttype) {
                    case StreamTokenizer.TT_NUMBER:
                        num++;
                        break;
                    case StreamTokenizer.TT_WORD:
                        charCount += st.sval.length();
                        wordCount++;
                        break;
                    case StreamTokenizer.TT_EOL:
                        lineCount++;
                        break;
                    case StreamTokenizer.TT_EOF:
                        break;
                    default:

                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            }
            str = f.getName() + ": " + " has " + lineCount + " line(s), " + wordCount + " word(s), " + charCount
                    + " and character(s). ";
            return str;
        }
    }

    String f_search(String keyword) throws IOException {
        String total = "";
        String line;
        try {
            LineNumberReader read = new LineNumberReader(new FileReader(f));
            while ((line = read.readLine()) != null) {

                StringTokenizer words = new StringTokenizer(line);
                System.out.println(line);
                if (findWord(words, keyword)) {
                    total = total + "line " + read.getLineNumber() + ":" + line + "\n";
                }
            }
            read.close();
        } catch (IOException e) {
        }
        return total;
    }

    static boolean findWord(StringTokenizer st, String index) {
        if (!st.hasMoreTokens()) {
            return false;
        } else if (st.nextToken().equals(index)) {
            return true;
        } else
            return findWord(st, index);
    }

    public void overwrite() throws IOException {
        PrintWriter pw = new PrintWriter(f);
        boolean end = false;
        while (!end) {
            int choice = GetData.getInt("Would you like to overwrite the file? \n1. Yes \n2. No");
            switch (choice) {
            case 1:
                String info = GetData.getWord("Enter your new file details");
                pw.print(info);
                pw.println();
                break;
            case 2:
                end = true;
                break;
            default:
                break;
            }
        }
        pw.flush();
        pw.close();
    }
}