package sprint7.dto.order;

public class OrderCreateResponse {
    private Integer track;

    public OrderCreateResponse() {
    }

    public OrderCreateResponse(Integer track) {
        this.track = track;
    }

    public Integer getTrack() {
        return track;
    }

    public void setTrack(Integer track) {
        this.track = track;
    }
}
