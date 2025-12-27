# Sparse Checkout
This checks out only the specified paths while keeping a full repo. It useful if you might need history or other parts later.

```shell
git clone --sparse <repository-url>
cd <repository-name>
# e.g., src/docs or folder/subfolder
git sparse-checkout set <path/to/subdirectory>
```

### Example:
```shell
git clone --sparse git@gitlab.com:randop/machines.git
cd machines
git sparse-checkout set routers-deuteronomy-v2
```

### Data saver mode
> For even less initial download in very large repos (avoids fetching unnecessary file contents upfront):
```shell
git clone --filter=blob:none --no-checkout https://gitlab.com/randop/notebook.git
cd notebook
git sparse-checkout init --cone
git sparse-checkout set scripts
git checkout
```
