package org.olanto.myterm.GUI;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;

/**
 *
 */
public class OutputStreamForLog extends OutputStream {

    private JTextArea log;
    private int step = 100;
    private int count = 0;

    public OutputStreamForLog(JTextArea logArea, int step) {
        this.log = logArea;
        this.step = step;
    }

    @Override
    public void write(int b) throws IOException {
        count++;
        log.append(String.valueOf((char) b));
        if (count % step == 0) {
            log.setCaretPosition(log.getDocument().getLength());
            log.update(log.getGraphics());
        }
    }
}
