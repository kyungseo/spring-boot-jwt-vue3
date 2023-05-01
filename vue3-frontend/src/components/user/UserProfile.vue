<template>
  <div class="container d-flex justify-content-center">
    <div class="col-sm-8">
      <div class="card">
        <img src="@/assets/home-profile.png" class="card-img-top" />
        <div class="card-body">
          <h5 class="card-title">{{currentUser.userRealName}} Profile</h5>
          <p class="card-text">
            <strong>Token:</strong>
            {{currentUser.accessToken.substring(0, 20)}} ... {{currentUser.accessToken.substr(currentUser.accessToken.length - 20)}}
            <br />
            <strong>Email:</strong>
            {{currentUser.userEmail}}
            <br />
            <strong>Authorities:</strong>
            {{currentUser.roles}}
          </p>
          <a href="#!" class="btn btn-primary">Button</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'UserProfile',
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    }
  },
  mounted() {
    if (!this.currentUser) {
      this.$router.push('/auth/login');
    }
  }
};
</script>