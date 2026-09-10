# Linear

Summary
Add an AI-assistance attribution as the final line of every Linear write operation you create, edit, or substantially update.

Format**Assisted By:** <vendor> <model name> <model version> <variant>
Example: **Assisted By:** Anthrophic Claude Sonnet 5 High
Do not invent version/variant. Use the most accurate information available.

Placement
• Attribution must be the LAST line of the content
• Leave a blank line before it, nothing after it
• Comments: end of comment body
• Issues/projects: end of description, acceptance criteria, technical notes, reviews, updates, etc.

Updates
• Preserve existing attribution if still accurate
• If AI materially changes content, update attribution to current model/session

Scope
Apply automatically to ALL Linear write operations with free-form text. Do not add to machine-generated metadata, IDs, labels, URLs, or fields without free-form text.

Requirement
Attribution is mandatory. Verify it is present and is the final line before submitting any Linear write operation.
