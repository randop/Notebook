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

```
Traceback (most recent call last):
  File "/applications/langchain-google/main.py", line 4, in <module>
    llm = ChatGoogleGenerativeAI(model="gemini-2.5-pro")
          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  File "/applications/langchain-google/.venv/lib/python3.12/site-packages/langchain_google_genai/chat_models.py", line 1179, in __init__
    super().__init__(**kwargs)
  File "/applications/langchain-google/.venv/lib/python3.12/site-packages/langchain_core/load/serializable.py", line 130, in __init__
    super().__init__(*args, **kwargs)
  File "/applications/langchain-google/.venv/lib/python3.12/site-packages/pydantic/main.py", line 253, in __init__
    validated_self = self.__pydantic_validator__.validate_python(data, self_instance=self)
                     ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  File "/applications/langchain-google/.venv/lib/python3.12/site-packages/langchain_google_genai/chat_models.py", line 1240, in validate_environment
    self.client = genaix.build_generative_service(
                  ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  File "/applications/langchain-google/.venv/lib/python3.12/site-packages/langchain_google_genai/_genai_extension.py", line 276, in build_generative_service
    return v1betaGenerativeServiceClient(**config)
           ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  File "/applications/langchain-google/.venv/lib/python3.12/site-packages/google/ai/generativelanguage_v1beta/services/generative_service/client.py", line 697, in __init__
    self._transport = transport_init(
                      ^^^^^^^^^^^^^^^
  File "/applications/langchain-google/.venv/lib/python3.12/site-packages/google/ai/generativelanguage_v1beta/services/generative_service/transports/grpc.py", line 234, in __init__
    super().__init__(
  File "/applications/langchain-google/.venv/lib/python3.12/site-packages/google/ai/generativelanguage_v1beta/services/generative_service/transports/base.py", line 100, in __init__
    credentials, _ = google.auth.default(
                     ^^^^^^^^^^^^^^^^^^^^
  File "/applications/langchain-google/.venv/lib/python3.12/site-packages/google/auth/_default.py", line 685, in default
    raise exceptions.DefaultCredentialsError(_CLOUD_SDK_MISSING_CREDENTIALS)
google.auth.exceptions.DefaultCredentialsError: Your default credentials were not found. To set up Application Default Credentials, see https://cloud.google.com/docs/authentication/external/set-up-adc for more information.
```