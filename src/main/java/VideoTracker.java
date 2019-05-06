public class VideoTracker {

    private String videoName;
    private String timeLength;
    private String videoUrl;
    private int serialNumber;

    VideoTracker(String videoName, String timeLength, String videoUrl, int serialNumber) {
        this.videoName = videoName;
        this.timeLength = timeLength;
        this.videoUrl = videoUrl;
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        String serialNumber = String.format("%-4s", /*"Sr.No.: " + */ getSerialNumber());
        String videoName = String.format("%-40s", /*"Video: " + */ getVideoName());
        String videoTimeLength = String.format("%s", /*"\tTime: " + */ getTimeLength());
        String videoUrl = String.format("%s", /*"\tTime: " + */ getVideoUrl());
        return serialNumber + videoName + videoTimeLength + "\n" + "URL : " + videoUrl + "\n";
//        return "Video: " + getVideoName() + "\t\t\t\t\t\t\t|\tTime: " + getTimeLength();
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
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
