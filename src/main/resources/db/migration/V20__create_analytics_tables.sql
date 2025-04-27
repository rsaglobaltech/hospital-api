-- Analytics bounded context

-- Dashboards table
CREATE TABLE dashboards (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    data TEXT
);

-- Dashboard widgets table
CREATE TABLE dashboard_widgets (
    id SERIAL PRIMARY KEY,
    dashboard_id VARCHAR(36) NOT NULL,
    title VARCHAR(255) NOT NULL,
    widget_type VARCHAR(50) NOT NULL,
    position_x INT NOT NULL,
    position_y INT NOT NULL,
    width INT NOT NULL,
    height INT NOT NULL,
    data_source VARCHAR(255),
    config TEXT,
    FOREIGN KEY (dashboard_id) REFERENCES dashboards(id) ON DELETE CASCADE
);

-- KPIs table
CREATE TABLE kpis (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    unit VARCHAR(50),
    query TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    thresholds TEXT
);

-- KPI values table (historical values)
CREATE TABLE kpi_values (
    id SERIAL PRIMARY KEY,
    kpi_id VARCHAR(36) NOT NULL,
    kpi_value DECIMAL(20, 4),
    time_period VARCHAR(50) NOT NULL,
    calculated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (kpi_id) REFERENCES kpis(id) ON DELETE CASCADE
);

-- Reports table
CREATE TABLE reports (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    query TEXT,
    parameters TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    report_format VARCHAR(50) DEFAULT 'JSON'
);

-- Report executions table (historical executions)
CREATE TABLE report_executions (
    id SERIAL PRIMARY KEY,
    report_id VARCHAR(36) NOT NULL,
    filter_params TEXT,
    result TEXT,
    executed_at TIMESTAMP NOT NULL,
    executed_by VARCHAR(255),
    execution_time_ms INT,
    FOREIGN KEY (report_id) REFERENCES reports(id) ON DELETE CASCADE
);

-- Prediction models table
CREATE TABLE prediction_models (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    version VARCHAR(50) NOT NULL,
    algorithm VARCHAR(255),
    accuracy DECIMAL(5, 4),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    required_params TEXT,
    model_metadata TEXT
);

-- Predictions table (historical predictions)
CREATE TABLE predictions (
    id VARCHAR(36) PRIMARY KEY,
    model_id VARCHAR(36) NOT NULL,
    patient_id VARCHAR(36),
    context_params TEXT,
    result TEXT,
    confidence DECIMAL(5, 4),
    generated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (model_id) REFERENCES prediction_models(id),
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);

-- Saved queries table
CREATE TABLE saved_queries (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    query TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    parameters TEXT
);

-- Query executions table (historical executions)
CREATE TABLE query_executions (
    id SERIAL PRIMARY KEY,
    query_id VARCHAR(36),
    query_text TEXT NOT NULL,
    parameters TEXT,
    result TEXT,
    row_count INT,
    executed_at TIMESTAMP NOT NULL,
    executed_by VARCHAR(255),
    execution_time_ms INT,
    FOREIGN KEY (query_id) REFERENCES saved_queries(id) ON DELETE SET NULL
);

-- Create indexes
CREATE INDEX idx_dashboards_name ON dashboards(name);
CREATE INDEX idx_dashboard_widgets_dashboard_id ON dashboard_widgets(dashboard_id);
CREATE INDEX idx_kpis_name ON kpis(name);
CREATE INDEX idx_kpi_values_kpi_id ON kpi_values(kpi_id);
CREATE INDEX idx_kpi_values_time_period ON kpi_values(time_period);
CREATE INDEX idx_reports_name ON reports(name);
CREATE INDEX idx_report_executions_report_id ON report_executions(report_id);
CREATE INDEX idx_prediction_models_name ON prediction_models(name);
CREATE INDEX idx_predictions_model_id ON predictions(model_id);
CREATE INDEX idx_predictions_patient_id ON predictions(patient_id);
CREATE INDEX idx_saved_queries_name ON saved_queries(name);
CREATE INDEX idx_query_executions_query_id ON query_executions(query_id);
