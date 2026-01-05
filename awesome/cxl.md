# CXL

[Compute Express Link (CXL)](https://grokipedia.com/page/Compute_Express_Link) is an open-standard, cache-coherent interconnect technology that enables high-speed, low-latency connections between processors, accelerators, and memory devices, primarily in data centers and high-performance computing environments. Built on the physical layer of PCI Express (PCIe), CXL maintains memory coherency across CPU and attached devices, facilitating resource pooling, sharing, and disaggregation to support demanding workloads such as artificial intelligence, machine learning, and big data analytics. By reducing software complexity, minimizing redundant memory management, and lowering system costs, CXL enhances overall performance and scalability in heterogeneous computing systems.

![photo of CXL DRAM Memory Expander module](https://gitlab.com/randop/notebook/-/raw/main/assets/CXL-DRAM-Memory-Expander-module.jpg)

### CXL Device Types
- **Type-1 CXL Device**: SMART NIC or video accelerators, which run specific algorithm function like video transcoding on data present in Host Memory.

![photo of CXL Type-1 device](https://gitlab.com/randop/notebook/-/raw/main/assets/cxl-type-1.jpg)

- **Type-2 CXL Device**: FPGA or CPU based accelerators with integrated memory (like HBM or DDR).

![photo of CXL Type-2 device](https://gitlab.com/randop/notebook/-/raw/main/assets/cxl-type-2.jpg)

- **Type-3 CXL Device**: Persistent Memory or Computational Storage devices sitting on CXL bus. Such devices cannot access Host Memory on their own.

![photo of CXL Type-3 device](https://gitlab.com/randop/notebook/-/raw/main/assets/cxl-type-3.jpg)
### How Does CXL Memory Expansion Work?
Memory expansion allows systems to extend memory capacity and bandwidth beyond the physical limits of traditional motherboard DIMM slots. It uses CXL Type 3 devices (memory expanders), typically PCIe add-in cards or modules with DDR5 (or other) DRAM, attached to a CXL-enabled CPU via PCIe lanes. The CPU treats this remote memory as part of its unified, coherent memory space, unlike traditional PCIe devices requiring explicit data copying. This enables seamless expansion for memory-intensive workloads like AI training, big data analytics, and in-memory databases.

![photo of CXL Type-3 device](https://gitlab.com/randop/notebook/-/raw/main/assets/cxl-memory-expansion.jpg)

### How Does CXL Memory Pooling Work?
CXL 2.0 supports switching to enable memory pooling for efficient memory allocation. At 2.0 level, device can be partitioned as multiple logical devices (MLD), allowing up to 16 hosts to simultaneously access different portions of the memory.
As an example, Host 1 (H1) can use half the memory in Device 1 (D1) and a quarter of the memory in D2 to finely match the memory requirements of its workload to the available capacity in the memory pool. The remaining capacity in D1 and D2 can be used by H2-H#.

![photo of CXL memory pooling](https://gitlab.com/randop/notebook/-/raw/main/assets/cxl-memory-pooling.jpg)

### Key Versions and Evolution
- **CXL 1.0/1.1 (2019)**: Introduced basic coherent memory access over PCIe 5.0.
- **CXL 2.0 (2020)**: Added support for memory pooling, sharing, and switching for multi-host scenarios.
- **CXL 3.0 (2022)**: Doubled bandwidth to 64 GT/s, improved fabric management, security, and multi-level switching.
- **CXL 4.0 (released November 2025)**: Doubles bandwidth again to 128 GT/s (based on PCIe 7.0), adds bundled ports for efficiency, and enhances reliability features like advanced RAS (Reliability, Availability, Serviceability).

### References
- [https://www.penguinsolutions.com/en-us/resources/blog/what-is-cxl-memory-expansion](https://www.penguinsolutions.com/en-us/resources/blog/what-is-cxl-memory-expansion)
