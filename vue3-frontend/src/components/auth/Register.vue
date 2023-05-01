<template>
  <div class="container d-flex justify-content-center">
    <div class="col-sm-8">
      <div class="card card-container">
        <img src="@/assets/login.png" class="card-img-top" />
        <div class="card-body">
          <h5 class="card-title">회원 가입</h5>
          <Form @submit="handleRegister" :validation-schema="schema">
            <div v-if="!successful">
              <div class="form-group">
                <label for="username">Username</label>
                <Field name="username" type="text" class="form-control" />
                <ErrorMessage name="username" class="error-feedback" />
              </div>
              <div class="form-group">
                <label for="email">Email</label>
                <Field name="email" type="email" class="form-control" />
                <ErrorMessage name="email" class="error-feedback" />
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
                  Sign Up
                </button>
              </div>
            </div>
          </Form>

          <div v-if="message" class="alert" :class="successful ? 'alert-success' : 'alert-danger'">
            {{ message }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Form, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";

export default {
  name: "Register",
  components: {
    Form,
    Field,
    ErrorMessage,
  },
  data() {
    const schema = yup.object().shape({
      username: yup
        .string()
        .required("사용자 ID는 필수 항목입니다.")
        .min(3, "3 글자 이상이어야 합니다.")
        .max(20, "20 글자를 초과할 수 없습니다."),
      email: yup
        .string()
        .required("이메일은 필수 항목입니다.")
        .email("Email이 잘못되었습니다.")
        .max(50, "50 글자를 초과할 수 없습니다."),
      password: yup
        .string()
        .required("비밀번호는 필수 항목입니다.")
        .min(6, "6 글자 이상이어야 합니다.")
        .max(40, "40 글자를 초과할 수 없습니다."),
    });

    return {
      successful: false,
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
  mounted() {
    if (this.loggedIn) {
      this.$router.push("/user/profile");
    }
  },
  methods: {
    handleRegister(user) {
      this.message = "";
      this.successful = false;
      this.loading = true;

      this.$store.dispatch("auth/register", user).then(
        (data) => {
          this.message = data.message;
          this.successful = true;
          this.loading = false;
        },
        (error) => {
          this.message =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
          this.successful = false;
          this.loading = false;
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
