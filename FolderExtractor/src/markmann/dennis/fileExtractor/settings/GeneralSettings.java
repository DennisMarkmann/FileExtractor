package markmann.dennis.fileExtractor.settings;

public class GeneralSettings {

    private int timerInterval;
    private boolean useUnzipping;
    private boolean useGui;
    private boolean useTimer;
    private boolean useTaskbarEntry;
    private boolean useNotificationWhileWorking;
    private boolean useRenaming;
    private boolean useCleanup;
    private boolean useFileMoving;
    private boolean useExtendedLogging;
    private boolean removeCorruptFiles;
    private String language;

    public String getLanguage() {
        return this.language;
    }

    public int getTimerInterval() {
        return this.timerInterval;
    }

    public boolean removeCorruptFiles() {
        return this.removeCorruptFiles;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setRemoveCorruptFiles(boolean removeCorruptFiles) {
        this.removeCorruptFiles = removeCorruptFiles;
    }

    public void setTimerInterval(int timerInterval) {
        this.timerInterval = timerInterval;
    }

    public void setUseCleanup(boolean useCleanup) {
        this.useCleanup = useCleanup;
    }

    public void setUseExtendedLogging(boolean useExtendedLogging) {
        this.useExtendedLogging = useExtendedLogging;
    }

    public void setUseFileMoving(boolean useFileMoving) {
        this.useFileMoving = useFileMoving;
    }

    public void setUseGui(boolean useGui) {
        this.useGui = useGui;
    }

    public void setUseNotificationWhileWorking(boolean useNotificationWhileWorking) {
        this.useNotificationWhileWorking = useNotificationWhileWorking;
    }

    public void setUseRenaming(boolean useRenaming) {
        this.useRenaming = useRenaming;
    }

    public void setUseTaskbarEntry(boolean useTaskbarEntry) {
        this.useTaskbarEntry = useTaskbarEntry;
    }

    public void setUseTimer(boolean useTimer) {
        this.useTimer = useTimer;
    }

    public void setUseUnzipping(boolean useUnzipping) {
        this.useUnzipping = useUnzipping;
    }

    public boolean useCleanup() {
        return this.useCleanup;
    }

    public boolean useExtendedLogging() {
        return this.useExtendedLogging;
    }

    public boolean useFileMoving() {
        return this.useFileMoving;
    }

    boolean useGui() {
        return this.useGui;
    }

    boolean useNotificationWhileWorking() {
        return this.useNotificationWhileWorking;
    }

    public boolean useRenaming() {
        return this.useRenaming;
    }

    boolean useTaskbarEntry() {
        return this.useTaskbarEntry;
    }

    public boolean useTimer() {
        return this.useTimer;
    }

    boolean useUnzipping() {
        return this.useUnzipping;
    }

}
