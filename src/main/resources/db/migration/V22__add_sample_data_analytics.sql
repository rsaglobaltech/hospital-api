-- Sample data for analytics bounded context

-- Sample dashboards
INSERT INTO dashboards (id, name, description, created_at, updated_at, created_by, data)
VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d500', 'ER_WaitTimes', 'Emergency Room Wait Times Dashboard', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 
     '{"currentWaitTime": 35, "averageWaitTime": 42, "waitTimeByPriority": {"Critical": 5, "High": 15, "Medium": 45, "Low": 90}, "waitTimeByHour": {"00:00": 25, "06:00": 20, "12:00": 55, "18:00": 65}}'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d501', 'BedOccupancy', 'Hospital Bed Occupancy Dashboard', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 
     '{"totalBeds": 500, "occupiedBeds": 425, "occupancyRate": 85.0, "occupancyByDepartment": {"ICU": 95.0, "Cardiology": 88.0, "Neurology": 82.0, "Orthopedics": 75.0, "Pediatrics": 70.0}}');

-- Sample dashboard widgets
INSERT INTO dashboard_widgets (dashboard_id, title, widget_type, position_x, position_y, width, height, data_source, config)
VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d500', 'Current Wait Time', 'gauge', 0, 0, 6, 4, 'er_wait_times', '{"min": 0, "max": 120, "units": "minutes"}'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d500', 'Wait Time by Priority', 'bar_chart', 6, 0, 6, 4, 'er_wait_times_by_priority', '{"xAxis": "Priority", "yAxis": "Minutes"}'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d500', 'Wait Time by Hour', 'line_chart', 0, 4, 12, 4, 'er_wait_times_by_hour', '{"xAxis": "Hour", "yAxis": "Minutes"}'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d501', 'Bed Occupancy Rate', 'gauge', 0, 0, 6, 4, 'bed_occupancy', '{"min": 0, "max": 100, "units": "%"}'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d501', 'Occupancy by Department', 'bar_chart', 6, 0, 6, 4, 'bed_occupancy_by_department', '{"xAxis": "Department", "yAxis": "Occupancy %"}');

-- Sample KPIs
INSERT INTO kpis (id, name, description, unit, query, created_at, updated_at, created_by, thresholds)
VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d502', 'AvgLengthOfStay', 'Average Length of Stay in Days', 'days', 
     'SELECT AVG(length_of_stay) FROM admissions WHERE discharge_date BETWEEN :start_date AND :end_date', 
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', '{"warning": 5.0, "critical": 7.0}'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d503', 'ReadmissionRate', '30-day Readmission Rate', 'percentage', 
     'SELECT COUNT(DISTINCT patient_id) / (SELECT COUNT(DISTINCT patient_id) FROM admissions) * 100 FROM admissions WHERE admission_date BETWEEN :start_date AND :end_date AND is_readmission = true', 
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', '{"warning": 15.0, "critical": 20.0}'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d504', 'MortalityRate', 'In-hospital Mortality Rate', 'percentage', 
     'SELECT COUNT(CASE WHEN discharge_disposition = ''Expired'' THEN 1 END) / COUNT(*) * 100 FROM admissions WHERE discharge_date BETWEEN :start_date AND :end_date', 
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', '{"warning": 3.0, "critical": 5.0}');

-- Sample KPI values
INSERT INTO kpi_values (kpi_id, kpi_value, time_period, calculated_at)
VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d502', 4.8, 'lastMonth', CURRENT_TIMESTAMP),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d502', 5.2, 'lastQuarter', CURRENT_TIMESTAMP),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d503', 12.5, 'lastMonth', CURRENT_TIMESTAMP),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d503', 14.2, 'lastQuarter', CURRENT_TIMESTAMP),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d504', 2.8, 'lastMonth', CURRENT_TIMESTAMP),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d504', 3.1, 'lastQuarter', CURRENT_TIMESTAMP);

-- Sample reports
INSERT INTO reports (id, name, description, query, parameters, created_at, updated_at, created_by, report_format)
VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d505', 'MonthlyAdmissions', 'Monthly Admissions Report', 
     'SELECT department, COUNT(*) as admission_count FROM admissions WHERE admission_date BETWEEN :start_date AND :end_date GROUP BY department', 
     '{"start_date": {"type": "date", "required": true}, "end_date": {"type": "date", "required": true}, "unitId": {"type": "string", "required": false}}', 
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'JSON'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d506', 'LengthOfStay', 'Length of Stay Report', 
     'SELECT department, AVG(length_of_stay) as avg_los FROM admissions WHERE discharge_date BETWEEN :start_date AND :end_date GROUP BY department', 
     '{"start_date": {"type": "date", "required": true}, "end_date": {"type": "date", "required": true}, "unitId": {"type": "string", "required": false}}', 
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'JSON');

-- Sample report executions
INSERT INTO report_executions (report_id, filter_params, result, executed_at, executed_by, execution_time_ms)
VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d505', 
     '{"start_date": "2023-04-01", "end_date": "2023-04-30"}', 
     '{"totalAdmissions": 120, "emergencyAdmissions": 45, "scheduledAdmissions": 75, "byDepartment": {"Cardiology": 30, "Neurology": 25, "Orthopedics": 20, "Pediatrics": 15, "Other": 30}}', 
     CURRENT_TIMESTAMP, 'system', 250),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d505', 
     '{"start_date": "2023-04-01", "end_date": "2023-04-30", "unitId": "Cardiology"}', 
     '{"totalAdmissions": 30, "emergencyAdmissions": 12, "scheduledAdmissions": 18, "byDepartment": {"Cardiology": 30}}', 
     CURRENT_TIMESTAMP, 'system', 220),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d506', 
     '{"start_date": "2023-04-01", "end_date": "2023-04-30"}', 
     '{"averageLengthOfStay": 4.5, "byDepartment": {"Cardiology": 5.2, "Neurology": 6.1, "Orthopedics": 3.8, "Pediatrics": 2.5, "Other": 4.0}}', 
     CURRENT_TIMESTAMP, 'system', 280);

-- Sample prediction models
INSERT INTO prediction_models (id, name, description, version, algorithm, accuracy, created_at, updated_at, required_params, model_metadata)
VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d507', 'readmissionRisk', '30-day Readmission Risk Prediction', '1.0', 'Random Forest', 0.85, 
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '["patientId"]', 
     '{"features": ["age", "previous_admissions", "comorbidities", "length_of_stay", "medications"], "training_date": "2023-01-15"}'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d508', 'lengthOfStayPredictor', 'Expected Length of Stay Prediction', '1.0', 'Gradient Boosting', 0.78, 
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '["patientId", "diagnosisCode"]', 
     '{"features": ["age", "diagnosis", "comorbidities", "admission_type"], "training_date": "2023-02-10"}'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d509', 'sepsisRisk', 'Early Sepsis Risk Detection', '1.0', 'LSTM Neural Network', 0.92, 
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '["patientId", "vitalSigns"]', 
     '{"features": ["vital_signs", "lab_results", "medical_history"], "training_date": "2023-03-05"}');

-- Sample predictions
INSERT INTO predictions (id, model_id, patient_id, context_params, result, confidence, generated_at)
VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d510', 'f47ac10b-58cc-4372-a567-0e02b2c3d507', '11111111-1111-1111-1111-111111111111', 
     '{"patientId": "11111111-1111-1111-1111-111111111111"}', 
     '{"risk": 0.35, "riskLevel": "Medium", "factors": ["Previous admissions", "Comorbidities", "Medication adherence"]}', 
     0.85, CURRENT_TIMESTAMP),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d511', 'f47ac10b-58cc-4372-a567-0e02b2c3d508', '22222222-2222-2222-2222-222222222222', 
     '{"patientId": "22222222-2222-2222-2222-222222222222", "diagnosisCode": "J18.9"}', 
     '{"predictedLOS": 5.2, "factors": ["Diagnosis", "Age", "Comorbidities"]}', 
     0.78, CURRENT_TIMESTAMP),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d512', 'f47ac10b-58cc-4372-a567-0e02b2c3d509', '33333333-3333-3333-3333-444444444444', 
     '{"patientId": "33333333-3333-3333-3333-444444444444", "vitalSigns": {"temperature": 38.5, "heartRate": 110, "respiratoryRate": 22, "bloodPressure": "110/70"}}', 
     '{"risk": 0.15, "riskLevel": "Low", "factors": ["Vital signs", "Lab results", "Medical history"], "recommendedActions": ["Monitor vital signs"]}', 
     0.92, CURRENT_TIMESTAMP);

-- Sample saved queries
INSERT INTO saved_queries (id, name, description, query, created_at, updated_at, created_by, parameters)
VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d513', 'PatientAdmissionHistory', 'Query to get a patient''s admission history', 
     'SELECT * FROM admissions WHERE patient_id = :patient_id ORDER BY admission_date DESC', 
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 
     '{"patient_id": {"type": "string", "required": true}}'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d514', 'DepartmentPerformance', 'Query to analyze department performance metrics', 
     'SELECT department, COUNT(*) as admission_count, AVG(length_of_stay) as avg_los FROM admissions WHERE admission_date BETWEEN :start_date AND :end_date GROUP BY department', 
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 
     '{"start_date": {"type": "date", "required": true}, "end_date": {"type": "date", "required": true}}');

-- Sample query executions
INSERT INTO query_executions (query_id, query_text, parameters, result, row_count, executed_at, executed_by, execution_time_ms)
VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d513', 
     'SELECT * FROM admissions WHERE patient_id = :patient_id ORDER BY admission_date DESC', 
     '{"patient_id": "11111111-1111-1111-1111-111111111111"}', 
     '[{"id": "adm-001", "patient_id": "11111111-1111-1111-1111-111111111111", "admission_date": "2023-03-15", "discharge_date": "2023-03-20", "length_of_stay": 5, "department": "Cardiology"}, {"id": "adm-002", "patient_id": "11111111-1111-1111-1111-111111111111", "admission_date": "2023-01-10", "discharge_date": "2023-01-15", "length_of_stay": 5, "department": "Neurology"}]', 
     2, CURRENT_TIMESTAMP, 'system', 180),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d514', 
     'SELECT department, COUNT(*) as admission_count, AVG(length_of_stay) as avg_los FROM admissions WHERE admission_date BETWEEN :start_date AND :end_date GROUP BY department', 
     '{"start_date": "2023-01-01", "end_date": "2023-03-31"}', 
     '[{"department": "Cardiology", "admission_count": 120, "avg_los": 5.2}, {"department": "Neurology", "admission_count": 95, "avg_los": 6.1}, {"department": "Orthopedics", "admission_count": 85, "avg_los": 3.8}, {"department": "Pediatrics", "admission_count": 70, "avg_los": 2.5}, {"department": "Other", "admission_count": 110, "avg_los": 4.0}]', 
     5, CURRENT_TIMESTAMP, 'system', 250);
