package FileExtractor.Settings;

public class GeneralSettings {

    private int timerInterval;
    private boolean useRenaming;
    private boolean useLogging;
    private boolean useUnzipping;
    private boolean useGui;
    private boolean useTimer;
    private boolean useTaskbarEntry;
    private boolean useNotificationWhileWorking;
    private String language;

    public String getLanguage() {
        return this.language;
    }

    public int getTimerInterval() {
        return this.timerInterval;
    }

    public boolean isUseGui() {
        return this.useGui;
    }

    public boolean isUseLogging() {
        return this.useLogging;
    }

    public boolean isUseNotificationWhileWorking() {
        return this.useNotificationWhileWorking;
    }

    public boolean isUseRenaming() {
        return this.useRenaming;
    }

    public boolean isUseTaskbarEntry() {
        return this.useTaskbarEntry;
    }

    public boolean isUseTimer() {
        return this.useTimer;
    }

    public boolean isUseUnzipping() {
        return this.useUnzipping;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setTimerInterval(int timerInterval) {
        this.timerInterval = timerInterval;
    }

    public void setUseGui(boolean useGui) {
        this.useGui = useGui;
    }

    public void setUseLogging(boolean useLogging) {
        this.useLogging = useLogging;
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

}
