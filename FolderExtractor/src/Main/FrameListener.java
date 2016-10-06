package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JCheckBox;

/**
 * Listener for the settings frame. Used to fill the different values and to close the window.
 *
 * @author dennis.markmann
 * @since JDK.1.7.0_21
 * @version 1.0
 */

class FrameListener implements ActionListener {

    private final MainFrame frame;
    private final JCheckBox seriesCB;
    private final JCheckBox moviesCB;
    private boolean seriesChecked = false;
    private boolean moviesChecked = false;

    FrameListener(final MainFrame frame, final JCheckBox seriesCB, final JCheckBox moviesCB) {

        this.frame = frame;
        this.seriesCB = seriesCB;
        this.moviesCB = moviesCB;

    }

    @Override
    public final void actionPerformed(final ActionEvent event) {
        this.seriesChecked = this.seriesCB.isSelected();
        this.moviesChecked = this.moviesCB.isSelected();
        this.frame.closeWindow();

        final Settings settings = new Settings();
        if (this.seriesChecked) {
            this.startExtraction(settings.getSeriesPath());
        }
        if (this.moviesChecked) {
            this.startExtraction(settings.getMoviePath());
        }
    }

    private void startExtraction(File path) {
        FileLister fl = new FileLister();
        ArrayList<File> folderList = fl.listFolderAtPath(path);
        new Extractor().extractFile(fl.listFilesInFolderList(folderList), path);
        new Cleaner().cleanFiles(folderList);
    }
}
