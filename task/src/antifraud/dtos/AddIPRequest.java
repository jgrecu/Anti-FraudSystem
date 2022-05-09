package antifraud.dtos;

import javax.validation.constraints.Pattern;

public class AddIPRequest {

    @Pattern(regexp = "^((\\d|[1-9]\\d|1\\d{2}|2[0-5]{2})\\.){3}(\\d|[1-9]\\d|1\\d{2}|2[0-5]{2})$")
    private String ip;

    public AddIPRequest() {
    }

    public AddIPRequest(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "AddIPRequest{" +
                "ip='" + ip + '\'' +
                '}';
    }
}
