<template>
  <div class="container d-flex justify-content-center">
    <div class="col-sm-8">
      <div class="card card-container">
        <img src="@/assets/login.png" class="card-img-top" />
        <div class="card-body">
          <h5 class="card-title">로그인</h5>
          <Form @submit="handleLogin" :validation-schema="schema">
            <div class="form-group">
              <label for="username">Email</label>
              <Field name="username" type="text" class="form-control" />
              <ErrorMessage name="username" class="error-feedback" />
            </div>
            <div class="form-group">
              <label for="password">Password</label>
              <Field name="password" type="password" class="form-control" />
              <ErrorMessage name="password" class="error-feedback" />
            </div>

            <div class="form-group">
              <button class="btn btn-primary btn-block" :disabled="loading">
                <span
                  v-show="loading"
                  class="spinner-border spinner-border-sm"
                ></span>
                <span>Login</span>
              </button>
            </div>

            <div class="form-group">
              <div v-if="message" class="alert alert-danger" role="alert">
                {{ message }}
              </div>
            </div>
          </Form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Form, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";

export default {
  name: "Login",
  components: {
    Form,
    Field,
    ErrorMessage,
  },
  data() {
    const schema = yup.object().shape({
      username: yup.string().required("사용자 ID는 필수 항목입니다."),
      password: yup.string().required("비밀번호는 필수 항목입니다."),
    });

    return {
      loading: false,
      message: "",
      schema,
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  created() {
    if (this.loggedIn) {
      this.$router.push("/user/profile");
    }
  },
  methods: {
    handleLogin(user) {
      this.loading = true;
      // username = email
      // TODO: input 유형을 email로 변경할 것
      user.email = user.username;

      this.$store.dispatch("auth/login", user).then(
        () => {
          this.$router.push("/user/profile");
        },
        (error) => {
          this.loading = false;
          this.message =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
        }
      );
    },
  },
};
</script>

<style scoped>
.error-feedback {
  color: red;
}
</style>
