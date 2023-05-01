<template>
  <div class="container d-flex justify-content-center">
    <div class="col-sm-8">
      <div class="card">
        <img src="@/assets/home-admin.png" class="card-img-top" />
        <div class="card-body">
          <h5 class="card-title">Admin Home</h5>
          <p class="card-text">{{ content }}</p>
          <a href="#!" class="btn btn-primary">Button</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { inject } from "vue";
import UserService from "../../services/user.service";
import EventBus from "../../common/EventBus";

export default {
  name: "AdminHome",
  data() {
    return {
      content: "",
    };
  },
  methods: {
    getAllUsers() {
      const toast = inject("toast", "");

      UserService.getAdminHome()
      .then((response) => {
        this.content = response.data;
      }).catch((error) => {
        toast.value =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();

          if (error.response && error.response.status === 403) {
            EventBus.dispatch("logout");
          }
      });
    },
  },
  mounted() {
    this.getAllUsers();
  },
};
</script>
