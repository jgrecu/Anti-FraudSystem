package eu.jgrecu.antifraud.repository;

import eu.jgrecu.antifraud.model.IpAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IpAddressRepository extends JpaRepository<IpAddress, Long> {

    Optional<IpAddress> findByIp(String ip);
}
