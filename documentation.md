# Carcinogenic Product Identifier Application Documentation

## Table of Contents
1. [Project Overview](#project-overview)
2. [Technical Architecture](#technical-architecture)
3. [Database Design](#database-design)
4. [User Interface](#user-interface)
5. [API Integration](#api-integration)
6. [Security Features](#security-features)
7. [Development Setup](#development-setup)
8. [Build and Deployment](#build-and-deployment)
9. [Testing](#testing)
10. [Maintenance and Updates](#maintenance-and-updates)

## Project Overview

### Purpose
The Carcinogenic Product Identifier Application is designed to help users identify and track products that may contain potentially carcinogenic substances. The application provides a user-friendly interface for searching, analyzing, and managing product information.

### System Architecture Diagram
```mermaid
graph TB
    subgraph Client
        UI[User Interface]
    end
    
    subgraph Application
        Auth[Authentication]
        PM[Product Management]
        RA[Risk Assessment]
        DB[Database Layer]
    end
    
    subgraph External Services
        GenAI[Google GenAI]
        RegDB[Regulatory Database]
    end
    
    UI --> Auth
    UI --> PM
    UI --> RA
    PM --> DB
    RA --> DB
    RA --> GenAI
    PM --> RegDB
```

### Key Features
- Product search and identification
- Carcinogenic substance database
- User authentication and authorization
- Product risk assessment
- Data export capabilities
- Real-time updates

## Technical Architecture

### Technology Stack
- **Programming Language**: Java 21
- **Database**: SQLite
- **UI Framework**: Java Swing
- **Build Tool**: Maven
- **Dependencies**:
  - Google GenAI SDK
  - SQLite JDBC Driver
  - Gson for JSON handling
  - JUnit for testing

### Component Interaction Diagram
```mermaid
sequenceDiagram
    participant User
    participant UI
    participant Auth
    participant PM
    participant DB
    participant GenAI
    
    User->>UI: Login Request
    UI->>Auth: Validate Credentials
    Auth->>DB: Check User
    DB-->>Auth: User Data
    Auth-->>UI: Auth Result
    UI-->>User: Login Response
    
    User->>UI: Search Product
    UI->>PM: Search Request
    PM->>DB: Query Database
    DB-->>PM: Product Data
    PM->>GenAI: Risk Assessment
    GenAI-->>PM: Assessment Result
    PM-->>UI: Search Results
    UI-->>User: Display Results
```

### Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── CarcinogenicProductIdentifier/
│   │               ├── Main.java
│   │               ├── database/
│   │               ├── model/
│   │               └── ui/
│   └── resources/
└── test/
    └── java/
        └── com/
            └── example/
                └── CarcinogenicProductIdentifier/
                    └── tests/
```

## Database Design

### Database Schema Diagram
```mermaid
erDiagram
    Products ||--o{ Product_Substances : contains
    Carcinogenic_Substances ||--o{ Product_Substances : found_in
    Users ||--o{ Products : manages
    
    Products {
        int product_id PK
        string name
        string manufacturer
        string category
        string description
        string risk_level
        datetime last_updated
    }
    
    Carcinogenic_Substances {
        int substance_id PK
        string name
        string chemical_formula
        string risk_category
        string description
        string regulatory_status
    }
    
    Product_Substances {
        int product_id FK
        int substance_id FK
        float concentration
        datetime detection_date
    }
    
    Users {
        int user_id PK
        string username
        string password_hash
        string role
        datetime last_login
    }
```

### Schema Overview
The application uses SQLite for data storage with the following main tables:

1. **Products**
   - Product ID (Primary Key)
   - Name
   - Manufacturer
   - Category
   - Description
   - Risk Level
   - Last Updated

2. **Carcinogenic Substances**
   - Substance ID (Primary Key)
   - Name
   - Chemical Formula
   - Risk Category
   - Description
   - Regulatory Status

3. **Product_Substances**
   - Product ID (Foreign Key)
   - Substance ID (Foreign Key)
   - Concentration
   - Detection Date

4. **Users**
   - User ID (Primary Key)
   - Username
   - Password Hash
   - Role
   - Last Login

### Data Flow Diagram
```mermaid
flowchart LR
    subgraph Input
        User[User Input]
        API[External API]
    end
    
    subgraph Processing
        Validation[Data Validation]
        Analysis[Risk Analysis]
        Storage[Data Storage]
    end
    
    subgraph Output
        Display[UI Display]
        Export[Data Export]
    end
    
    User --> Validation
    API --> Validation
    Validation --> Analysis
    Analysis --> Storage
    Storage --> Display
    Storage --> Export
```

### Database Operations
- CRUD operations for products and substances
- Search and filtering capabilities
- Data validation and integrity checks
- Backup and restore functionality

## User Interface

### UI Component Hierarchy
```mermaid
graph TD
    subgraph Main Window
        Login[Login Screen]
        Dashboard[Dashboard]
        ProductMgmt[Product Management]
        Reports[Reports]
    end
    
    subgraph Dashboard Components
        Search[Search Panel]
        QuickAccess[Quick Access]
        Status[Status Panel]
        Profile[Profile Management]
    end
    
    subgraph Product Management
        Add[Add Product]
        Edit[Edit Product]
        Delete[Delete Product]
        Import[Import/Export]
    end
    
    Main Window --> Login
    Main Window --> Dashboard
    Main Window --> ProductMgmt
    Main Window --> Reports
    
    Dashboard --> Search
    Dashboard --> QuickAccess
    Dashboard --> Status
    Dashboard --> Profile
    
    ProductMgmt --> Add
    ProductMgmt --> Edit
    ProductMgmt --> Delete
    ProductMgmt --> Import
```

### Login Screen
- Username/password authentication
- Password recovery option
- Remember me functionality
- Session management

### Main Dashboard
- Product search interface
- Quick access to common functions
- System status indicators
- User profile management

### Product Management
- Add/Edit/Delete products
- Bulk import/export
- Product categorization
- Risk assessment tools

### Reporting
- Custom report generation
- Data visualization
- Export options (PDF, CSV, Excel)
- Scheduled reports

## API Integration

### API Integration Diagram
```mermaid
graph LR
    subgraph Application
        Main[Main Application]
        GenAI[Google GenAI Client]
        RegAPI[Regulatory API Client]
    end
    
    subgraph External Services
        Google[Google GenAI Service]
        Regulatory[Regulatory Database]
    end
    
    Main --> GenAI
    Main --> RegAPI
    GenAI --> Google
    RegAPI --> Regulatory
```

### Google GenAI Integration
- Natural language processing for product analysis
- Risk assessment algorithms
- Text classification
- Sentiment analysis

### External APIs
- Product database synchronization
- Regulatory updates
- Market data integration
- Weather impact analysis

## Security Features

### Security Architecture
```mermaid
graph TB
    subgraph Security Layer
        Auth[Authentication]
        Authz[Authorization]
        Encrypt[Encryption]
        Audit[Audit Logging]
    end
    
    subgraph Application Layer
        UI[User Interface]
        Business[Business Logic]
        Data[Data Access]
    end
    
    UI --> Auth
    Auth --> Authz
    Business --> Authz
    Data --> Encrypt
    Auth --> Audit
    Authz --> Audit
```

### Authentication
- Secure password hashing
- Session management
- Token-based authentication
- Role-based access control

### Data Protection
- Encrypted database
- Secure data transmission
- Regular security audits
- Backup and recovery procedures

### Compliance
- GDPR compliance
- Data retention policies
- Privacy controls
- Audit logging

## Development Setup

### Prerequisites
- JDK 21 or later
- Maven 3.8 or later
- Git
- IDE (recommended: IntelliJ IDEA or Eclipse)

### Local Development
1. Clone the repository
2. Install dependencies:
   ```bash
   mvn clean install
   ```
3. Configure database:
   ```bash
   java -cp target/CarcinogenicProductIdentifier-1.0-SNAPSHOT.jar com.example.CarcinogenicProductIdentifier.database.DatabaseInitializer
   ```
4. Run the application:
   ```bash
   mvn exec:java
   ```

### Configuration
- Database connection settings
- API keys and endpoints
- Logging configuration
- Environment variables

## Build and Deployment

### Build Process
1. Update version in `pom.xml`
2. Run tests:
   ```bash
   mvn test
   ```
3. Build package:
   ```bash
   mvn clean package
   ```

### Deployment Steps
1. Prepare deployment package
2. Configure environment
3. Deploy database
4. Start application
5. Verify deployment

## Testing

### Unit Tests
- JUnit test cases
- Mock objects
- Test coverage analysis
- Continuous integration

### Integration Tests
- API integration testing
- Database integration
- UI testing
- Performance testing

### Test Environment
- Test database setup
- Mock services
- Test data generation
- Environment configuration

## Maintenance and Updates

### Regular Maintenance
- Database optimization
- Log rotation
- Performance monitoring
- Security updates

### Update Procedures
1. Backup current installation
2. Deploy new version
3. Run database migrations
4. Verify functionality
5. Rollback plan

### Monitoring
- Application logs
- Performance metrics
- Error tracking
- User feedback

## Troubleshooting Guide

### Common Issues
1. Database Connection Issues
   - Check database file permissions
   - Verify connection settings
   - Check disk space

2. Application Performance
   - Monitor memory usage
   - Check database indexes
   - Review query performance

3. UI Issues
   - Clear application cache
   - Update Java version
   - Check display settings

### Support Procedures
1. Error Logging
2. Issue Tracking
3. User Support
4. Bug Reporting

## Future Enhancements

### Planned Features
1. Mobile Application
2. Cloud Integration
3. Advanced Analytics
4. Machine Learning Integration

### Roadmap
- Version 2.0: Mobile Support
- Version 2.1: Cloud Integration
- Version 2.2: Advanced Analytics
- Version 2.3: Machine Learning Features

## Contributing

### Development Guidelines
- Code style guide
- Documentation requirements
- Testing requirements
- Pull request process

### Version Control
- Branch naming conventions
- Commit message format
- Release process
- Tag management

## License and Legal

### License Information
- Software license
- Third-party licenses
- Copyright notices
- Usage restrictions

### Compliance
- Regulatory requirements
- Industry standards
- Data protection laws
- Export controls

## Workflow Diagrams

### User Authentication Workflow
```mermaid
stateDiagram-v2
    [*] --> LoginScreen
    LoginScreen --> ValidateCredentials: Submit Credentials
    ValidateCredentials --> InvalidCredentials: Invalid
    ValidateCredentials --> CheckUserRole: Valid
    InvalidCredentials --> LoginScreen: Show Error
    CheckUserRole --> AdminDashboard: Admin
    CheckUserRole --> UserDashboard: Regular User
    AdminDashboard --> [*]
    UserDashboard --> [*]
```

### Product Management Workflow
```mermaid
stateDiagram-v2
    [*] --> Dashboard
    Dashboard --> SearchProducts: Search
    Dashboard --> AddNewProduct: Add Product
    Dashboard --> ViewReports: Reports
    
    SearchProducts --> ProductList: Results
    ProductList --> ViewProductDetails: Select
    ViewProductDetails --> EditProduct: Edit
    ViewProductDetails --> DeleteProduct: Delete
    ViewProductDetails --> AssessRisk: Assess
    
    AddNewProduct --> ProductForm: Fill Details
    ProductForm --> ValidateProduct: Submit
    ValidateProduct --> ProductList: Valid
    ValidateProduct --> ProductForm: Invalid
    
    EditProduct --> ProductForm
    DeleteProduct --> ConfirmDelete
    ConfirmDelete --> ProductList: Confirmed
    ConfirmDelete --> ViewProductDetails: Cancelled
    
    AssessRisk --> RiskAnalysis
    RiskAnalysis --> UpdateRiskLevel
    UpdateRiskLevel --> ProductList
```

### Risk Assessment Workflow
```mermaid
graph TB
    subgraph Input
        Product[Product Information]
        Substances[Known Substances]
        Regulations[Regulatory Data]
    end
    
    subgraph Analysis
        GenAI[Google GenAI Analysis]
        Manual[Manual Review]
        Compare[Compare with Standards]
    end
    
    subgraph Output
        RiskLevel[Risk Level]
        Report[Assessment Report]
        Recommendations[Recommendations]
    end
    
    Product --> GenAI
    Substances --> GenAI
    Regulations --> Compare
    
    GenAI --> Manual
    Manual --> Compare
    Compare --> RiskLevel
    Compare --> Report
    Compare --> Recommendations
```

### Report Generation Workflow
```mermaid
sequenceDiagram
    participant User
    participant UI
    participant ReportGen
    participant DB
    participant Export
    
    User->>UI: Request Report
    UI->>ReportGen: Generate Report
    ReportGen->>DB: Fetch Data
    DB-->>ReportGen: Data
    ReportGen->>ReportGen: Process Data
    ReportGen->>Export: Format Report
    Export-->>UI: Report Ready
    UI-->>User: Display Report
    
    alt Export
        User->>UI: Export Report
        UI->>Export: Export Request
        Export-->>User: Download File
    end
```

### Data Synchronization Workflow
```mermaid
graph LR
    subgraph Local System
        App[Application]
        LocalDB[(Local Database)]
    end
    
    subgraph External Systems
        RegDB[(Regulatory DB)]
        MarketDB[(Market Data)]
        WeatherAPI[Weather API]
    end
    
    subgraph Sync Process
        Check[Check Updates]
        Validate[Validate Data]
        Merge[Merge Changes]
        Backup[Create Backup]
    end
    
    App --> Check
    Check --> Validate
    Validate --> Merge
    Merge --> Backup
    
    RegDB --> Check
    MarketDB --> Check
    WeatherAPI --> Check
    
    Backup --> LocalDB
    Merge --> LocalDB
```

### Error Handling Workflow
```mermaid
stateDiagram-v2
    [*] --> MonitorSystem
    MonitorSystem --> DetectError: Error Occurs
    DetectError --> LogError: Log Error
    LogError --> AnalyzeError: Analyze
    AnalyzeError --> DetermineAction: Determine Action
    
    DetermineAction --> RetryOperation: Retry
    DetermineAction --> NotifyUser: Notify
    DetermineAction --> SystemRecovery: Recover
    
    RetryOperation --> MonitorSystem: Success
    RetryOperation --> NotifyUser: Failed
    
    NotifyUser --> UserResponse: Wait
    UserResponse --> MonitorSystem: Resolved
    UserResponse --> SystemRecovery: Not Resolved
    
    SystemRecovery --> MonitorSystem: Recovered
    SystemRecovery --> EmergencyShutdown: Critical
    EmergencyShutdown --> [*]
```

### Backup and Recovery Workflow
```mermaid
graph TB
    subgraph Backup Process
        Schedule[Schedule Backup]
        Prepare[Prepare Data]
        Compress[Compress Data]
        Store[Store Backup]
    end
    
    subgraph Recovery Process
        Select[Select Backup]
        Verify[Verify Integrity]
        Restore[Restore Data]
        Validate[Validate Recovery]
    end
    
    Schedule --> Prepare
    Prepare --> Compress
    Compress --> Store
    
    Select --> Verify
    Verify --> Restore
    Restore --> Validate
    Validate --> [*]
``` 