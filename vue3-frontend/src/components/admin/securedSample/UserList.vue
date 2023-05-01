<template>
  <div class="container d-flex justify-content-center">
    <vue-good-table :columns="columns" :rows="rows" />
  </div>
</template>

<script>
import { inject } from "vue";
import UserService from "../../../services/user.service";
import EventBus from "../../../common/EventBus";

export default {
  name: "UserList",
  data() {
    return {
      columns: [
        {
          label: "Name",
          field: "membername",
        },
        {
          label: "Email",
          field: "email",
        },
        {
          label: "Birthdate",
          field: "birthdate",
        },
        {
          label: "Phone Number",
          field: "phoneNumber",
        },
      ],
      rows: [],
    };
  },
  methods: {
    getAllUsers() {
      const toast = inject("toast", "");

      UserService.getAll()
      .then((response) => {
        console.log(response.data);
        this.rows = response.data.result.dtoList;
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
