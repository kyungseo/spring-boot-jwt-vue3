<template>
  <div class="container d-flex justify-content-center">
    <div class="col-sm-8">
      <div class="card">
        <img src="@/assets/home-main.png" class="card-img-top" />
        <div class="card-body">
          <h5 class="card-title">Public Home</h5>
          <p class="card-text">{{ content }}</p>
          <a href="#!" class="btn btn-primary">Button</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import UserService from "../services/user.service";

export default {
  name: "Home",
  data() {
    return {
      content: "",
    };
  },
  mounted() {
    UserService.getPublicHome().then(
      (response) => {
        this.content = response.data;
      },
      (error) => {
        this.content =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();
      }
    );
  },
};
</script>
