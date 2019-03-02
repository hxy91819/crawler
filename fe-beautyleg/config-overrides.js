const {
    override,
    fixBabelImports
} = require("customize-cra");

module.exports = override(
    fixBabelImports("babel-plugin-import", {
        libraryName: "antd-mobile",
        style: "css"
    })
);