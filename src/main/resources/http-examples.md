# Hospital API HTTP Request Examples

This document provides examples of HTTP requests to the Hospital API. These examples can be used with tools like cURL, Postman, or any HTTP client.

## Authentication

### Login

```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "doctor1",
  "password": "password123"
}
```

Response:

```json
{
  "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user_id": "123e4567-e89b-12d3-a456-426614174000",
  "username": "doctor1",
  "roles": ["DOCTOR"]
}
```

### Refresh Token

```http
POST /api/v1/auth/refresh-token
Content-Type: application/json

{
  "username": "doctor1",
  "password": ""
}
```

Response:

```json
{
  "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user_id": "123e4567-e89b-12d3-a456-426614174000",
  "username": "doctor1",
  "roles": ["DOCTOR"]
}
```

## Scheduling

### Create Available Slot

```http
POST /api/v1/scheduling/slots
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

{
  "doctorId": "123e4567-e89b-12d3-a456-426614174001",
  "date": "2023-06-15",
  "startTime": "10:00:00",
  "endTime": "11:00:00"
}
```

Response:

```
123e4567-e89b-12d3-a456-426614174002
```

### Book Available Slot

```http
PUT /api/v1/scheduling/slots/123e4567-e89b-12d3-a456-426614174002/book
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Response: 200 OK

### Unbook Available Slot

```http
PUT /api/v1/scheduling/slots/123e4567-e89b-12d3-a456-426614174002/unbook
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Response: 200 OK

### Get Available Slot by ID

```http
GET /api/v1/scheduling/slots/123e4567-e89b-12d3-a456-426614174002
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Response:

```json
{
  "id": "123e4567-e89b-12d3-a456-426614174002",
  "doctorId": "123e4567-e89b-12d3-a456-426614174001",
  "date": "2023-06-15",
  "startTime": "10:00:00",
  "endTime": "11:00:00",
  "available": true
}
```

### Get Available Slots

```http
GET /api/v1/scheduling/slots?doctorId=123e4567-e89b-12d3-a456-426614174001&date=2023-06-15
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Response:

```json
[
  {
    "id": "123e4567-e89b-12d3-a456-426614174002",
    "doctorId": "123e4567-e89b-12d3-a456-426614174001",
    "date": "2023-06-15",
    "startTime": "10:00:00",
    "endTime": "11:00:00",
    "available": true
  },
  {
    "id": "123e4567-e89b-12d3-a456-426614174003",
    "doctorId": "123e4567-e89b-12d3-a456-426614174001",
    "date": "2023-06-15",
    "startTime": "11:00:00",
    "endTime": "12:00:00",
    "available": false
  }
]
```

## Appointments

### Create Appointment

```http
POST /api/v1/appointments
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

{
  "patientId": "123e4567-e89b-12d3-a456-426614174000",
  "doctorId": "123e4567-e89b-12d3-a456-426614174001",
  "startTime": "2023-06-15T10:00:00",
  "endTime": "2023-06-15T11:00:00",
  "reason": "Annual check-up"
}
```

Response:

```
123e4567-e89b-12d3-a456-426614174004
```

### Update Appointment

```http
PUT /api/v1/appointments/123e4567-e89b-12d3-a456-426614174004
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

{
  "startTime": "2023-06-15T10:30:00",
  "endTime": "2023-06-15T11:30:00",
  "reason": "Annual check-up",
  "notes": "Patient has a history of high blood pressure"
}
```

Response: 200 OK

### Cancel Appointment

```http
PUT /api/v1/appointments/123e4567-e89b-12d3-a456-426614174004/cancel
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Response: 200 OK

### Get Appointment by ID

```http
GET /api/v1/appointments/123e4567-e89b-12d3-a456-426614174004
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Response:

```json
{
  "id": "123e4567-e89b-12d3-a456-426614174004",
  "patientId": "123e4567-e89b-12d3-a456-426614174000",
  "doctorId": "123e4567-e89b-12d3-a456-426614174001",
  "startTime": "2023-06-15T10:30:00",
  "endTime": "2023-06-15T11:30:00",
  "reason": "Annual check-up",
  "notes": "Patient has a history of high blood pressure",
  "status": "SCHEDULED"
}
```

### Get All Appointments

```http
GET /api/v1/appointments
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Response:

```json
[
  {
    "id": "123e4567-e89b-12d3-a456-426614174004",
    "patientId": "123e4567-e89b-12d3-a456-426614174000",
    "doctorId": "123e4567-e89b-12d3-a456-426614174001",
    "startTime": "2023-06-15T10:30:00",
    "endTime": "2023-06-15T11:30:00",
    "reason": "Annual check-up",
    "notes": "Patient has a history of high blood pressure",
    "status": "SCHEDULED"
  }
]
```

## Medical Staff

### Create Medical Staff

```http
POST /api/v1/medical-staff
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "+1234567890",
  "dateOfBirth": "1980-01-01",
  "address": "123 Main St, City, Country",
  "specialty": "CARDIOLOGY",
  "degree": "MD",
  "institution": "Medical University",
  "dateObtained": "2005-05-15",
  "licenseNumber": "MED12345",
  "licenseExpiryDate": "2025-05-15",
  "hireDate": "2010-01-15"
}
```

Response:

```
123e4567-e89b-12d3-a456-426614174001
```

### Update Medical Staff

```http
PUT /api/v1/medical-staff/123e4567-e89b-12d3-a456-426614174001
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "+1234567890",
  "address": "456 New St, City, Country",
  "specialty": "CARDIOLOGY"
}
```

Response: 200 OK

### Deactivate Medical Staff

```http
PUT /api/v1/medical-staff/123e4567-e89b-12d3-a456-426614174001/deactivate
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

{
  "terminationDate": "2023-06-30"
}
```

Response: 200 OK

### Get Medical Staff by ID

```http
GET /api/v1/medical-staff/123e4567-e89b-12d3-a456-426614174001
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Response:

```json
{
  "id": "123e4567-e89b-12d3-a456-426614174001",
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "+1234567890",
  "dateOfBirth": "1980-01-01",
  "address": "456 New St, City, Country",
  "specialty": "CARDIOLOGY",
  "qualifications": [
    {
      "degree": "MD",
      "institution": "Medical University",
      "dateObtained": "2005-05-15",
      "licenseNumber": "MED12345",
      "licenseExpiryDate": "2025-05-15"
    }
  ],
  "active": true,
  "hireDate": "2010-01-15",
  "terminationDate": null
}
```

### Get All Medical Staff

```http
GET /api/v1/medical-staff
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Response:

```json
[
  {
    "id": "123e4567-e89b-12d3-a456-426614174001",
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "+1234567890",
    "dateOfBirth": "1980-01-01",
    "address": "456 New St, City, Country",
    "specialty": "CARDIOLOGY",
    "qualifications": [
      {
        "degree": "MD",
        "institution": "Medical University",
        "dateObtained": "2005-05-15",
        "licenseNumber": "MED12345",
        "licenseExpiryDate": "2025-05-15"
      }
    ],
    "active": true,
    "hireDate": "2010-01-15",
    "terminationDate": null
  }
]
```

### Get Medical Staff by Specialty

```http
GET /api/v1/medical-staff/specialty/CARDIOLOGY
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Response:

```json
[
  {
    "id": "123e4567-e89b-12d3-a456-426614174001",
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "+1234567890",
    "dateOfBirth": "1980-01-01",
    "address": "456 New St, City, Country",
    "specialty": "CARDIOLOGY",
    "qualifications": [
      {
        "degree": "MD",
        "institution": "Medical University",
        "dateObtained": "2005-05-15",
        "licenseNumber": "MED12345",
        "licenseExpiryDate": "2025-05-15"
      }
    ],
    "active": true,
    "hireDate": "2010-01-15",
    "terminationDate": null
  }
]
```

## Patients

### Create Patient

```http
POST /api/v1/patients
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

{
  "firstName": "Jane",
  "lastName": "Smith",
  "email": "jane.smith@example.com",
  "phoneNumber": "+1987654321",
  "dateOfBirth": "1990-05-15",
  "address": "789 Oak St, City, Country"
}
```

Response:

```
123e4567-e89b-12d3-a456-426614174000
```

### Update Patient

```http
PUT /api/v1/patients/123e4567-e89b-12d3-a456-426614174000
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

{
  "firstName": "Jane",
  "lastName": "Smith",
  "email": "jane.smith@example.com",
  "phoneNumber": "+1987654321",
  "dateOfBirth": "1990-05-15",
  "address": "789 Oak St, City, Country",
  "medicalHistory": "Allergic to penicillin"
}
```

Response: 200 OK

### Delete Patient

```http
DELETE /api/v1/patients/123e4567-e89b-12d3-a456-426614174000
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Response: 204 No Content

### Get Patient by ID

```http
GET /api/v1/patients/123e4567-e89b-12d3-a456-426614174000
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Response:

```json
{
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "firstName": "Jane",
  "lastName": "Smith",
  "email": "jane.smith@example.com",
  "phoneNumber": "+1987654321",
  "dateOfBirth": "1990-05-15",
  "address": "789 Oak St, City, Country",
  "medicalHistory": "Allergic to penicillin"
}
```

### Get All Patients

```http
GET /api/v1/patients
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Response:

```json
[
  {
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "jane.smith@example.com",
    "phoneNumber": "+1987654321",
    "dateOfBirth": "1990-05-15",
    "address": "789 Oak St, City, Country",
    "medicalHistory": "Allergic to penicillin"
  }
]
```
