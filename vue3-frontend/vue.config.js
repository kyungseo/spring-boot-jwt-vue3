module.exports = {
  // http-proxy-middleware를 사용하여 /api/v1로 시작하는 모든 webpack dev-server 요청을
  // vue-backend(localhost:8090)로 proxy 한다.
  // see https://cli.vuejs.org/config/#devserver-proxy
  devServer: {
    proxy: {
      '/api/v1': {
        // target port는 vue-backend의 Application.Properties에 정의된
        // server.port와 일치해야 한다.
        target: 'http://localhost:8090',
        ws: true,
        changeOrigin: true
      }
    }
  },
  // maven과 호환될 수 있도록 build path를 변경
  // see https://cli.vuejs.org/config/
  outputDir: 'target/dist',
  assetsDir: 'static'
};
