import axiosInstance from "./api";
import TokenService from "./token.service";

const setup = (store) => {
  axiosInstance.interceptors.request.use(
    (config) => {
      if (config.url !== "/auth/login") {
        const token = TokenService.getLocalAccessToken();
        console.log(
          //"axiosInstance.interceptors.request >\n" + "URL: " + config.url + "\n" + "TOKEN: " + token);
          "axiosInstance.interceptors.request >\n" + "URL: " + config.url);
        if (token) {
          // for Spring Boot back-end
          config.headers["Authorization"] = 'Bearer ' + token;
          // Node.js Express back-end인 경우 다음 코드 사용
          //config.headers["x-access-token"] = token;
        }
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  axiosInstance.interceptors.response.use(
    (res) => {
      return res;
    },
    async (err) => {
      const originalConfig = err.config;

      if (originalConfig.url !== "/auth/login" && err.response) {
        console.log("axiosInstance.interceptors.request >\nERR STATUS: " + err.response.status)
        // Access Token이 만료됨
        if (err.response.status === 401 && !originalConfig._retry) {
          originalConfig._retry = true;

          try {
            /* /api/v1/auth/refresh
            // PAYLOAD
            {
              "refreshToken": "8eab0374-d63c-4ef5-afe3-c65956a27744"
            }
            // RESPONSE
            {
              "tokenType": "Bearer ",
              "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyMyIsImlhdCI6MTY4MDI0OTc2NSwiaXNzIjoiS3l1bmdzZW8uUGFya0BnbWFpbC5jb20iLCJleHAiOjE2ODAyNTMzNjUsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfU1RBRkYifQ.ISp5klfuX1crrGGAQYepVdvWpkH2y3WE-zHJKcAA53-JjSna9QGFoZ_QgTJidaylGgz3GMyoNQptT3I_fq-6Jw",
              "refreshToken": "77ab7606-195c-4178-9ce0-6a91c2fabdfc",
              "roles": "",
              "expiryDuration": 3600000
            }
            */
            const rs = await axiosInstance.post("/auth/refresh", {
              refreshToken: TokenService.getLocalRefreshToken(),
            });

            const { accessToken } = rs.data;

            store.dispatch('auth/refreshToken', accessToken);
            TokenService.updateLocalAccessToken(accessToken);

            return axiosInstance(originalConfig);
          } catch (_error) {
            if (_error.response && _error.response.data) {
              return Promise.reject(_error.response.data);
            }
  
            return Promise.reject(_error);
          }
        }
  
        if (err.response.status === 403 && err.response.data) {
          return Promise.reject(err.response.data);
        }
      }

      return Promise.reject(err);
    }
  );
};

export default setup;