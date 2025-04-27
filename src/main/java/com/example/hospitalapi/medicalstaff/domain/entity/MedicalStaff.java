package com.example.hospitalapi.medicalstaff.domain.entity;

import com.example.hospitalapi.medicalstaff.domain.valueobject.HireDate;
import com.example.hospitalapi.medicalstaff.domain.valueobject.Qualification;
import com.example.hospitalapi.medicalstaff.domain.valueobject.Specialty;
import com.example.hospitalapi.medicalstaff.domain.valueobject.StaffId;
import com.example.hospitalapi.medicalstaff.domain.valueobject.StaffStatus;
import com.example.hospitalapi.medicalstaff.domain.valueobject.TerminationDate;
import com.example.hospitalapi.patient.domain.valueobject.Address;
import com.example.hospitalapi.patient.domain.valueobject.DateOfBirth;
import com.example.hospitalapi.patient.domain.valueobject.Email;
import com.example.hospitalapi.patient.domain.valueobject.Name;
import com.example.hospitalapi.patient.domain.valueobject.PhoneNumber;
import com.example.hospitalapi.shared.domain.entity.AggregateRoot;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Entity representing a medical staff member in the hospital system
 */
@Getter
public class MedicalStaff extends AggregateRoot {
    private final StaffId id;
    private Name name;
    private Email email;
    private PhoneNumber phoneNumber;
    private DateOfBirth dateOfBirth;
    private Address address;
    private Specialty specialty;
    private final List<Qualification> qualifications;
    private StaffStatus status;
    private HireDate hireDate;
    private Optional<TerminationDate> terminationDate;

    public MedicalStaff(StaffId id, Name name, Email email, PhoneNumber phoneNumber, 
                       LocalDate dateOfBirthValue, String addressValue, Specialty specialty,
                       Qualification qualification, LocalDate hireDateValue) {
        if (id == null) {
            throw new IllegalArgumentException("Staff ID cannot be null");
        }
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        if (phoneNumber == null) {
            throw new IllegalArgumentException("Phone number cannot be null");
        }
        if (dateOfBirthValue == null) {
            throw new IllegalArgumentException("Date of birth cannot be null");
        }
        if (addressValue == null || addressValue.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }
        if (specialty == null) {
            throw new IllegalArgumentException("Specialty cannot be null");
        }
        if (qualification == null) {
            throw new IllegalArgumentException("At least one qualification is required");
        }
        if (hireDateValue == null) {
            throw new IllegalArgumentException("Hire date cannot be null");
        }

        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = new DateOfBirth(dateOfBirthValue);
        this.address = new Address(addressValue);
        this.specialty = specialty;
        this.qualifications = new ArrayList<>();
        this.qualifications.add(qualification);
        this.status = StaffStatus.active();
        this.hireDate = new HireDate(hireDateValue);
        this.terminationDate = Optional.empty();
    }

    public void updateName(Name name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.name = name;
    }

    public void updateEmail(Email email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        this.email = email;
    }

    public void updatePhoneNumber(PhoneNumber phoneNumber) {
        if (phoneNumber == null) {
            throw new IllegalArgumentException("Phone number cannot be null");
        }
        this.phoneNumber = phoneNumber;
    }

    public void updateAddress(String addressValue) {
        if (addressValue == null || addressValue.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }
        this.address = new Address(addressValue);
    }

    public void updateSpecialty(Specialty specialty) {
        if (specialty == null) {
            throw new IllegalArgumentException("Specialty cannot be null");
        }
        this.specialty = specialty;
    }

    public void addQualification(Qualification qualification) {
        if (qualification == null) {
            throw new IllegalArgumentException("Qualification cannot be null");
        }
        this.qualifications.add(qualification);
    }

    public List<Qualification> getQualifications() {
        return Collections.unmodifiableList(qualifications);
    }

    public void activate() {
        this.status = StaffStatus.active();
        this.terminationDate = Optional.empty();
    }

    public void deactivate(LocalDate terminationDateValue) {
        if (terminationDateValue == null) {
            throw new IllegalArgumentException("Termination date cannot be null");
        }
        if (terminationDateValue.isBefore(this.hireDate.getValue())) {
            throw new IllegalArgumentException("Termination date cannot be before hire date");
        }

        this.status = StaffStatus.inactive();
        this.terminationDate = Optional.of(new TerminationDate(terminationDateValue));
    }

    public boolean isActive() {
        return status.equals(StaffStatus.active());
    }

    public boolean hasExpiredLicense() {
        return qualifications.stream().anyMatch(Qualification::isExpired);
    }

    public boolean hasLicenseExpiringSoon(int monthsThreshold) {
        return qualifications.stream().anyMatch(q -> q.isExpiringSoon(monthsThreshold));
    }
}
