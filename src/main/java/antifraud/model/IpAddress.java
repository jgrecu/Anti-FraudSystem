package antifraud.model;

import javax.persistence.*;

@Entity
public class IpAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ip_address", nullable = false)
    private String ip;

    public IpAddress() {
    }

    public IpAddress(Long id, String ip) {
        this.id = id;
        this.ip = ip;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "IpAddress{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                '}';
    }
}
