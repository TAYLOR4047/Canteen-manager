module.exports = {
    dev: {
        // Paths
        assetsSubDirectory: 'static',
        assetsPublicPath: '/',
        proxyTable: {
            '/local': {
                target: 'http://localhost:8088/',
                changeOrigin: true,//允许跨域
                pathRewrite: {
                    '^/local': ''
                }
            },
        },
    },
}
