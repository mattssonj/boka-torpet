const path = require("path");

module.exports = {
  entry: ["@babel/polyfill", "./src/main/js/App.jsx"],
  cache: true,
  devServer: {
    contentBase: path.join(__dirname, "src"),
    compress: true,
    port: 9000,
  },
  devtool: "source-map",
  mode: "development",
  output: {
    path: path.join(__dirname, "./src/main/resources/static/built"),
    filename: "bundle.js",
  },
  module: {
    rules: [
      {
        test: path.join(__dirname, "."),
        exclude: /(node_modules)/,
        use: [
          {
            loader: "babel-loader",
            options: {
              presets: ["@babel/preset-env", "@babel/preset-react"],
            },
          },
        ],
      },
      {
        test: /\.css$/,
        use: ["style-loader", "css-loader"],
      },
    ],
  },
};
