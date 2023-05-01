import axios from "axios";

const instance = axios.create({
  baseURL: "http://localhost:8090/api/v1/jwt",
  headers: {
    "Content-Type": "application/json",
  },
});

export default instance;
