# Github Actions Bot

**The "GitHub Actions bot"** (`github-actions[bot]`) is a special built-in system account that GitHub uses for actions performed by workflows. It appears on commits, comments, PR assignments, labels, etc., when you use the automatic `GITHUB_TOKEN`.

### How to Use It

1. **Use the built-in `GITHUB_TOKEN`** (recommended)
   - GitHub automatically provides this short-lived token in every workflow job.
   - Reference it as `${{ secrets.GITHUB_TOKEN }}`.
   - Any API calls or Git operations using this token will be attributed to `github-actions[bot]`.

**Example workflow** (assigns a PR and makes a commit as the bot):

```yaml
name: Example Bot Action

on:
  pull_request:
    types: [opened]

jobs:
  bot-action:
    runs-on: ubuntu-latest
    permissions:
      pull-requests: write   # Adjust as needed
      contents: write

    steps:
      - uses: actions/checkout@v4

      - name: Assign PR with bot
        env:
          PR_NUMBER: ${{ github.event.pull_request.number }}
        run: |
          curl -H "Authorization: token ${{ secrets.GITHUB_TOKEN }}" \
               -X POST \
               -d '{"assignees": ["your-username"]}' \
               https://api.github.com/repos/${{ github.repository }}/issues/${PR_NUMBER}/assignees

      - name: Make a commit as the bot
        run: |
          git config user.name "github-actions[bot]"
          git config user.email "41898282+github-actions[bot]@users.noreply.github.com"
          
          echo "Auto-updated at $(date)" >> README.md
          git add README.md
          git commit -m "chore: auto update"
          git push
```

### Key Details

- **Email for bot commits**: `41898282+github-actions[bot]@users.noreply.github.com`
- **Username**: `github-actions[bot]`
- **Permissions**: Controlled via the `permissions` key at workflow or job level (principle of least privilege).
- **Limitations**: It is a *system account*, not a full GitHub App. This means it cannot always bypass branch protection rules (e.g., "Require signed commits" or certain status checks).

### When to Use Something Else Instead

| Use Case                          | Recommended Approach                  |
|-----------------------------------|---------------------------------------|
| Simple automation inside repo     | `GITHUB_TOKEN` + `github-actions[bot]` |
| Bypass branch protection          | Create a **GitHub App**               |
| Cross-repo access                 | GitHub App or PAT from a machine user |
| Custom bot name/identity          | GitHub App or dedicated bot account   |

**For advanced bots**, create a [GitHub App](https://docs.github.com/en/apps) and generate installation tokens in your workflow.

### Official Resources
- [Using GITHUB_TOKEN in workflows](https://docs.github.com/en/actions/security-for-github-actions/security-hardening-your-deployments/about-security-hardening-with-github-actions) (or search "GITHUB_TOKEN" in GitHub Docs)
- [Workflow permissions syntax](https://docs.github.com/en/actions/using-workflows/workflow-syntax-for-github-actions#permissions)

