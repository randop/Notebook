# Tab vs Space

### Hard Facts and Data on Spaces vs. Tabs for Code Indentation

The **spaces vs. tabs** debate focuses on consistency, readability, collaboration, file size, and minor factors like performance or accessibility. No objective studies prove one is superior for readability or performance, but real-world data from surveys, repositories, and style guides show clear trends.

#### Usage Statistics from Surveys and Repositories
- **Stack Overflow Developer Survey (2017)**: Among ~28,000 professional developers:
  - 41.8% primarily use **spaces**.
  - 40.7% primarily use **tabs**.
  - 17.5% use both.
  This is one of the largest datasets; earlier surveys (e.g., 2015) showed tabs slightly ahead (~45% tabs vs. ~34% spaces).
- **GitHub Analysis (2016, by Google engineer Felipe Hoffa)**: Analyzed 400,000 repositories, 1 billion files, 14 TB of code.
  - **Spaces** dominate in most languages (e.g., JavaScript, Python, Java, Ruby).
  - **Tabs** more common in a few (e.g., Go, some C projects).
  - Overall, spaces are far more prevalent in popular/open-source code.
- **JavaScript-Specific Data**: In top npm packages and surveys (e.g., State of JS indirect data), tabs are "almost non-existent"; spaces (usually 2) overwhelmingly preferred.

Spaces are more common in modern, collaborative, and web-related codebases.

#### Salary Correlation (2017 Stack Overflow Data)
- Developers using **spaces** had a median salary of ~$59,000.
- Those using **tabs** had ~$43,750.
- Spaces users earned ~8-9% more on average, even after controlling for experience, country, language, and developer type.
- This held across subgroups (e.g., web vs. mobile devs).
- **Caveat**: Correlation, not causation. Likely proxy for other factors (e.g., spaces common in high-paying ecosystems like web/devops; tabs in some legacy/mobile areas).

#### Major Style Guides and Projects
| Project/Language       | Preference          | Details                                      |
|------------------------|---------------------|----------------------------------------------|
| Python (PEP 8)         | Spaces (4 per level)| Strictly recommends spaces; mixing tabs/spaces forbidden. |
| Google (Java, JS, C++) | Spaces (2 per level)| No tabs for indentation.                     |
| Airbnb/JavaScript      | Spaces (2)          | Dominant JS style guide.                     |
| Linux Kernel (C)       | Tabs (8-wide)       | Explicitly uses real tabs; rare modern outlier. |
| Go                     | Tabs                | gofmt enforces tabs.                         |

Most major guides (especially post-2010) mandate **spaces** for consistency.

#### Objective Pros/Cons Backed by Data/Reasoning
- **Consistency/Readability**:
  - Spaces ensure identical appearance across editors/tools (no variable tab width issues).
  - Tabs allow personal customization (e.g., accessibility: wider tabs for visual impairments).
  - Mixed tabs/spaces cause diffs, merge conflicts, and misalignment (common pain point in version control).
- **File Size**:
  - Tabs: 1 byte per indent level → smaller files (e.g., ~10-17% reduction in large files with deep nesting).
  - Spaces (4 per level): 4 bytes per level → larger, but negligible today (e.g., <1% impact on most repos).
- **Performance**:
  - Negligible difference in parsing/loading (tabs slightly smaller/faster, but irrelevant vs. code execution).
  - No measurable runtime impact; file size savings tiny in modern contexts.
- **Other**:
  - Tabs semantically meant for indentation (historical intent).
  - Spaces better for alignment (e.g., lining up arguments in functions).

#### Conclusion from Data
**Spaces** are the dominant choice in practice (especially in collaborative/modern projects) and correlate with higher adoption in popular ecosystems. They win on consistency, which reduces bugs in teams. **Tabs** have theoretical advantages (size, customization) but lose on real-world uniformity. Most evidence points to spaces as the "winning" standard today—pick one and enforce it (e.g., via EditorConfig, Prettier, or Black). The key fact: inconsistency (mixing) is worse than either pure choice.
