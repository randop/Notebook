# Google Gemini

## Install
```bash
pnpm i -g @google/gemini-cli
export GEMINI_API_KEY="YOUR_API_KEY"
```

## Try
```
cd new-project/
gemini
> Write me a Gemini Discord bot that answers questions using a FAQ.md file I will provide
```

```
✕ [API Error: {"error":{"message":"{\n  \"error\": {\n    \"code\": 429,\n
  \"message\": \"Resource has been exhausted (e.g. check quota).\",\n    \"status\":
  \"RESOURCE_EXHAUSTED\"\n  }\n}\n","code":429,"status":"Too Many Requests"}}]
  Please wait and try again later. To increase your limits, request a quota increase
  through AI Studio, or switch to another /auth method
```

```
╭─────────────────────────────────╮
│                                 │
│  Agent powering down. Goodbye!  │
│                                 │
│                                 │
│  Cumulative Stats (1 Turns)     │
│                                 │
│  Input Tokens            7,005  │
│  Output Tokens           1,015  │
│  Thoughts Tokens           290  │
│  ─────────────────────────────  │
│  Total Tokens            8,310  │
│                                 │
│  Total duration (API)    46.5s  │
│  Total duration (wall)  6m 26s  │
│                                 │
╰─────────────────────────────────╯
```

```
✕ [API Error: got status: UNAVAILABLE. {"error":{"code":503,"message":"The model is
  overloaded. Please try again later.","status":"UNAVAILABLE"}}]
```