const proxy = require('http-proxy-middleware')
const {createProxyMiddleware} = require("http-proxy-middleware");

module.exports = function(app) {
    app.use(
        //local java api
        createProxyMiddleware('/api1',{
            target: 'http://ps.gavinw.xyz:8810',
            changeOrigin: true,
            pathRewrite:{
                '^/api1':''}
        }),
        // yantai bus api
        createProxyMiddleware('/api2',{
            target: 'http://api1.jiaodong.net:81',
            changeOrigin: true,
            pathRewrite:{
                '^/api2':''}
        }),
    )
}