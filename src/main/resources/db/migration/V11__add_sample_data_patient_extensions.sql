-- Sample data for patient extensions

-- Update patients with insurance information
UPDATE patients SET 
    insurance_provider = 'Blue Cross',
    insurance_policy_number = 'BC123456',
    insurance_expiry_date = '2024-12-31'
WHERE id = '11111111-1111-1111-1111-111111111111';

UPDATE patients SET 
    insurance_provider = 'Aetna',
    insurance_policy_number = 'AE789012',
    insurance_expiry_date = '2025-06-30'
WHERE id = '22222222-2222-2222-2222-222222222222';

UPDATE patients SET 
    insurance_provider = 'United Healthcare',
    insurance_policy_number = 'UH345678',
    insurance_expiry_date = '2024-09-15'
WHERE id = '33333333-3333-3333-3333-444444444444';

UPDATE patients SET 
    insurance_provider = 'Cigna',
    insurance_policy_number = 'CI901234',
    insurance_expiry_date = '2025-03-20'
WHERE id = '44444444-4444-4444-4444-555555555555';

-- Update patients with emergency contact information
UPDATE patients SET 
    emergency_contact_name = 'Mary Doe',
    emergency_contact_phone = '+1987654321',
    emergency_contact_relationship = 'Spouse'
WHERE id = '11111111-1111-1111-1111-111111111111';

UPDATE patients SET 
    emergency_contact_name = 'Robert Smith',
    emergency_contact_phone = '+1122334455',
    emergency_contact_relationship = 'Husband'
WHERE id = '22222222-2222-2222-2222-222222222222';

UPDATE patients SET 
    emergency_contact_name = 'Patricia Johnson',
    emergency_contact_phone = '+5566778899',
    emergency_contact_relationship = 'Wife'
WHERE id = '33333333-3333-3333-3333-444444444444';

UPDATE patients SET 
    emergency_contact_name = 'David Brown',
    emergency_contact_phone = '+9988776655',
    emergency_contact_relationship = 'Brother'
WHERE id = '44444444-4444-4444-4444-555555555555';

-- Update patients with preferences
UPDATE patients SET 
    preferred_language = 'English',
    preferred_communication_method = 'Email',
    special_needs = NULL
WHERE id = '11111111-1111-1111-1111-111111111111';

UPDATE patients SET 
    preferred_language = 'Spanish',
    preferred_communication_method = 'Phone',
    special_needs = 'Requires wheelchair access'
WHERE id = '22222222-2222-2222-2222-222222222222';

UPDATE patients SET 
    preferred_language = 'English',
    preferred_communication_method = 'SMS',
    special_needs = NULL
WHERE id = '33333333-3333-3333-3333-444444444444';

UPDATE patients SET 
    preferred_language = 'French',
    preferred_communication_method = 'Email',
    special_needs = 'Hearing impaired, needs written instructions'
WHERE id = '44444444-4444-4444-4444-555555555555';

-- Update audit information
UPDATE patients SET 
    created_at = '2023-01-15 10:30:00',
    updated_at = '2023-05-20 14:45:00',
    created_by = 'system',
    updated_by = 'admin'
WHERE id IN ('11111111-1111-1111-1111-111111111111', '22222222-2222-2222-2222-222222222222', '33333333-3333-3333-3333-444444444444', '44444444-4444-4444-4444-555555555555');