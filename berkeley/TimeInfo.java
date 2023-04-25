package berkeley;
import java.time.LocalTime;
import java.util.ArrayList;

public class TimeInfo {
    private String name;
    private LocalTime time;
    private long rtt;
    private LocalTime estimatedTime;
    private LocalTime mean;
    private long adjustments;

    public TimeInfo(String name, LocalTime time, long rtt, LocalTime estimatedTime, LocalTime mean, long adjustments) {
        this.name = name;
        this.time = time;
        this.rtt = rtt;
        this.estimatedTime = estimatedTime;
        this.mean = mean;
        this.adjustments = adjustments;
    }

    public String getName() {
        return name;
    }

    public LocalTime getTime() {
        return time;
    }

    public long getRtt() {
        return rtt;
    }

    public LocalTime getEstimatedTime() {
        return estimatedTime;
    }

    public LocalTime getmean() {
        return mean;
    }

    public long getAdjustments() {
        return adjustments;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setRtt(long rtt) {
        this.rtt = rtt;
    }

    public void setEstimatedTime(LocalTime estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public void setMean(LocalTime mean) {
        this.mean = mean;
    }

    public void setAdjustments(long adjustments) {
        this.adjustments = adjustments;
    }

    public void calculateEstimatedTime(long rtt) {
        long mean_rtt = rtt / 2;
        LocalTime new_time = this.time.plusNanos(mean_rtt * 1_000_000);
        System.out.println("Mean_RTT" + mean_rtt);
        this.estimatedTime = new_time;
    }

    public static LocalTime calculateMeans(ArrayList<TimeInfo> clocks, long rtt_max) {
        long total_seconds = 0;
        long total_clocks = 0;
        for(TimeInfo clock : clocks) {
            long clock_rtt = clock.getRtt();
            if(clock_rtt <= rtt_max) {
                total_seconds += clock.getEstimatedTime().toNanoOfDay();;
                total_clocks += 1;
            }
        }

        long mean_seconds = total_seconds / total_clocks;

        return LocalTime.ofNanoOfDay(mean_seconds);
    }

    public void calculateAdjustments() {
        long estimated_time = this.estimatedTime.toNanoOfDay() / 1_000_000;
        long mean_time = this.mean.toNanoOfDay() / 1_000_000;

        this.adjustments = Math.abs(mean_time - estimated_time);
    }
}
