# React
> React is a free and open-source front-end JavaScript library for building user interfaces based on UI components. It is maintained by Meta and a community of individual developers and companies. https://en.wikipedia.org/wiki/React_(JavaScript_library)

### package.json
```json
{
  "name": "randop-react",
  "version": "0.1.0",
  "description": "ReactJS application starter project",
  "main": "index.js",
  "keywords": [],
  "author": "",
  "scripts": {
    "clean": "rm dist/bundle.js",
    "build-dev": "webpack --mode development",
    "build-prod": "webpack --mode production",
    "start": "webpack serve --hot --mode development",
    "test": "jest"
  },
  "dependencies": {
    "@material-ui/core": "4.x.x",
    "@material-ui/icons": "4.x.x",
    "fontsource-roboto": "4.x.x",
    "ol": "^6.14.1",
    "react": "18.x.x",
    "react-dom": "18.x.x",
    "react-hot-loader": "4.x.x",
    "tailwindcss": "1.x.x"
  },
  "devDependencies": {
    "@babel/core": "^7.18.6",
    "@babel/preset-env": "^7.18.6",
    "@babel/preset-react": "^7.18.6",
    "@hot-loader/react-dom": "^17.0.2+4.13.0",
    "autoprefixer": "^10.4.7",
    "babel-jest": "^28.1.2",
    "babel-loader": "^8.2.5",
    "clean-webpack-plugin": "^4.0.0",
    "copy-webpack-plugin": "^11.0.0",
    "css-loader": "^6.7.1",
    "eslint": "^8.18.0",
    "eslint-plugin-react": "^7.30.1",
    "file-loader": "^6.2.0",
    "html-webpack-plugin": "^5.5.0",
    "jest": "^28.1.2",
    "mini-css-extract-plugin": "^2.6.1",
    "node-sass": "^7.0.1",
    "postcss-loader": "^7.0.0",
    "prettier": "^2.7.1",
    "sass-loader": "^13.0.2",
    "style-loader": "^3.3.1",
    "url-loader": "^4.1.1",
    "webpack": "^5.73.0",
    "webpack-cli": "^4.10.0",
    "webpack-dev-server": "^4.9.3"
  }
}
```
