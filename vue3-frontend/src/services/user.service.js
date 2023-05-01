import api from './api';

class UserService {
  /* "/api/v1/jwt/access/test/*" */

  getPublicHome() {
    return api.get('/access/test/public');
  }

  getUserHome() {
    return api.get('/access/test/user');
  }

  getStaffHome() {
    return api.get('/access/test/staff');
  }

  getAdminHome() {
    return api.get('/access/test/admin');
  }
  
  /* "/api/v1/jwt/secured/sample/users/*" */

  getAll() {
    return api.get('/secured/sample/users')
  }

  get(id) {
    return api.get(`/secured/sample/users/${id}`)
  }

  create(data) {
    return api.post('/secured/sample/users', data)
  }

  update(id, data) {
    return api.put(`/secured/sample/users/${id}`, data)
  }

  delete(id) {
    return api.delete(`/secured/sample/users/${id}`)
  }

  /* getAll() => user data sample 
  {
    "state": 200,
    "success": true,
    "message": "success",
    "error": "",
    "fieldErrors": "",
    "data": {
        "totalPage": 3,
        "pageNum": 1,
        "pageSize": 5,
        "start": 1,
        "end": 3,
        "prev": false,
        "next": false,
        "pageList": [
            1,
            2,
            3
        ],
        "dtos": [
            {
                "id": 8,
                "membername": "박경서",
                "email": "Kyungseo.Park@gmail.com",
                "password": "$2a$11$z8JZk3fPQ4HuW0aPKWO6IuQzja3wBe4rV/2iLkcXgXy0hi7dHLMTi",
                "enabled": true,
                "age": 25,
                "phoneNumber": "82-10-4728-2323",
                "country": "KR",
                "birthdate": null,
                "isUsing2FA": false,
                "secret": "MI43AT2TPZGAWKAO",
                "roles": null,
                "regDate": "2023-04-01T15:15:49.140356",
                "modDate": "2023-04-01T15:15:49.140356",
                "date": null
            }
        ]
    },
    "timestamp": "2023-04-01 15:30:23"
  }
  */
}

export default new UserService();
