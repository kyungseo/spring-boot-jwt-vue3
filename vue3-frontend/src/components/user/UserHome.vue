<template>
  <div class="container d-flex justify-content-center">
    <div class="col-sm-8">
      <div class="card">
        <img src="@/assets/home-user.png" class="card-img-top" />
        <div class="card-body">
          <h5 class="card-title">User Home</h5>
          <p class="card-text">{{ content }}</p>
          <a href="#!" class="btn btn-primary">Button</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import UserService from "../../services/user.service";
import EventBus from "../../common/EventBus";

export default {
  name: "UserHome",
  data() {
    return {
      content: "",
    };
  },
  mounted() {
    UserService.getUserHome().then(
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

        if (error.response && error.response.status === 403) {
          EventBus.dispatch("logout");
        }
      }
    );
  },
};
</script>
