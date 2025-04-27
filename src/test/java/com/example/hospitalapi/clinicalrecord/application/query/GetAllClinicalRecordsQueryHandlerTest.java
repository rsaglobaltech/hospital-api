package com.example.hospitalapi.clinicalrecord.application.query;

import com.example.hospitalapi.clinicalrecord.domain.entity.ClinicalRecord;
import com.example.hospitalapi.clinicalrecord.domain.repository.ClinicalRecordRepository;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordDescription;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordId;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordTitle;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordType;
import com.example.hospitalapi.medicalstaff.domain.valueobject.DoctorId;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.shared.domain.valueobject.Timestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GetAllClinicalRecordsQueryHandlerTest {

    @Mock
    private ClinicalRecordRepository clinicalRecordRepository;

    @Mock
    private ClinicalRecord clinicalRecord1;

    @Mock
    private ClinicalRecord clinicalRecord2;

    private GetAllClinicalRecordsQueryHandler handler;

    private final UUID clinicalRecordUuid1 = UUID.randomUUID();
    private final String clinicalRecordId1 = clinicalRecordUuid1.toString();
    private final UUID clinicalRecordUuid2 = UUID.randomUUID();
    private final String clinicalRecordId2 = clinicalRecordUuid2.toString();
    private final UUID patientUuid = UUID.randomUUID();
    private final String patientId = patientUuid.toString();
    private final DoctorId doctorId = new DoctorId(UUID.randomUUID());
    private final ClinicalRecordType type = ClinicalRecordType.DIAGNOSIS;
    private final ClinicalRecordTitle title1 = new ClinicalRecordTitle("Test Title 1");
    private final ClinicalRecordTitle title2 = new ClinicalRecordTitle("Test Title 2");
    private final ClinicalRecordDescription description1 = new ClinicalRecordDescription("Test Description 1");
    private final ClinicalRecordDescription description2 = new ClinicalRecordDescription("Test Description 2");
    private final Timestamp createdAt = new Timestamp(LocalDateTime.now().minusDays(1));
    private final Timestamp updatedAt = new Timestamp(LocalDateTime.now());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new GetAllClinicalRecordsQueryHandler(clinicalRecordRepository);

        // Setup mock clinical records
        when(clinicalRecord1.getId()).thenReturn(new ClinicalRecordId(clinicalRecordId1));
        when(clinicalRecord1.getPatientId()).thenReturn(new PatientId(patientId));
        when(clinicalRecord1.getDoctorId()).thenReturn(doctorId);
        when(clinicalRecord1.getType()).thenReturn(type);
        when(clinicalRecord1.getTitle()).thenReturn(title1);
        when(clinicalRecord1.getDescription()).thenReturn(description1);
        when(clinicalRecord1.getAttachmentUrl()).thenReturn(null);
        when(clinicalRecord1.getCreatedAt()).thenReturn(createdAt);
        when(clinicalRecord1.getUpdatedAt()).thenReturn(updatedAt);

        when(clinicalRecord2.getId()).thenReturn(new ClinicalRecordId(clinicalRecordId2));
        when(clinicalRecord2.getPatientId()).thenReturn(new PatientId(patientId));
        when(clinicalRecord2.getDoctorId()).thenReturn(doctorId);
        when(clinicalRecord2.getType()).thenReturn(type);
        when(clinicalRecord2.getTitle()).thenReturn(title2);
        when(clinicalRecord2.getDescription()).thenReturn(description2);
        when(clinicalRecord2.getAttachmentUrl()).thenReturn(null);
        when(clinicalRecord2.getCreatedAt()).thenReturn(createdAt);
        when(clinicalRecord2.getUpdatedAt()).thenReturn(updatedAt);
    }

    @Test
    void testHandleGetAllClinicalRecordsQuery() {
        // Arrange
        GetAllClinicalRecordsQuery query = new GetAllClinicalRecordsQuery();
        List<ClinicalRecord> clinicalRecords = Arrays.asList(clinicalRecord1, clinicalRecord2);

        when(clinicalRecordRepository.findAll()).thenReturn(clinicalRecords);

        // Act
        List<ClinicalRecordResponse> responses = handler.handle(query);

        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.size());
        
        ClinicalRecordResponse response1 = responses.get(0);
        assertEquals(clinicalRecordId1, response1.getId());
        assertEquals(patientId, response1.getPatientId());
        assertEquals(doctorId, response1.getDoctorId());
        assertEquals(type, response1.getType());
        assertEquals(title1, response1.getTitle());
        assertEquals(description1, response1.getDescription());
        
        ClinicalRecordResponse response2 = responses.get(1);
        assertEquals(clinicalRecordId2, response2.getId());
        assertEquals(title2, response2.getTitle());
        assertEquals(description2, response2.getDescription());

        verify(clinicalRecordRepository).findAll();
    }

    @Test
    void testHandleGetAllClinicalRecordsQueryWithTypeFilter() {
        // Arrange
        GetAllClinicalRecordsQuery query = new GetAllClinicalRecordsQuery(type);
        List<ClinicalRecord> clinicalRecords = Arrays.asList(clinicalRecord1, clinicalRecord2);

        when(clinicalRecordRepository.findByType(any(ClinicalRecordType.class))).thenReturn(clinicalRecords);

        // Act
        List<ClinicalRecordResponse> responses = handler.handle(query);

        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.size());
        
        verify(clinicalRecordRepository).findByType(any(ClinicalRecordType.class));
    }

    @Test
    void testHandleGetAllClinicalRecordsQueryWithNoRecords() {
        // Arrange
        GetAllClinicalRecordsQuery query = new GetAllClinicalRecordsQuery();

        when(clinicalRecordRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<ClinicalRecordResponse> responses = handler.handle(query);

        // Assert
        assertNotNull(responses);
        assertTrue(responses.isEmpty());
        
        verify(clinicalRecordRepository).findAll();
    }
}