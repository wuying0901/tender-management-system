IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='tender_projects' AND xtype='U')
BEGIN
    CREATE TABLE tender_projects (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        project_id NVARCHAR(255) NOT NULL UNIQUE,
        title NVARCHAR(255) NOT NULL,
        description NVARCHAR(1000),
        bidder NVARCHAR(255) NOT NULL,
        agency NVARCHAR(255),
        location NVARCHAR(255) NOT NULL,
        category NVARCHAR(255) NOT NULL,
        fund_source NVARCHAR(255),
        project_scope NVARCHAR(1000),
        qualification NVARCHAR(1000),
        registration_start_time DATETIME2,
        registration_end_time DATETIME2,
        document_start_time DATETIME2,
        document_end_time DATETIME2,
        clarification_end_time DATETIME2,
        bid_deadline DATETIME2 NOT NULL,
        estimated_amount NVARCHAR(255),
        supervisor_name NVARCHAR(255),
        supervisor_phone NVARCHAR(50),
        contact_name NVARCHAR(255),
        contact_phone NVARCHAR(50),
        contact_email NVARCHAR(255),
        contact_address NVARCHAR(500),
        department NVARCHAR(255),
        position NVARCHAR(255),
        publish_time DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        create_time DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        update_time DATETIME2,
        is_active BIT NOT NULL DEFAULT 1
    );
END

