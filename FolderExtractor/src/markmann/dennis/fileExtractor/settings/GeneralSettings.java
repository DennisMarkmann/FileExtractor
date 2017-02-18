package markmann.dennis.fileExtractor.settings;

public class GeneralSettings {

    boolean useGui;
    boolean useSystemTray;
    boolean useTimer;
    int timerInterval;
    boolean useNotificationWhileWorking;
    boolean useRenaming;
    boolean useCleanup;
    boolean useFileMoving;
    boolean useExtendedLogging;
    boolean removeCorruptFiles;

    public int getTimerInterval() {
        return this.timerInterval;
    }

    public boolean removeCorruptFiles() {
        return this.removeCorruptFiles;
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

    public void setUseSystemTray(boolean useSystemTray) {
        this.useSystemTray = useSystemTray;
    }

    public void setUseTimer(boolean useTimer) {
        this.useTimer = useTimer;
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

    public boolean useSystemTray() {
        return this.useSystemTray;
    }

    public boolean useTimer() {
        return this.useTimer;
    }

}
