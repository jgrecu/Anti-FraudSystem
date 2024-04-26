package eu.jgrecu.antifraud.service;

import eu.jgrecu.antifraud.dtos.AddIPRequest;
import eu.jgrecu.antifraud.dtos.AddIpResponse;
import eu.jgrecu.antifraud.dtos.StatusResponse;
import eu.jgrecu.antifraud.exceptions.BadRequestException;
import eu.jgrecu.antifraud.exceptions.HttpConflictException;
import eu.jgrecu.antifraud.model.IpAddress;
import eu.jgrecu.antifraud.repository.IpAddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IpAddressService {

    private final IpAddressRepository repository;

    public IpAddressService(IpAddressRepository repository) {
        this.repository = repository;
    }

    public Optional<AddIpResponse> addIpAddress(AddIPRequest addIPRequest) {
        Optional<IpAddress> optionalIpAddress = repository.findByIp(addIPRequest.getIp());
        if (optionalIpAddress.isPresent()) {
            throw new HttpConflictException("Duplicate IP");
        }

        IpAddress ipAddress = new IpAddress();
        ipAddress.setIp(addIPRequest.getIp());
        IpAddress savedIpAddress = repository.save(ipAddress);
        return Optional.of(new AddIpResponse(savedIpAddress));
    }

    public Optional<StatusResponse> deleteSuspiciousIP(String ip) {

        boolean matches = ip.matches("^((\\d|[1-9]\\d|1\\d{2}|2[0-5]{2})\\.){3}(\\d|[1-9]\\d|1\\d{2}|2[0-5]{2})$");
        if (!matches) {
            throw new BadRequestException("Invalid IP format");
        }

        Optional<IpAddress> optionalIpAddress = repository.findByIp(ip);

        if (optionalIpAddress.isPresent()) {
            IpAddress ipAddress = optionalIpAddress.get();
            repository.delete(ipAddress);
            return Optional.of(new StatusResponse("IP %s successfully removed!".formatted(ipAddress.getIp())));
        } else {
            return Optional.empty();
        }
    }

    public List<AddIpResponse> getAllSuspiciousIPs() {
        return repository.findAll().stream().map(AddIpResponse::new).collect(Collectors.toList());
    }
}
