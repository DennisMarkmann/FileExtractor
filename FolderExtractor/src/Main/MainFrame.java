package Main;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

import dennis.markmann.MyLibraries.GuiJobs.DefaultFrames.Implementations.DefaultFrame;
import dennis.markmann.MyLibraries.GuiJobs.DefaultFrames.Implementations.MyWindowAdapter;
import dennis.markmann.MyLibraries.GuiJobs.DefaultFrames.Implementations.WindowCloseDialogOptions;

class MainFrame extends JFrame implements DefaultFrame {

    private static MainFrame instance = null;
    private static final long serialVersionUID = 230542486392426735L;

    static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
        }
        else {
            instance.toFront();
        }
        return instance;
    }

    public MainFrame() {
        BUILDER.setDefaultFrameSettings(this, "FileExtractor");
        this.addWindowListener(new MyWindowAdapter(this));
        this.setSize(360, 120);
        this.setResizable(false);

        final JCheckBox seriesCB = BUILDER.createCheckBox(this, "seriesCB", "Series", 0, 0);
        final JCheckBox moviesCB = BUILDER.createCheckBox(this, "moviesCB", "Movies", 1, 0);
        final JButton bestaetigenButton = BUILDER.createButton(this, "bestaetigenButton", "Okay", 2, 0);

        final FrameListener listener = new FrameListener(this, seriesCB, moviesCB);
        bestaetigenButton.addActionListener(listener);

        this.setVisible(true);
    }

    @Override
    public void closeWindow() {
        this.dispose();
        instance = null;
    }

    @Override
    public void openClosingDialog(WindowCloseDialogOptions request) {
        System.exit(0);
    }
}
