-- Inventory table for pharmacy bounded context
CREATE TABLE inventory (
    medication_id VARCHAR(36) PRIMARY KEY,
    total_quantity INT NOT NULL,
    available_quantity INT NOT NULL,
    low_stock_threshold INT NOT NULL,
    FOREIGN KEY (medication_id) REFERENCES medications(id)
);

-- Create indexes
CREATE INDEX idx_inventory_available_quantity ON inventory(available_quantity);