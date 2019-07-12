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

    String getVideoName() {
        return videoName;
    }

    private String getVideoUrl() {
        return videoUrl;
    }

    private String getTimeLength() {
        return timeLength;
    }

    int getSerialNumber() {
        return serialNumber;
    }

}
