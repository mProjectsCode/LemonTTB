const path = require("path");

module.exports = {
    outputDir: path.resolve(__dirname, "../../resources/static"),
    devServer: {
        proxy: {
            "^/api/": {
                target: "http://localhost:8080",
                pathRewrite: { "^/api/": "/api/" },
                changeOrigin: false,
                logLevel: "debug"
            },
        },
        port: 8081,
        hot: false,
        liveReload: false,
    },
};