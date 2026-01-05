# CXL

[Compute Express Link (CXL)](https://grokipedia.com/page/Compute_Express_Link) is an open-standard, cache-coherent interconnect technology that enables high-speed, low-latency connections between processors, accelerators, and memory devices, primarily in data centers and high-performance computing environments. Built on the physical layer of PCI Express (PCIe), CXL maintains memory coherency across CPU and attached devices, facilitating resource pooling, sharing, and disaggregation to support demanding workloads such as artificial intelligence, machine learning, and big data analytics. By reducing software complexity, minimizing redundant memory management, and lowering system costs, CXL enhances overall performance and scalability in heterogeneous computing systems.

![photo of CXL DRAM Memory Expander module](https://gitlab.com/randop/notebook/-/raw/main/assets/CXL-DRAM-Memory-Expander-module.jpg)
### Key Versions and Evolution
- **CXL 1.0/1.1 (2019)**: Introduced basic coherent memory access over PCIe 5.0.
- **CXL 2.0 (2020)**: Added support for memory pooling, sharing, and switching for multi-host scenarios.
- **CXL 3.0 (2022)**: Doubled bandwidth to 64 GT/s, improved fabric management, security, and multi-level switching.
- **CXL 4.0 (released November 2025)**: Doubles bandwidth again to 128 GT/s (based on PCIe 7.0), adds bundled ports for efficiency, and enhances reliability features like advanced RAS (Reliability, Availability, Serviceability).
