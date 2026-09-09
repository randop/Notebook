# PNPM

### Installation
```sh
curl -fsSL https://get.pnpm.io/install.sh | sh -
# or
wget -qO- https://get.pnpm.io/install.sh | sh -
```

## Install the LTS version of Node.js:
```sh
pnpm runtime set node lts -g
```

### Install the latest version of Node.js:
```sh
pnpm runtime set node latest -g
```

### Install a specific major version:
```sh
pnpm runtime set node 22 -g

# Check the active Node version
node -v
```

## Commands
```
| npm command       | pnpm equivalent   |
|-------------------|-------------------|
| npm install       | pnpm install      |
| npm i <pkg>       | pnpm add <pkg>    |
| npm run <cmd>     | pnpm <cmd>        |
```
