import pandas as pd
from langchain.agents.agent_types import AgentType
from langchain_experimental.agents.agent_toolkits import create_pandas_dataframe_agent
from langchain.callbacks.streaming_stdout import StreamingStdOutCallbackHandler
from langchain_community.llms import GPT4All

callbacks = [StreamingStdOutCallbackHandler()]

local_path = (
    "/root/.cache/gpt4all/mistral-7b-instruct-v0.1.Q4_0.gguf"
)

llm = GPT4All(model=local_path, temp=0.0, backend="gptj", callbacks=callbacks, verbose=True)

df = pd.read_csv("./data/mlb_teams_2012.csv")

agent = create_pandas_dataframe_agent(
    llm,
    df,
    verbose=False
)

agent.run("Who is top winner? Let's do it step by step.")
