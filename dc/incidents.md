# Incidents

### 1. **May 7–8, 2026 – US-East-1 (Northern Virginia)
- **Cause**: Cooling system failure in a single data center led to a rapid temperature spike ("thermal event"). Servers automatically shut down to protect hardware once temperatures exceeded safe thresholds, causing power loss to affected racks.
- **Impact**: Affected EC2 instances, EBS volumes, and other services in one Availability Zone (use1-az4). Services like Coinbase were down for ~7 hours. Recovery took many hours as AWS brought additional cooling capacity online.
- **Context**: This is one of the clearest and most recent examples of cooling directly causing a major outage, highlighting challenges with high-density AI workloads.

### 2. **August 23, 2019 – AP-Northeast-1 (Tokyo)**
- **Cause**: Failure in the data center control and cooling system. A bug in a third-party cooling control system caused excessive interactions, leading the system to go offline. Cooling went into a faulty state (some units shut down instead of max cooling), temperatures rose, and servers shut down.
- **Impact**: A small percentage of EC2 servers in a single Availability Zone shut down due to overheating, causing instance failures and service disruptions.

### 3. **June 2012 – US-East-1 (Northern Virginia)**
- **Cause**: During a utility power outage, the data center switched to generators. A defective **generator cooling fan** then failed, causing a generator to overheat and shut down. This compounded the power issue.
- **Impact**: Significant outage affecting multiple services.

