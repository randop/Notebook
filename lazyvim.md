# LazyVim

## Setup fuzzy find (<leader>sg), Tree-sitter and LSP
```bash
sudo pacman -S tree-sitter ripgrep
```

## Force reload everything in safest way
> Fixes most of plugins and configurations issues
> 
> When Neovim starts, LazyVim will automatically reinstall/update all plugins.
```shell
rm -rf ~/.local/share/nvim/lazy
```

* Return to netrw: `:Rex`
* Prepend comment (#) on selected text: `:'<,'>norm I# `

---

## Manually set filetype for LSP and syntax highlighting

### 1. For a specific file / buffer (manual / temporary)

In any buffer, run one of these commands:

```vim
:set filetype=lua
" or
:set ft=lua
```

Or from Lua (e.g., in a command or mapping):

```lua
vim.bo.filetype = "lua"
-- or
vim.cmd("set filetype=lua")
```

This immediately enables:
- Lua syntax highlighting (via Treesitter in LazyVim)
- Lua LSP (`lua_ls` / Lua Language Server) if it's attached or attaches on filetype change
- Indentation, snippets, etc.

### 2. Permanently associate a file extension with Lua (recommended)

Create or edit the file:

`~/.config/nvim/filetype.lua`

Add this:

```lua
-- ~/.config/nvim/filetype.lua
vim.filetype.add({
  extension = {
    -- Example: treat .rockspec files as Lua (common for Lua projects)
    rockspec = "lua",
    -- Add more if needed, e.g. custom extensions
    -- mylua = "lua",
  },
  -- Or by filename pattern (for files without extension)
  pattern = {
    [".*%.lua%.txt"] = "lua",   -- example
    ["init%.lua"] = "lua",      -- already handled usually
  },
})
```

LazyVim respects `filetype.lua` (it's loaded early by Neovim).

After creating this file, restart Neovim or run `:e` on the buffer.

### 3. Force Lua syntax / LSP on any buffer (via autocmd)

If you need to force Lua on certain buffers (e.g., specific filenames or conditions), add this to:

`~/.config/nvim/lua/config/autocmds.lua`

```lua
-- Force Lua filetype + syntax + LSP on specific conditions
vim.api.nvim_create_autocmd({ "BufRead", "BufNewFile" }, {
  pattern = { "*.myext", "myconfig" },  -- change to your needs
  callback = function()
    vim.bo.filetype = "lua"
    -- Optional: ensure Treesitter highlights
    vim.bo.syntax = "lua"  -- rarely needed with Treesitter
  end,
})
```

### 4. Ensure Lua LSP is enabled (LazyVim default)

LazyVim already includes **lua_ls** by default via `nvim-lspconfig` + Mason.

You can customize it in:

`~/.config/nvim/lua/plugins/lsp.lua` (or any file in `lua/plugins/`)

```lua
return {
  {
    "neovim/nvim-lspconfig",
    opts = {
      servers = {
        lua_ls = {
          -- Your custom settings for lua_ls
          settings = {
            Lua = {
              diagnostics = {
                globals = { "vim" },
              },
              workspace = {
                checkThirdParty = false,
                library = {
                  vim.fn.expand("$VIMRUNTIME/lua"),
                  vim.fn.stdpath("config") .. "/lua",
                },
              },
            },
          },
        },
      },
    },
  },
}
```

### Quick check commands

After setting the filetype:

- `:set filetype?` → should show `lua`
- `:LspInfo` → should show `lua_ls` attached
- `:TSHighlightCapturesUnderCursor` → to see Treesitter highlights

### Common use cases

- **.lua files** → already handled automatically by Neovim/LazyVim.
- **Custom extensions** (e.g., `.rockspec`, `.lua.in`) → use `filetype.lua`.
- **Files without extension** that should be treated as Lua → use `pattern` in `vim.filetype.add` or an autocmd.

