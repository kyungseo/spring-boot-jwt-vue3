import api from "./api";
import TokenService from "./token.service";
import DeviceService from "./device.service";

class AuthService {
  /* /api/v1/auth/login
  // PAYLOAD
  {
    "username": "NonEmpty String",
    "email": "NonEmpty String",
    "password": "NonEmpty String",
    "deviceInfo": {
      "deviceId": "Non empty string",
      "deviceType": "DEVICE_TYPE_ANDROID, DEVICE_TYPE_IOS",
      "notificationToken": "Non empty string"
    }
  }
  // RESPONSE
  {
    "tokenType": "Bearer ",
    "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyMyIsImlhdCI6MTY4MDE2NjQ3MywiaXNzIjoiS3l1bmdzZW8uUGFya0BnbWFpbC5jb20iLCJleHAiOjE2ODAxNzAwNzMsImF1dGhvcml0aWVzIjoiUk9MRV9BRE1JTixST0xFX1VTRVIsUk9MRV9TVEFGRiJ9.tb3yT4yz3t_MLo1ZY4KmqNk5oJXtBYlOEKzoKdRi1sKdlu9OAVdrIa_Wj4AWu2PnxyLmazkrGcV-kAXOhdBFwA",
    "refreshToken": "4c93b1d1-7cc7-4b3b-ad1c-f5ed83a529d2",
    "roles": "ROLE_ADMIN,ROLE_USER,ROLE_STAFF",
    "expiryDuration": 3600000
  }
  */
  //login({ username, password }) {
  login(user) {
    // deviceInfo 추가
    user.deviceInfo = {
      deviceId: DeviceService.getDeviceUUID(),
      deviceType: "DEVICE_TYPE_ANDROID",
      notificationToken: "notificationToken"
    }

    return api
      .post("/auth/login", user)
      .then((response) => {
        if (response.data.accessToken) {
          TokenService.setUser(response.data);
        }

        return response.data;
      });
  }

  /* /api/v1/user/logout
  // PAYLOAD
  {
    "token": "string",
    "deviceInfo": {
      "Device Id": "Non empty string",
      "Device type Android/iOS": "DEVICE_TYPE_ANDROID, DEVICE_TYPE_IOS",
      "Device notification id": "Non empty string"
    }
  }
  // RESPONSE
  {
    "state": 200,
    "success": true,
    "message": "사용자가 로그아웃 성공!",
    "error": "",
    "fieldErrors": "",
    "data": [],
    "timestamp": "2023-03-31 15:35:06"
  }
  */
  logout() {
    const user = {};
    user.token = TokenService.getLocalAccessToken();
    user.deviceInfo = {
      deviceId: DeviceService.getDeviceUUID(),
      deviceType: "DEVICE_TYPE_ANDROID",
      notificationToken: "notificationToken"
    }

    return api
      .post("/user/logout", user)
      .then((response) => {
        if (response.data.success) {
          TokenService.removeUser();
        }

        return response.data;
      });
  }

  /* /api/v1/auth/register
  // PAYLOAD
  {
    "username": "kyungseo72",
    "email": "Kyungseo.Park@kakao.com",
    "password": "passworD1!",
    "registerAsAdmin": true
  }
  // RESPONSE
  {
    "data": "사용자 등록이 성공하였습니다. 확인을 위해 이메일을 체크하시기 바랍니다.",
    "success": true,
    "timestamp": "2023-03-31T07:11:55.054037700Z"
  }
  */
  register({ username, email, password }) {
    return api.post("/auth/register", {
      username,
      email,
      password
    });
  }
}

export default new AuthService();
