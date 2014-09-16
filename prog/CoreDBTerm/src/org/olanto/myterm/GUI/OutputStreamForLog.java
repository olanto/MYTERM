package org.olanto.myterm.GUI;
 
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;
 
/**
 * 
 */
public class OutputStreamForLog extends OutputStream {
    private JTextArea log;
     
    public OutputStreamForLog(JTextArea logArea) {
        this.log = logArea;
    }
     
    @Override
    public void write(int b) throws IOException {
        log.append(String.valueOf((char)b));
        log.setCaretPosition(log.getDocument().getLength());
        log.update(log.getGraphics());
    }
}
