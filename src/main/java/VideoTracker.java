public class VideoTracker {

    private String videoName;
    private String timeLength;
    private int serialNumber;

    VideoTracker(String videoName, String timeLength, int serialNumber) {
        this.videoName = videoName;
        this.timeLength = timeLength;
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        String serialNumber = String.format("%-4s", /*"Sr.No.: " + */ getSerialNumber());
        String videoName = String.format("%-40s", /*"Video: " + */ getVideoName());
        String videoTimeLength = String.format("%s", /*"\tTime: " + */ getTimeLength());
        return serialNumber + videoName + videoTimeLength + "\n";
//        return "Video: " + getVideoName() + "\t\t\t\t\t\t\t|\tTime: " + getTimeLength();
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(String timeLength) {
        this.timeLength = timeLength;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }
}
