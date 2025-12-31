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
