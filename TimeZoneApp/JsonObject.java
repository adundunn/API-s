// This file is meant to live in *project name*/src/main/java

public class JsonObject {

    long dstOffset, rawOffset;
    String status, timeZoneId, timeZoneName;

    public JsonObject (int dstOffset, int rawOffset, String status, String timeZoneId, String timeZoneName) {
        this.dstOffset = dstOffset;
        this.rawOffset = rawOffset;
        this.status = status;
        this.timeZoneId = timeZoneId;
        this.timeZoneName = timeZoneName;
    }
}
