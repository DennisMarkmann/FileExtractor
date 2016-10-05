package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        this.startApplication();
    }

    private void startApplication() {
        final Settings settings = new Settings();
        if (this.seriesChecked) {
            new Extractor().extractFile(
                    new FileLister().listFilesInSubFolder(settings.getSeriesPath(), false),
                    settings.getSeriesPath());
            new Cleaner().cleanFiles(new FileLister().listFolderAtPath(settings.getSeriesPath()));

        }
        if (this.moviesChecked) {
            new Extractor().extractFile(
                    new FileLister().listFilesInSubFolder(settings.getMoviePath(), false),
                    settings.getSeriesPath());
            new Cleaner().cleanFiles(new FileLister().listFolderAtPath(settings.getMoviePath()));
        }
    }
}
