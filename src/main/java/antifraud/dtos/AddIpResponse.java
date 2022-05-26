package antifraud.dtos;

import antifraud.model.IpAddress;

public class AddIpResponse {
    private final Long id;
    private final String ip;

    public AddIpResponse(IpAddress ipAddress) {
        this.id = ipAddress.getId();
        this.ip = ipAddress.getIp();
    }

    public Long getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    @Override
    public String toString() {
        return "AddIpResponse{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                '}';
    }
}
